package innovitics.azimut.businessservices;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.businessmodels.payment.PaytabsCallbackRequest;
import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.utilities.crosslayerenums.Action;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.PaymentTransactionStatus;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.validations.validators.payment.InitiatePayment;
import innovitics.azimut.validations.validators.payment.QueryPayment;

@SuppressWarnings("unchecked")
@Service
public class BusinessPaymentService extends AbstractBusinessService<BusinessPayment>{

	@Autowired BusinessAzimutTradingService businessAzimutTradingService;
	@Autowired InitiatePayment initiatePayment;
	@Autowired QueryPayment queryPayment;
	
	public static final String TRANSACTION_ID_PARAM="transactionId";
	public static final String AMOUNT_PARAM="amount";
	
	public BusinessPayment initiatePayment(BusinessPayment businessPayment,BusinessUser tokenizedBusinessUser,String language,boolean isMobile) throws IntegrationException, BusinessException
	{
		this.validation.validate(businessPayment, initiatePayment, BusinessPayment.class.getName());
		try
		{
			PaymentTransaction paymentTransaction=new PaymentTransaction();
			this.populateDynamicParameters(paymentTransaction, businessPayment);
			paymentTransaction = this.paymentService.addPaymentTransaction(
					this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false),
					businessPayment.getAmount(), PaymentGateway.PAYTABS, businessPayment.getCurrencyId(),
					businessPayment.getAction(), paymentTransaction.getParameterNames(),
					paymentTransaction.getParameterValues());
		
			if(!isMobile)
			{
				businessPayment=(BusinessPayment)this.restContract.getData(this.restContract.paytabsInitiatePaymentMapper, this.preparePaymentInputs(paymentTransaction,tokenizedBusinessUser,businessPayment,language), null);
				this.updateTransactionAfterGatewayCall(businessPayment, paymentTransaction,PaymentTransactionStatus.PG);
			}
			else
			{	
				businessPayment.setCartId(String.valueOf(paymentTransaction.getId()));
				//businessPayment.setCartId(this.aes.encrypt(String.valueOf(paymentTransaction.getId())+"-"+String.valueOf(StringUtility.generateAmountStringWithoutDecimalPoints(businessPayment.getAmount()))));
				businessPayment.setTransactionId(paymentTransaction.getId());
			}
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);
		}
		
		return businessPayment;
	}
	public BusinessPayment queryPayment(BusinessPayment businessPayment,BusinessUser tokenizedBusinessUser,String language,boolean isMobile) throws IntegrationException, BusinessException
	{
		
			this.validation.validate(businessPayment, queryPayment, BusinessPayment.class.getName());
			PaymentTransaction paymentTransaction=new PaymentTransaction();
			paymentTransaction=this.paymentTransactionUtility.getTransactionByUser(tokenizedBusinessUser.getId(),businessPayment.getReferenceTransactionId(), PaymentGateway.PAYTABS);
			
			if(paymentTransaction!=null)
			{
				businessPayment.setTransactionStatus(PaymentTransactionStatus.getById(paymentTransaction.getStatus()).getStatusId());
				if(StringUtility.isStringPopulated(language))
				{
					if(StringUtility.stringsMatch(language, StringUtility.ENGLISH))
					{
						businessPayment.setStatusMessage(PaymentTransactionStatus.getById(paymentTransaction.getStatus()).getStatus());
					}
					else if(StringUtility.stringsMatch(language, StringUtility.ARABIC))
					{
						businessPayment.setStatusMessage(PaymentTransactionStatus.getById(paymentTransaction.getStatus()).getStatusAr());
					}
				}
			}

		return businessPayment;
	}
	
	
	private BusinessPayment preparePaymentInputs(PaymentTransaction paymentTransaction,BusinessUser tokenizedBusinessUser, BusinessPayment businessPayment,String language) {
		
		businessPayment.setTransactionId(paymentTransaction.getId());
		businessPayment.setUserPhone(tokenizedBusinessUser.getUserPhone());
		businessPayment.setCity(tokenizedBusinessUser.getCity());
		businessPayment.setCountry(tokenizedBusinessUser.getCountry());
		businessPayment.setEmailAddress(tokenizedBusinessUser.getEmailAddress());
		businessPayment.setLanguage(language);
		businessPayment.setFirstName(tokenizedBusinessUser.getFirstName());
		businessPayment.setLastName(tokenizedBusinessUser.getLastName());
		return businessPayment;
	}
	
	private void updateTransactionAfterGatewayCall(BusinessPayment businessPayment,PaymentTransaction paymentTransaction,PaymentTransactionStatus paymentTransactionStatus)
	{
		paymentTransaction.setStatus(paymentTransactionStatus.getStatusId());
		paymentTransaction.setReferenceTransactionId(businessPayment.getReferenceTransactionId());
		this.paymentService.updatePaymentTransaction(paymentTransaction);
		
	}
	
	public PaytabsCallbackRequest updateTransactionAfterGatewayCallback(PaytabsCallbackRequest paytabsCallbackRequest,String serial) throws BusinessException
	{
		
			this.logger.info("Serial:::"+serial);
			PaymentTransaction paymentTransaction=new PaymentTransaction();
			String valueToEncrypt="";
			String amountWithoutDecimalPoint="";
		
			try 
			{
			boolean areParamsPopulated=paytabsCallbackRequest!=null&&(StringUtility.isStringPopulated(paytabsCallbackRequest.getCartId())&&StringUtility.isStringPopulated(paytabsCallbackRequest.getCartAmount()));
			if(areParamsPopulated)
			{
			   amountWithoutDecimalPoint=StringUtility.generateAmountStringWithoutDecimalPoints(paytabsCallbackRequest.getCartAmount());
			}
			this.checkPaymentStatus(paytabsCallbackRequest.getTransactionReference(),Double.valueOf(paytabsCallbackRequest.getCartAmount()),paytabsCallbackRequest.getPaymentResult().getResponseStatus());
			if(StringUtility.isStringPopulated(serial))
			{
				this.logger.info("Serial populated:::");
				valueToEncrypt=areParamsPopulated?amountWithoutDecimalPoint:null;
				if(StringUtility.stringsMatch(serial,StringUtility.isStringPopulated(valueToEncrypt)?this.aes.ecryptWithoutSpecialCharacters(valueToEncrypt):null))
				{		
					try 
					{
						paymentTransaction=	this.findPaymentTransaction(paytabsCallbackRequest,Long.valueOf(paytabsCallbackRequest.getCartId()));
						if(paymentTransaction!=null)
						{
							this.populateThePaymentTransaction(paymentTransaction, paytabsCallbackRequest);
							this.paymentService.updatePaymentTransaction(paymentTransaction);
							this.execute(paymentTransaction);
						}
					}
					catch (Exception exception)
					{
						throw this.handleBusinessException(exception,ErrorCode.PAYMENT_TRANSACTION_NOT_FOUND);
					}
				}
				else
				{
					throw new BusinessException(ErrorCode.PAYMENT_TRANSACTION_NOT_FOUND);
				}
			}
			else
			{
				this.logger.info("Serial Not populated:::");
				/*Map<String,String> values=this.generateIdAndAmount(paytabsCallbackRequest.getCartId());
				if(values!=null)
				{
					paytabsCallbackRequest.setTransactionId(Long.valueOf(values.get(TRANSACTION_ID_PARAM)));
				}
				*/
				if(paytabsCallbackRequest!=null&&StringUtility.isStringPopulated(paytabsCallbackRequest.getCartId())) 
				{
					paytabsCallbackRequest.setTransactionId(Long.valueOf(paytabsCallbackRequest.getCartId()));
				}
				
				try 
				{
					paymentTransaction=this.findPaymentTransaction(paytabsCallbackRequest,paytabsCallbackRequest.getTransactionId());
					
					if(paymentTransaction!=null)
					{
						valueToEncrypt=areParamsPopulated?(paymentTransaction!=null?String.valueOf(paymentTransaction.getId())+"-"+amountWithoutDecimalPoint:null):null;
						if(StringUtility.stringsMatch(paytabsCallbackRequest.getCartId(),StringUtility.isStringPopulated(valueToEncrypt)?this.aes.encrypt(valueToEncrypt):null))
						{
							this.populateThePaymentTransaction(paymentTransaction, paytabsCallbackRequest);
							this.paymentService.updatePaymentTransaction(paymentTransaction);
							this.execute(paymentTransaction);
						}
					}
				}
				catch (Exception exception)
				{
					throw this.handleBusinessException(exception,ErrorCode.PAYMENT_TRANSACTION_NOT_FOUND);
				}
			}
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception, ErrorCode.PAYMENT_FAILURE);
		}
		
		return new PaytabsCallbackRequest();
		
	}
	
	private PaymentTransaction findPaymentTransaction(PaytabsCallbackRequest paytabsCallbackRequest,Long transactionId) throws BusinessException
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		try 
		{
			if(transactionId!=null)
			{
				this.logger.info("Get By Id");
				paymentTransaction=this.paymentService.getTransactionById(transactionId);
			}
			else
			{
				this.logger.info("Get By Reference Id");
				paymentTransaction=this.paymentService.getTransactionByReferenceId(paytabsCallbackRequest.getTransactionReference(), PaymentGateway.PAYTABS);
			}
		}
		catch(Exception exception)
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
			return null;
		}
		return paymentTransaction;

	}
	private void populateThePaymentTransaction(PaymentTransaction paymentTransaction, PaytabsCallbackRequest paytabsCallbackRequest)
	{
		
		paymentTransaction.setReferenceTransactionId(paytabsCallbackRequest.getTransactionReference());
		
		if(paymentTransaction!=null&&paytabsCallbackRequest!=null&&paytabsCallbackRequest.getPaymentResult()!=null)
		{
			if(paytabsCallbackRequest.getPaymentResult().getResponseStatus()!=null)
			{
				paymentTransaction.setStatus(paytabsCallbackRequest.getPaymentResult().getResponseStatus());					
			}
			if(paytabsCallbackRequest.getPaymentResult().getResponseMessage()!=null) 
			{
				paymentTransaction.setMessage(paytabsCallbackRequest.getPaymentResult().getResponseMessage());
			}
			if(paytabsCallbackRequest.getPaymentInfo()!=null&&StringUtility.isStringPopulated(paytabsCallbackRequest.getPaymentInfo().getPaymentMethod()))
			{
			paymentTransaction.setPaymentMethod(paytabsCallbackRequest.getPaymentInfo().getPaymentMethod());
			}
		}
	}
	
	private void checkPaymentStatus(String transactionReference,Double amount,String responseStatus) throws BusinessException 
	{
		try 
		{
			BusinessPayment queryBusinessPayment=(BusinessPayment) this.restContract.getData(restContract.paytabsQueryPaymentMapper, new BusinessPayment(transactionReference), null);
			if(queryBusinessPayment!=null)
			{
				if(!NumberUtility.areDoubleValuesMatching(amount, queryBusinessPayment.getAmount())
				   || StringUtility.stringsDontMatch(responseStatus, queryBusinessPayment.getTransactionStatus()))
				
					throw new BusinessException(ErrorCode.PAYMENT_TRANSACTION_CORRUPTED);
			}
			else
			{
				throw new BusinessException(ErrorCode.PAYMENT_TRANSACTION_NOT_FOUND);
			}
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);
		}
		
	}

	
	private void execute(PaymentTransaction paymentTransaction) throws IntegrationException, BusinessException, IOException,Exception
	{
		if(StringUtility.stringsMatch(paymentTransaction!=null?paymentTransaction.getStatus():null, StringUtility.PAYTABS_SUCCESS_STATUS))
		{
			this.logger.info("Payment Succeeded:::");
			if(NumberUtility.areIntegerValuesMatching(Action.INJECT.getActionId(), paymentTransaction.getAction()))
			{
				this.inject(paymentTransaction);
			}
		}
	}
	
	
	private void inject(PaymentTransaction paymentTransaction) throws IntegrationException, BusinessException, IOException,Exception
	{
		
			BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
			baseAzimutTrading.setOrderValue(paymentTransaction.getTransactionAmount());
			baseAzimutTrading.setCurrencyId(paymentTransaction.getCurrencyId());
		
			if(paymentTransaction!=null&&paymentTransaction.getKeyValueMap()!=null)
			{
				if(StringUtility.isStringPopulated(paymentTransaction.getKeyValueMap().get(StringUtility.BANK_ID)))
				{
					baseAzimutTrading.setBankId(Long.valueOf(paymentTransaction.getKeyValueMap().get(StringUtility.BANK_ID)));
				}
				if(StringUtility.isStringPopulated(paymentTransaction.getKeyValueMap().get(StringUtility.ACCOUNT_ID)))
				{
					baseAzimutTrading.setAccountId(Long.valueOf(paymentTransaction.getKeyValueMap().get(StringUtility.ACCOUNT_ID)));
				}
			}
			this.businessAzimutTradingService.inject(this.userMapper.convertBasicUnitToBusinessUnit(paymentTransaction.getUser()), baseAzimutTrading);

	}
	
	public void populateDynamicParameters(PaymentTransaction paymentTransaction,BusinessPayment businessPayment)
	{
		
		StringBuffer parameterNames=new StringBuffer();
		StringBuffer parameterValues=new StringBuffer();
		if(businessPayment!=null&&businessPayment.getAction()!=null)
		{
			if(NumberUtility.areIntegerValuesMatching(Action.INJECT.getActionId(), businessPayment.getAction()))
			{
				parameterNames.append(StringUtility.BANK_ID);
				parameterNames.append(StringUtility.COMMA);
				parameterNames.append(StringUtility.ACCOUNT_ID);
				parameterValues.append(String.valueOf(businessPayment.getBankId()));
				parameterValues.append(StringUtility.COMMA);
				parameterValues.append(String.valueOf(businessPayment.getAccountId()));
			}
			
			paymentTransaction.setParameterNames(parameterNames.toString());
			paymentTransaction.setParameterValues(parameterValues.toString());
		}
	}
	

	public  Map<String,String> generateIdAndAmount(String value)
	{
		this.logger.info("Value:::"+value);
		Map<String,String> stringMap=new HashMap<String,String>();
		if(StringUtility.isStringPopulated(value))
		{
			List<String>values= StringUtility.splitStringUsingCharacter(aes.decrypt(value),"-");
			this.logger.info("Values:::"+StringUtility.splitStringUsingCharacter(aes.decrypt(value),"-"));	
			stringMap.put(TRANSACTION_ID_PARAM,values.get(0));
			stringMap.put(AMOUNT_PARAM,values.get(1));
			
			return stringMap;
		}
		else
			return null;
	}
	
}
