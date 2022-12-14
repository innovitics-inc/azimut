package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.paytabs.InitiatePaymentApiConsumer;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentInput;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentOutput;
import innovitics.azimut.rest.models.paytabs.InitiatePaymentRequest;
import innovitics.azimut.rest.models.paytabs.InitiatePaymentResponse;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.crosslayerenums.PaymentTransactionStatus;

@Component
public class PaytabsInitiatePaymentMapper extends RestMapper<InitiatePaymentInput, InitiatePaymentOutput, InitiatePaymentRequest, InitiatePaymentResponse, BusinessPayment>{

	@Autowired InitiatePaymentApiConsumer  initiatePaymentApiConsumer;
	
	@Override
	BusinessPayment consumeRestService(BusinessPayment businessPayment, String params)
			throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessPayment> consumeListRestService(BusinessPayment businessPayment, String params)
			throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	InitiatePaymentInput createInput(BusinessPayment businessPayment) {
		InitiatePaymentInput initiatePaymentInput=new InitiatePaymentInput();
		initiatePaymentInput.setCartId((businessPayment!=null&&businessPayment.getTransactionId()!=null)?String.valueOf(businessPayment.getTransactionId()):null);
		
		initiatePaymentInput.setCartAmount(businessPayment.getAmount());
		initiatePaymentInput.setCartCurrency(CurrencyType.getById(businessPayment.getCurrencyId()).getType());
		initiatePaymentInput.setCity(businessPayment.getCity());
		initiatePaymentInput.setCountry(businessPayment.getCountry());
		initiatePaymentInput.setZip(null);
		initiatePaymentInput.setEmail(businessPayment.getEmailAddress());
		initiatePaymentInput.setName(businessPayment.getFirstName()+" "+businessPayment.getLastName());
		initiatePaymentInput.setPayPageLang(businessPayment.getLanguage());
		initiatePaymentInput.setPhone(businessPayment.getUserPhone());
		
		initiatePaymentInput.setShippingCity(businessPayment.getCity());
		initiatePaymentInput.setShippingCountry(businessPayment.getCountry());
		initiatePaymentInput.setShippingZip(null);
		initiatePaymentInput.setShippingEmail(businessPayment.getEmailAddress());
		initiatePaymentInput.setShippingPhone(businessPayment.getUserPhone());
		initiatePaymentInput.setCartDescription("Cart with transaction id"+" "+initiatePaymentInput.getCartId()+" from Innovitics");
		initiatePaymentInput.setReturnUrl(businessPayment.getReturnUrl());
		
		return initiatePaymentInput;
	}

	@Override
	BusinessPayment createBusinessEntityFromOutput(InitiatePaymentOutput initiatePaymentOutput) {
		BusinessPayment businessPayment=new BusinessPayment();
		businessPayment.setReferenceTransactionId(initiatePaymentOutput.getReferenceTransaction());
		businessPayment.setRedirectUrl(initiatePaymentOutput.getRedirectUrl());
		return businessPayment;
	}

	@Override
	protected List<BusinessPayment> createListBusinessEntityFromOutput(InitiatePaymentOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setConsumer(BusinessPayment businessPayment) {
		this.consumer=initiatePaymentApiConsumer;
		
	}

}
