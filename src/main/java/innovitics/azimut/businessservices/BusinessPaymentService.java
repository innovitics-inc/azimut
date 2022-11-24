package innovitics.azimut.businessservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.businessmodels.payment.PaytabsCallbackRequest;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.rest.mappers.PaytabsInitiatePaymentMapper;
import innovitics.azimut.services.payment.PaymentService;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.PaymentTransactionStatus;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@SuppressWarnings("unchecked")
@Service
public class BusinessPaymentService extends AbstractBusinessService<BusinessPayment>{

	@Autowired PaymentService paymentService;
	
	public BusinessPayment initiatePayment(BusinessPayment businessPayment,BusinessUser tokenizedBusinessUser,String language) throws IntegrationException, BusinessException
	{
		try
		{
			PaymentTransaction paymentTransaction=this.paymentService.addPayment(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false), businessPayment.getAmount(), PaymentGateway.PAYTABS);
		
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
		this.paymentService.updateTransaction(paymentTransaction);
		
	}
	
	public PaytabsCallbackRequest updateTransactionAfterGatewayCallback(PaytabsCallbackRequest paytabsCallbackRequest,String serial) throws BusinessException
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		
		boolean areParamsPopulated=paytabsCallbackRequest!=null&&(StringUtility.isStringPopulated(paytabsCallbackRequest.getCartId())&&StringUtility.isStringPopulated(paytabsCallbackRequest.getCartAmount()));
		String valueToHash=areParamsPopulated?paytabsCallbackRequest.getCartId()+paytabsCallbackRequest.getCartAmount():null;
		if(StringUtility.stringsMatch(serial, this.aes.hashString(valueToHash)))
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
				this.paymentService.updateTransaction(paymentTransaction);

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
	
}
