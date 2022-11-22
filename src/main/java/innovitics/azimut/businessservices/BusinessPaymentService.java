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
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class BusinessPaymentService extends AbstractBusinessService<BusinessPayment>{

	@Autowired PaymentService paymentService;
	@Autowired PaytabsInitiatePaymentMapper paytabsinitiatePaymentMapper;
	public BusinessPayment initiatePayment(BusinessPayment businessPayment,BusinessUser tokenizedBusinessUser,String language) throws IntegrationException, BusinessException
	{
		try
		{
			PaymentTransaction paymentTransaction=this.paymentService.addPayment(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false), businessPayment.getAmount(), PaymentGateway.PAYTABS);
		
			businessPayment=(BusinessPayment)this.restContract.getData(paytabsinitiatePaymentMapper, this.preparePaymentInputs(paymentTransaction,tokenizedBusinessUser,businessPayment,language), null);
		
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
	
	public PaytabsCallbackRequest updateTransactionAfterGatewayCallback(PaytabsCallbackRequest paytabsCallbackRequest) throws BusinessException
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		
		try 
		{
			paymentTransaction=this.paymentService.getTransactionByReferneceId(paytabsCallbackRequest.getTransactionReference(), PaymentGateway.PAYTABS);
			if(paymentTransaction!=null&&paytabsCallbackRequest!=null&&paytabsCallbackRequest.getPaymentInfo()!=null)
			{
				if(paytabsCallbackRequest.getPaymentResult().getResponseStatus()!=null)
				{
					paymentTransaction.setStatus(paytabsCallbackRequest.getPaymentResult().getResponseStatus());
					this.paymentService.updateTransaction(paymentTransaction);
				}
			}
		
		}
		catch (Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.PAYMENT_TRANSACTION_NOT_FOUND);
		}
		
		return new PaytabsCallbackRequest();
		
	}
	
}
