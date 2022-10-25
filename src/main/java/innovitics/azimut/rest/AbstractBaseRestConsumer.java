package innovitics.azimut.rest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.errorhandling.RestErrorHandler;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;

@Service
public abstract class AbstractBaseRestConsumer<REQ,RES,I,O>
implements BaseRestConsumer<REQ,RES,I,O> {

	@Autowired protected ConfigProperties configProperties;
	@Autowired protected ExceptionHandler exceptionHandler;
	@Autowired protected ArrayUtility arrayUtility;
	@Autowired protected RestErrorHandler restErrorHandler;

	protected abstract void populateResponse(String url,ResponseEntity<RES> responseEntity);
	
	public O invoke(I input,Class<RES> clazz,String params) throws IntegrationException, HttpClientErrorException, Exception {
		logger.info("Input::" + input);
		ResponseEntity<RES> responseEntity=null;
		try {

			HttpEntity<String> httpEntity= this.generateRequestFromInput(input);
			
			HttpEntity<MultiValueMap<String, String>> mappedHttpEntity= this.generateMappedRequestFromInput(input);
			
			if(httpEntity!=null)
			{
				//logger.info("Request::" + httpEntity.toString());
				String url=this.generateURL(params);
				
				logger.info("URL:::"+url);
				
				//ResponseEntity<RES> responseEntity=this.consumeRestAPI(httpEntity, this.chooseHttpMethod(), );
				
				
				//ResponseEntity<RES> responseEntity=this.consumeRestAPI(httpEntity, this.chooseHttpMethod(), clazz,params);
				responseEntity=this.consumeRestAPI(httpEntity, this.chooseHttpMethod(), clazz,params);
				
				this.populateResponse(url, responseEntity);
				
				logger.info("Response::" + responseEntity!=null?responseEntity.toString():null);
				
				this.validateResponse(responseEntity);
				
				O output=this.generateOutPutFromResponse(responseEntity);
				logger.info("Output:::" +output!=null?output.toString():null);
				return output;
			}
			if(mappedHttpEntity!=null)
			{
				//logger.info("Request::" + mappedHttpEntity.toString());
				String url=this.generateURL(params);
				
				logger.info("URL:::"+url);
				
				//ResponseEntity<RES> responseEntity=this.consumeURLEncodedRequestRestAPI(mappedHttpEntity, this.chooseHttpMethod(), clazz,params);
				responseEntity=this.consumeURLEncodedRequestRestAPI(mappedHttpEntity, this.chooseHttpMethod(), clazz,params);
				logger.info("Response::" + responseEntity!=null?responseEntity.toString():null);
				
				this.validateResponse(responseEntity);
				
				O output=this.generateOutPutFromResponse(responseEntity);
				logger.info("Output:::" +output!=null?output.toString():null);
				return output;
	
			}
			
			
			return null;
			
		} 
		
		catch (Exception exception) 
		{
			if(this.exceptionHandler.isConnectionTimeOutException(exception))
			{
				throw new IntegrationException(ErrorCode.CONNECTION_TIMEOUT);
			}
			else
			{
				throw this.handleException(exception);
			}	
		}
	}
	
	
	
	
	protected boolean validateResponseStatus(ResponseEntity<RES> responseEntity) 
	{	boolean result=false;
		this.logger.info("Response Entity::::"+ responseEntity!=null?responseEntity.toString():null);
		this.logger.info("Response Body::::"+responseEntity.getBody()!=null?responseEntity.getBody().toString():null);
		boolean responseResult=responseEntity!=null&&responseEntity.getStatusCode()!=null&&responseEntity.getStatusCode().is2xxSuccessful()&&responseEntity.getBody()!=null;
		
		return responseResult;
	}
	
	@Override
	public
	ResponseEntity<RES> consumeRestAPI(HttpEntity<String> httpEntity,HttpMethod httpMethod,Class<RES> clazz,String params) throws Exception,HttpClientErrorException, IntegrationException
	{						
		//this.logger.info("Request right before invocation::::"+httpEntity.toString());
		this.logger.info("Method:::"+httpMethod);
		this.logger.info("Class:::"+clazz.getName());

		ResponseEntity<RES> responseEntity=this.restTemplate().exchange(this.generateURL(params), httpMethod, httpEntity, clazz);
		return responseEntity;
			
	}
	
	@Override
	public
	ResponseEntity<RES> consumeURLEncodedRequestRestAPI(HttpEntity<MultiValueMap<String, String>> httpEntity,HttpMethod httpMethod,Class<RES> clazz,String params) throws Exception,HttpClientErrorException, IntegrationException
	{						
		this.logger.info("URL Encoded Request right before invocation::::"+httpEntity);
		this.logger.info("Method:::"+httpMethod);
		this.logger.info("Class:::"+clazz.getName());

		ResponseEntity<RES> responseEntity=this.restTemplate().exchange(this.generateURL(params), httpMethod, httpEntity, clazz);
		return responseEntity;
			
	}
	@Bean
	protected RestTemplate restTemplate() {
		/*List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		//Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		// Note: here we are making this converter to process any kind of response, 
		// not only application/*json, which is the default behaviour
		
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
		messageConverters.add(converter);  
		RestTemplate restTemplate=new RestTemplate();
		
		restTemplate.setMessageConverters(messageConverters);*/ 
	    return new RestTemplate();
	}
	
	@Bean
	protected HttpHeaders httpHeaders() {
	    return new HttpHeaders();
	}
	
	protected  long getContentLength(REQ request) 
	{		
		ObjectMapper objectMapper = new ObjectMapper();
		String json ="";
	    try 
	    {
			json = objectMapper.writeValueAsString(request);
			this.logger.info("Json:::::"+json);
		} catch (JsonProcessingException e) 
	    {
			this.logger.info("Could not stringfy to json object");
			e.printStackTrace();
		}
		return json.length();
	}
	
	
	protected HttpEntity<String> stringfy(REQ request,HttpHeaders headers)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		String json ="";
	    try 
	    {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setSerializationInclusion(Include.NON_ABSENT);
			objectMapper.setSerializationInclusion(Include.NON_EMPTY);

			json = objectMapper.writeValueAsString(request);
			//this.logger.info("Json:::::"+json);
		} catch (JsonProcessingException e) 
	    {
			this.logger.info("Could not stringfy to json object");
			e.printStackTrace();
		}

	
		HttpEntity<String> httpEntity= new HttpEntity<String>(json, headers);
		return httpEntity;
	}
	
}
