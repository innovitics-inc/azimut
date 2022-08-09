package innovitics.azimut.rest.apis.valify;

import java.util.Arrays;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public abstract class RestValifyApiConsumer  <ValifyRequest, ValifyResponse, ValifyInput, ValifyOutput> 
extends AbstractBaseRestConsumer<ValifyRequest, ValifyResponse, ValifyInput, ValifyOutput> {
	
	public static final String NON_MATHCING_ERROR="5039";
	public static final String BUNDLE_ERROR="5001";
	
	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(locale);
		logger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	public HttpHeaders generateHeaders(String locale,long contentLength,String token) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		logger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	
	public abstract HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(ValifyInput valifyInput);
	
	
	
	
	protected IntegrationException handleValifyError(String errorMessage,String errorCode) throws IntegrationException
	{
			this.logger.info("Error Message:::"+ errorMessage);
			this.logger.info("Error Code:::"+ errorCode);
			IntegrationException integrationException =new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
			integrationException.setErrorMessage(errorMessage);
			return integrationException;		
	}
	

	@Override
	public String generateURL(String params)
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
		this.logger.info("Stack trace:::");
		
		exception.printStackTrace();
		
		if(exception instanceof IntegrationException)
		{
			return (IntegrationException)exception;
		}
		
		if(exception instanceof HttpClientErrorException)
		{
			
			String errorMessage=exception.getMessage();
			int errorCode=ErrorCode.FAILED_TO_INTEGRATE.getCode();
			
			if(StringUtility.isStringPopulated(errorMessage)&&errorMessage.contains(NON_MATHCING_ERROR))
			{
				errorCode=ErrorCode.IMAGES_NOT_SIMIILAR.getCode();
				errorMessage=ErrorCode.IMAGES_NOT_SIMIILAR.getMessage();
			}
			
			if(StringUtility.isStringPopulated(errorMessage)&&errorMessage.contains(BUNDLE_ERROR))
			{
				errorCode=ErrorCode.PAYMENT_FAILURE.getCode();
				errorMessage=ErrorCode.PAYMENT_FAILURE.getMessage();
			}
			
			IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage, errorMessage, exception.getStackTrace());
			return  integrationException;
		}
		
		
		return  new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
	
	}
}
