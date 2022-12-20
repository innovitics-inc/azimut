package innovitics.azimut.rest.apis.teacomputers;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import innovitics.azimut.rest.models.teacomputers.TeaComputerResponse;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.security.TeaComputersSignatureGenerator;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.MyLogger;
import innovitics.azimut.rest.models.teacomputers.TeaComputerRequest; 


public abstract class RestTeaComputerApiConsumer <TeaComputerRequest, TeaComputerResponse, TeaComputerInput, TeaComputerOutput> 
extends AbstractBaseRestConsumer<TeaComputerRequest, TeaComputerResponse, TeaComputerInput, TeaComputerOutput>{

	@Autowired TeaComputersSignatureGenerator teaComputersSignatureGenerator;
	
	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("lang",locale);
		MyLogger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	
	protected abstract String generateSignature(TeaComputerRequest teaComputerRequest);
	protected abstract void generateResponseSignature(TeaComputerResponse teaComputerResponse)throws IntegrationException;
	protected abstract void generateResponseListSignature(TeaComputerResponse teaComputerResponse) throws IntegrationException;
	public  HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(TeaComputerInput teacomputersInput)
	{
		return null;
	}
	
	@Override
	public
	ResponseEntity<TeaComputerResponse> consumeRestAPI(HttpEntity<String> httpEntity,HttpMethod httpMethod,Class<TeaComputerResponse> clazz,String params,TeaComputerInput input) throws Exception,HttpClientErrorException, IntegrationException
	{					
		try {
		MyLogger.info("Tea Computer Rest::::");
		MyLogger.info("Request right before invocation::::"+httpEntity.toString());
		MyLogger.info("Method:::"+httpMethod);
		MyLogger.info("Class:::"+clazz.getName());
		final RestTemplate authRestTemplate = new RestTemplate();	
		authRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestWithBodyFactory());
		ResponseEntity<TeaComputerResponse> responseEntity=authRestTemplate.exchange(this.generateURL(params), httpMethod, httpEntity, clazz);
		return responseEntity;
		}
		catch(HttpClientErrorException clientErrorException)
		{
			MyLogger.info("An integration exception was caught:::");
			throw this.handleTeaComputerError(clientErrorException);
		}
			
	}
	
	
	
	protected IntegrationException handleTeaComputerError(HttpClientErrorException clientErrorException) throws IntegrationException
	{
		return this.handleError(clientErrorException);		
	}
	
	
	protected void populateCredentials(innovitics.azimut.rest.models.teacomputers.TeaComputerRequest request)
	{
		request.setUserName(this.configProperties.getTeaComputersUsername());
		request.setPassword(this.configProperties.getTeaComputersPassword());
	}
	

	@Override
	public String generateBaseURL(String params)
	{
		return this.configProperties.getTeaComputersUrl();
		
	}
	
	  
	private static final class HttpComponentsClientHttpRequestWithBodyFactory extends HttpComponentsClientHttpRequestFactory {
	  @Override 
	  protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) { 
	    if (httpMethod == HttpMethod.GET) { 
	      return new HttpGetRequestWithEntity(uri); 
	    } 
	    return super.createHttpUriRequest(httpMethod, uri); 
	  }
	}

	private static final class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
	  public HttpGetRequestWithEntity(final URI uri) {
	    super.setURI(uri);
	  }

	@Override
	public String getMethod() {
		return HttpMethod.GET.name();
	}
	  
	
	}
	
	public IntegrationException handleError(HttpClientErrorException httpClientErrorException)  {
		MyLogger.info("httpClientErrorException:::"+httpClientErrorException.toString());
		int errorCode=ErrorCode.FAILED_TO_INTEGRATE.getCode();
		String errorMessage="";
		innovitics.azimut.rest.models.teacomputers.TeaComputerResponse  teaComputerResponse=new innovitics.azimut.rest.models.teacomputers.TeaComputerResponse() ;
		ObjectMapper mapper = new ObjectMapper();
		try 
		{
			MyLogger.info("Parsing the exception to the teaComputerResponse:::");
			MyLogger.info("httpClientErrorException.getResponseBodyAsString():::"+httpClientErrorException.getResponseBodyAsString());
			teaComputerResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.teacomputers.TeaComputerResponse.class);
			errorMessage=teaComputerResponse.getMessage();
			if(StringUtility.isStringPopulated(teaComputerResponse.getErrorCode()))
			{
				errorCode=Integer.valueOf(teaComputerResponse.getErrorCode());
			}
			
			MyLogger.info("teaComputerResponse:::"+teaComputerResponse.toString());
		} catch (JsonProcessingException e) {
			MyLogger.info("Failed to Parse:::");
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,null, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 
}
	
	protected void populateResponse(String url,ResponseEntity<TeaComputerResponse> responseEntity)
	{
		
	};

	@Override
	public void transferFromInputToOutput(TeaComputerInput input,TeaComputerOutput output)
	{
		
	}
}
