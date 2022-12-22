package innovitics.azimut.rest.apis.valify;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
import innovitics.azimut.rest.models.valify.ValifyResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;

public abstract class RestValifyApiConsumer  <ValifyRequest, ValifyResponse, ValifyInput, ValifyOutput> 
extends AbstractBaseRestConsumer<ValifyRequest, ValifyResponse, ValifyInput, ValifyOutput> {
	
	public static final String NON_MATHCING_ERROR="5039";
	public static final String BUNDLE_ERROR="5001";
	public static final String OCR_ERROR="Please make sure there is no glare, no shadows";
	
	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(locale);
		MyLogger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	public HttpHeaders generateHeaders(String locale,long contentLength,String token) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		MyLogger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	
	public abstract HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(ValifyInput valifyInput);
	
	protected void populateResponse(String url,ResponseEntity<ValifyResponse> responseEntity)
	{
		
	};

	
	
	
	protected IntegrationException handleValifyError(String errorMessage,String errorCode) throws IntegrationException
	{
			MyLogger.info("Error Message:::"+ errorMessage);
			MyLogger.info("Error Code:::"+ errorCode);
			IntegrationException integrationException =new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
			integrationException.setErrorMessage(errorMessage);
			return integrationException;		
	}
	

	@Override
	public String generateBaseURL(String params)
	{
		return this.configProperties.getValifyUrl();
		
	}
	
	protected void populateGeneralRequestDetails(innovitics.azimut.rest.models.valify.ValifyRequest request,innovitics.azimut.rest.models.valify.ValifyData data,innovitics.azimut.rest.entities.valify.ValifyInput input) 
	{
	
		if(input!=null&&data!=null)
		{
			data.setLang(input.getLang());
		}
		
		if(input!=null&&request!=null)
		{
			request.setLang(input.getLang());
		}
	}
	
	protected void populateGeneralResponseDetails(innovitics.azimut.rest.models.valify.ValifyResponse response,innovitics.azimut.rest.entities.valify.ValifyOutput valifyOutput) 
	{
	
		if(response!=null)
		{
			valifyOutput.setTransactionId(response.getTransaction_id());
			valifyOutput.setTrialsRemaining(response.getTrials_remaining());
		}
	}
	protected IntegrationException populateIntegrationException(innovitics.azimut.rest.models.valify.ValifyResponse response) 
	{
		IntegrationException integrationException=new IntegrationException();
		integrationException.setErrorCode(Integer.valueOf(response.getError_code()));
		integrationException.setErrorMessage(response.getMessage());
		integrationException.setTransactionId(response.getTransaction_id());
		return integrationException;
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
		innovitics.azimut.rest.models.valify.ValifyResponse  valifyResponse=new innovitics.azimut.rest.models.valify.ValifyResponse();
		ObjectMapper mapper = new ObjectMapper();
		try {
			valifyResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.valify.ValifyResponse.class);
			errorMessage=valifyResponse.getMessage();
			
			if(StringUtility.isStringPopulated(errorMessage)&&errorMessage.contains(BUNDLE_ERROR))
			{
				errorCode=ErrorCode.PAYMENT_FAILURE.getCode();
				errorMessage=ErrorCode.PAYMENT_FAILURE.getMessage();
			}

			else if(StringUtility.isStringPopulated(errorMessage)&&errorMessage.contains(OCR_ERROR))
			{
				errorCode=ErrorCode.IMAGES_NOT_ClEAR.getCode();								
			}
			else
			{
				errorCode=Integer.valueOf(valifyResponse.getError_code());
				errorMessage=valifyResponse.getMessage();
			}

			MyLogger.info("valifyResponse:::"+valifyResponse.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,null, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 
}
	@Override
	public void transferFromInputToOutput(ValifyInput input,ValifyOutput output)
	{
		
	}
}
