package innovitics.azimut.rest.apis.teacomputers;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

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

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.security.TeaComputersSignatureGenerator;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
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
		logger.info("Generated Headers:::"+headers.toString());
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
	ResponseEntity<TeaComputerResponse> consumeRestAPI(HttpEntity<String> httpEntity,HttpMethod httpMethod,Class<TeaComputerResponse> clazz,String params) throws Exception,HttpClientErrorException, IntegrationException
	{					
		try {
		this.logger.info("Tea Computer Rest::::");
		this.logger.info("Request right before invocation::::"+httpEntity.toString());
		this.logger.info("Method:::"+httpMethod);
		this.logger.info("Class:::"+clazz.getName());
		final RestTemplate authRestTemplate = new RestTemplate();	
		authRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestWithBodyFactory());
		ResponseEntity<TeaComputerResponse> responseEntity=authRestTemplate.exchange(this.generateURL(params), httpMethod, httpEntity, clazz);
		return responseEntity;
		}
		catch(HttpClientErrorException clientErrorException)
		{
			this.logger.info("An integration exception was caught:::");
			throw this.handleTeaComputerError(clientErrorException.getMessage(), clientErrorException.getStatusCode().toString());
		}
			
	}
	
	
	
	protected IntegrationException handleTeaComputerError(String errorMessage,String errorCode) throws IntegrationException
	{
			this.logger.info("Error Message:::"+ errorMessage);
			this.logger.info("Error Code:::"+ errorCode);
			IntegrationException integrationException =new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
			integrationException.setErrorMessage(errorMessage);
			return integrationException;		
	}
	

	protected void populateCredentials(innovitics.azimut.rest.models.teacomputers.TeaComputerRequest request)
	{
		request.setUserName(this.configProperties.getTeaComputersUsername());
		request.setPassword(this.configProperties.getTeaComputersPassword());
	}
	
	@Override
	public String generateURL(String params)
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
	
	
}
