package innovitics.azimut.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.exceptions.IntegrationException;


	public  interface BaseRestConsumer <REQ , RES ,I ,O > {
	
	public final static Logger logger = LogManager.getLogger(BaseRestConsumer.class.getName());
	
	HttpEntity<String> generateRequestFromInput(I input);
	HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(I input);	
	O generateOutPutFromResponse(ResponseEntity<RES> responseEntity);
	
	ResponseEntity<RES> consumeRestAPI(HttpEntity<String> httpEntity,HttpMethod httpMethod,Class<RES> clazz,String params) throws Exception,HttpClientErrorException, IntegrationException;
	
	ResponseEntity<RES> consumeURLEncodedRequestRestAPI(HttpEntity<MultiValueMap<String, String>> httpEntity,HttpMethod httpMethod,Class<RES> clazz,String params) throws Exception,HttpClientErrorException, IntegrationException;
	
	void validateResponse(ResponseEntity<RES> responseEntity) throws IntegrationException;
	
	HttpHeaders generateHeaders(String locale,long contentLength);
	
	IntegrationException handleException(Exception exception);
		
	String generateURL(String params);
	
	HttpMethod chooseHttpMethod();
	
}
