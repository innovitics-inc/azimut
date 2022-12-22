package innovitics.azimut.rest.apis.firebase;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;

public abstract class RestFirebaseApiConsumer <FirebaseRequest, FirebaseResponse, FirebaseInput, FirebaseOutput>
extends AbstractBaseRestConsumer<FirebaseRequest, FirebaseResponse, FirebaseInput, FirebaseOutput>
{

	
	String addWebKey(String value)
	{
		return value+this.configProperties.getFirebaseWebKey();
	}
	
	@Override
	public HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(FirebaseInput input) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void validateResponse(ResponseEntity<FirebaseResponse> responseEntity) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HttpHeaders generateHeaders(String locale, long contentLength) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String generateBaseURL(String params) {
		return this.configProperties.getFirebaseUrl();
	}


	@Override
	protected void populateResponse(String url, ResponseEntity<FirebaseResponse> responseEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferFromInputToOutput(FirebaseInput input, FirebaseOutput output) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IntegrationException handleException(Exception exception) {
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
		innovitics.azimut.rest.models.firebase.FirebaseResponse  firebaseResponse=new innovitics.azimut.rest.models.firebase.FirebaseResponse() ;
		ObjectMapper mapper = new ObjectMapper();
		try 
		{
			MyLogger.info("Parsing the exception to the teaComputerResponse:::");
			MyLogger.info("httpClientErrorException.getResponseBodyAsString():::"+httpClientErrorException.getResponseBodyAsString());
			firebaseResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.firebase.FirebaseResponse.class);
			errorMessage=firebaseResponse.getMessage();
			errorCode=firebaseResponse.getCode();
			
			
			MyLogger.info("firebaseResponse:::"+firebaseResponse.toString());
		} catch (JsonProcessingException e) {
			MyLogger.info("Failed to Parse:::");
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,errorMessage, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 
	}	


}
