package innovitics.azimut.businessservices;

import java.io.IOException;

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

@SuppressWarnings("unchecked")
@Service
public class BusinessPaymentService extends AbstractBusinessService<BusinessPayment>{

	@Autowired BusinessAzimutTradingService businessAzimutTradingService;
	
	public BusinessPayment initiatePayment(BusinessPayment businessPayment,BusinessUser tokenizedBusinessUser,String language) throws IntegrationException, BusinessException
	{
		try
		{
			PaymentTransaction paymentTransaction=new PaymentTransaction();
			this.populateDynamicParameters(paymentTransaction, businessPayment);
			paymentTransaction = this.paymentService.addPaymentTransaction(
					this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false),
					businessPayment.getAmount(), PaymentGateway.PAYTABS, businessPayment.getCurrencyId(),
					businessPayment.getAction(), paymentTransaction.getParameterNames(),
					paymentTransaction.getParameterValues());
		
			businessPayment=(BusinessPayment)this.restContract.getData(this.restContract.paytabsInitiatePaymentMapper, this.preparePaymentInputs(paymentTransaction,tokenizedBusinessUser,businessPayment,language), null);
		
			this.updateTransactionAfterGatewayCall(businessPayment, paymentTransaction);
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);
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
	
	private void updateTransactionAfterGatewayCall(BusinessPayment businessPayment,PaymentTransaction paymentTransaction)
	{
		paymentTransaction.setStatus(PaymentTransactionStatus.PG.getStatusId());
		paymentTransaction.setReferenceTransactionId(businessPayment.getReferenceTransactionId());
		this.paymentService.updatePaymentTransaction(paymentTransaction);
		
	}
	
	public PaytabsCallbackRequest updateTransactionAfterGatewayCallback(PaytabsCallbackRequest paytabsCallbackRequest,String serial) throws BusinessException
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		String valueToEncrypt="";
		boolean areParamsPopulated=paytabsCallbackRequest!=null&&(StringUtility.isStringPopulated(paytabsCallbackRequest.getCartId())&&StringUtility.isStringPopulated(paytabsCallbackRequest.getCartAmount()));
		if(areParamsPopulated)
		{
			String amountWithoutDecimalPoint=(StringUtility.splitStringUsingCharacter(String.valueOf(paytabsCallbackRequest.getCartAmount()), "\\.")).get(0);
			valueToEncrypt=areParamsPopulated?amountWithoutDecimalPoint:null;
		}
		if(StringUtility.stringsMatch(serial,StringUtility.isStringPopulated(valueToEncrypt)?this.aes.encrypt(valueToEncrypt):null))
		{
			this.checkPaymentStatus(paytabsCallbackRequest.getTransactionReference(),Double.valueOf(paytabsCallbackRequest.getCartAmount()),paytabsCallbackRequest.getPaymentResult().getResponseStatus());
			try 
			{
				if(StringUtility.isStringPopulated(paytabsCallbackRequest.getCartId()))
				{
					paymentTransaction=this.paymentService.getTransactionByReferenceId(paytabsCallbackRequest.getTransactionReference(), PaymentGateway.PAYTABS,Long.valueOf(paytabsCallbackRequest.getCartId()));
				}
				else
				{
					paymentTransaction=this.paymentService.getTransactionByReferenceId(paytabsCallbackRequest.getTransactionReference(), PaymentGateway.PAYTABS);
				}
			
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
				this.paymentService.updatePaymentTransaction(paymentTransaction);
				this.execute(paymentTransaction);
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
		
		return new PaytabsCallbackRequest();
		
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
		if(true/*StringUtility.stringsMatch(paymentTransaction!=null?paymentTransaction.getStatus():null, StringUtility.EXCLUDED_STATUSES[0])*/)
		{
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
	
}
