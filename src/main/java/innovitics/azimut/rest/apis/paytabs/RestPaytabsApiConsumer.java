package innovitics.azimut.rest.apis.paytabs;

import java.util.Arrays;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentInput;
import innovitics.azimut.rest.entities.paytabs.InitiatePaymentOutput;
import innovitics.azimut.rest.entities.paytabs.PaytabsInput;
import innovitics.azimut.rest.entities.paytabs.PaytabsOutput;
import innovitics.azimut.rest.entities.paytabs.QueryPaymentInput;
import innovitics.azimut.rest.models.paytabs.InitiatePaymentResponse;
import innovitics.azimut.rest.models.paytabs.PaytabsRequest;
import innovitics.azimut.rest.models.paytabs.PaytabsResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.MyLogger;

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
		MyLogger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	@Override
	public String generateBaseURL(String params)
	{
		return this.configProperties.getPaytabsUrl();
	}
	
	@Override
	public HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(PaytabsInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void populateCredentials(innovitics.azimut.rest.models.paytabs.PaytabsRequest request,innovitics.azimut.rest.entities.paytabs.PaytabsInput input)
	{
		request.setProfileId(Integer.valueOf(this.configProperties.getPaytabsProfileId()));
		request.setPayPageLang(input.getPayPageLang());
		request.setTransType(TRANSACTION_TYPE);
		request.setTransClass(TRANSACTION_CLASS);
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
	
	@Override
	public IntegrationException handleException(Exception exception) 
	{
		return this.validateExceptionType(exception);
	}
	protected IntegrationException validateExceptionType(Exception exception)
	{
		MyLogger.info("Stack trace:::");
		
		exception.printStackTrace();
		
		if(exception instanceof IntegrationException)
		{
			return (IntegrationException)exception;
		}
		
		if(exception instanceof HttpClientErrorException)
		{
			
			IntegrationException integrationException=this.handleError((HttpClientErrorException)exception);			
			return  integrationException;
		}
		
		
		return  new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
	
	}	
	public IntegrationException handleError(HttpClientErrorException httpClientErrorException)  {
		MyLogger.info("httpClientErrorException:::"+httpClientErrorException.toString());
		int errorCode=ErrorCode.FAILED_TO_INTEGRATE.getCode();
		String errorMessage="";
		innovitics.azimut.rest.models.paytabs.PaytabsResponse  paytabsResponse=new innovitics.azimut.rest.models.paytabs.PaytabsResponse() ;
		ObjectMapper mapper = new ObjectMapper();
		try 
		{
			MyLogger.info("Parsing the exception to the teaComputerResponse:::");
			MyLogger.info("httpClientErrorException.getResponseBodyAsString():::"+httpClientErrorException.getResponseBodyAsString());
			paytabsResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.paytabs.PaytabsResponse.class);
			errorMessage=paytabsResponse.getMessage();
			errorCode=paytabsResponse.getCode();
			
			
			MyLogger.info("paytabsResponse:::"+paytabsResponse.toString());
		} catch (JsonProcessingException e) {
			MyLogger.info("Failed to Parse:::");
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,errorMessage, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 
	}	
	@Override
	protected void populateResponse(String url, ResponseEntity<PaytabsResponse> responseEntity) {
		// TODO Auto-generated method stub
		
	}
}
