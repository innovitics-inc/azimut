package innovitics.azimut.rest.apis.oldsystem;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.rest.models.old.AdminUserLoginResponse;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;


public abstract class RestOldSystemApiConsumer  <OldSystemRequest, OldSystemResponse, OldSystemInput, OldSystemOutput> 
extends AbstractBaseRestConsumer<OldSystemRequest, OldSystemResponse, OldSystemInput, OldSystemOutput> {


	@Override
	public HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(OldSystemInput input) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void validateResponse(ResponseEntity<OldSystemResponse> responseEntity) throws IntegrationException {
		if(responseEntity!=null&&responseEntity.getStatusCode()!=null&&!responseEntity.getStatusCode().is2xxSuccessful())
		{
			throw new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.validateExceptionType(exception);
	}


	@Override
	public String generateBaseURL(String params) {
		return this.configProperties.getAzimutUrl();
	}

	@Override
	public IntegrationException handleError(HttpClientErrorException httpClientErrorException) 
	{
		MyLogger.info("httpClientErrorException:::"+httpClientErrorException.toString());
		int errorCode=ErrorCode.FAILED_TO_INTEGRATE.getCode();
		String errorMessage="";
		innovitics.azimut.rest.models.old.OldSystemResponse oldSystemResponse=new innovitics.azimut.rest.models.old.OldSystemResponse();
		ObjectMapper mapper = new ObjectMapper();
		try {
			oldSystemResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.old.OldSystemResponse.class);
			errorMessage=oldSystemResponse.getMessage();
			{
				errorMessage=oldSystemResponse.getMessage();
			}

			MyLogger.info("oldSystemResponse:::"+oldSystemResponse.toString());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,null, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 

	}

	@Override
	protected void populateResponse(String url, ResponseEntity<OldSystemResponse> responseEntity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void transferFromInputToOutput(OldSystemInput input, OldSystemOutput output) {
		// TODO Auto-generated method stub
		
	}

}
