package innovitics.azimut.rest.apis.paytabs;

import java.util.Arrays;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentInput;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentOutput;
import innovitics.azimut.rest.entities.paytabs.PaytabsInput;
import innovitics.azimut.rest.entities.paytabs.PaytabsOutput;
import innovitics.azimut.rest.models.paytabs.InitiatePaymentResponse;
import innovitics.azimut.rest.models.paytabs.PaytabsRequest;
import innovitics.azimut.rest.models.paytabs.PaytabsResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public abstract class RestPaytabsApiConsumer <PaytabsRequest, PaytabsResponse, PaytabsInput, PaytabsOutput> 
extends AbstractBaseRestConsumer<PaytabsRequest, PaytabsResponse, PaytabsInput, PaytabsOutput> {

	
	private static final String TRANSACTION_TYPE="sale";
	private static final String TRANSACTION_CLASS="ecom";
	
	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(StringUtility.PAYTABS_AUTHORIZATION_HEADER,this.configProperties.getPaytabsServerKey());
		headers.add("lang",locale);
		logger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	@Override
	public String generateBaseURL(String params)
	{
		return this.configProperties.getPaytabsUrl();
	}
	
	protected void populateCredentials(innovitics.azimut.rest.models.paytabs.PaytabsRequest request,innovitics.azimut.rest.entities.paytabs.PaytabsInput input)
	{
		request.setProfileId(Integer.valueOf(this.configProperties.getPaytabsProfileId()));
		request.setPayPageLang(input.getPayPageLang());
		request.setTransType(TRANSACTION_TYPE);
		request.setTransClass(TRANSACTION_CLASS);
		request.setCallbackUrl(this.configProperties.getPaytabsCallBackUrl());
		request.setReturnUrl(this.configProperties.getPaytabsReturnUrl());
	}
	@Override
	public void transferFromInputToOutput(PaytabsInput input, PaytabsOutput output) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void validateResponse(ResponseEntity<PaytabsResponse> responseEntity) throws IntegrationException {
		if(!this.validateResponseStatus(responseEntity))
		{
			throw new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
	}
	
	public IntegrationException handleError(HttpClientErrorException httpClientErrorException)  {
		this.logger.info("httpClientErrorException:::"+httpClientErrorException.toString());
		int errorCode=ErrorCode.FAILED_TO_INTEGRATE.getCode();
		String errorMessage="";
		innovitics.azimut.rest.models.paytabs.PaytabsResponse  paytabsResponse=new innovitics.azimut.rest.models.paytabs.PaytabsResponse() ;
		ObjectMapper mapper = new ObjectMapper();
		try 
		{
			this.logger.info("Parsing the exception to the teaComputerResponse:::");
			this.logger.info("httpClientErrorException.getResponseBodyAsString():::"+httpClientErrorException.getResponseBodyAsString());
			paytabsResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.paytabs.PaytabsResponse.class);
			errorMessage=paytabsResponse.getMessage();
			errorCode=paytabsResponse.getCode();
			
			
			this.logger.info("paytabsResponse:::"+paytabsResponse.toString());
		} catch (JsonProcessingException e) {
			this.logger.info("Failed to Parse:::");
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,errorMessage, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 
	}

	@Override
	public IntegrationException handleException(Exception exception) 
	{
		this.logger.info("Handling the Exception in the Get Company Bank Accounts API:::");
		if(exception instanceof IntegrationException)
		{
			IntegrationException integrationException=(IntegrationException)exception;			
			return integrationException;
		}
		else
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
	}
	
	@Override
	protected void populateResponse(String url, ResponseEntity<PaytabsResponse> responseEntity) {
		// TODO Auto-generated method stub
		
	}
}
