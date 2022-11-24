package innovitics.azimut.rest.apis.paytabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentInput;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentOutput;
import innovitics.azimut.rest.models.paytabs.CustomerDetails;
import innovitics.azimut.rest.models.paytabs.InitiatePaymentRequest;
import innovitics.azimut.rest.models.paytabs.InitiatePaymentResponse;
import innovitics.azimut.rest.models.paytabs.ShippingDetails;
import innovitics.azimut.rest.models.teacomputers.AddClientBankAccountRequest;
import innovitics.azimut.security.AES;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class InitiatePaymentApiConsumer extends RestPaytabsApiConsumer<InitiatePaymentRequest, InitiatePaymentResponse, InitiatePaymentInput, InitiatePaymentOutput>{

	@Autowired AES aes;
	private static final String PATH="/payment/request";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(InitiatePaymentInput input) {
		
		InitiatePaymentRequest request=new InitiatePaymentRequest();
		this.populateCredentials(request, input);
		request.setCartId(input.getCartId());
		request.setCartAmount(input.getCartAmount());
		request.setCartCurrency(input.getCartCurrency());
		request.setCartDescription(input.getCartDescription());
		CustomerDetails customerDetails=new CustomerDetails();
		customerDetails.setCity(input.getCity());
		customerDetails.setCountry(input.getCountry());
		customerDetails.setEmail(input.getEmail());
		customerDetails.setName(input.getName());
		customerDetails.setPhone(input.getPhone());
		customerDetails.setStreet(input.getStreet());
		customerDetails.setZip(input.getZip());
		request.setCustomerDetails(customerDetails);
		ShippingDetails shippingDetails=new ShippingDetails();
		shippingDetails.setCity(input.getCity());
		shippingDetails.setCountry(input.getCountry());
		shippingDetails.setEmail(input.getEmail());
		shippingDetails.setName(input.getName());
		shippingDetails.setPhone(input.getPhone());
		shippingDetails.setStreet(input.getStreet());
		shippingDetails.setZip(input.getZip());
		request.setShippingDetails(shippingDetails);
		request.setCallbackUrl(this.configProperties.getPaytabsCallBackUrl()+"?"+StringUtility.TRANSACTION_SERIAL_PARAM_NAME+"="+this.generateSerial(input));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getPayPageLang(), this.getContentLength(request)));
		return httpEntity;
	}


	@Override
	public InitiatePaymentOutput generateOutPutFromResponse(ResponseEntity<InitiatePaymentResponse> responseEntity) {
		InitiatePaymentOutput initiatePaymentOutput=new InitiatePaymentOutput();
		initiatePaymentOutput.setRedirectUrl(responseEntity.getBody().getRedirectUrl());
		initiatePaymentOutput.setReferenceTransaction(responseEntity.getBody().getTransactionReference());
		return initiatePaymentOutput;
	}


	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public Class<InitiatePaymentResponse> getResponseClassType() {
		return InitiatePaymentResponse.class;
	}

	String generateSerial(InitiatePaymentInput initiatePaymentInput) 
	{
		if(initiatePaymentInput!=null&&StringUtility.isStringPopulated(initiatePaymentInput.getCartId())&&initiatePaymentInput.getCartAmount()!=null)
		{
			return aes.hashString(initiatePaymentInput.getCartId()+String.valueOf(initiatePaymentInput.getCartAmount()));
		}
		else
		{
			return null;
		}
	}




}
