package innovitics.azimut.businessservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.rest.mappers.PaytabsInitiatePaymentMapper;
import innovitics.azimut.services.payment.PaymentService;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.PaymentTransactionStatus;

@Service
public class BusinessPaymentService extends AbstractBusinessService<BusinessPayment>{

	@Autowired PaymentService paymentService;
	@Autowired PaytabsInitiatePaymentMapper paytabsinitiatePaymentMapper;
	public BusinessPayment initiatePayment(BusinessPayment businessPayment,BusinessUser tokenizedBusinessUser,String language) throws IntegrationException
	{
		PaymentTransaction paymentTransaction=this.paymentService.addPayment(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false), businessPayment.getAmount(), PaymentGateway.PAYTABS);
		businessPayment=(BusinessPayment)this.restContract.getData(paytabsinitiatePaymentMapper, this.preparePaymentInputs(tokenizedBusinessUser,businessPayment,language), null);
		this.updateTransactionAfterGatewayCall(businessPayment, paymentTransaction);
		return businessPayment;
	}
	private BusinessPayment preparePaymentInputs(BusinessUser tokenizedBusinessUser, BusinessPayment businessPayment,String language) {
		
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
		paymentTransaction.setStatus(PaymentTransactionStatus.PENDING_AT_GATEWAY.getStatusId());
		paymentTransaction.setReferenceTransactionId(businessPayment.getReferenceTransactionId());
		this.paymentService.updateTransaction(paymentTransaction);
		
	}
	
}
