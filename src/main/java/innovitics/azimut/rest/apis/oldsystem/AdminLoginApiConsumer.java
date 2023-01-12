package innovitics.azimut.rest.apis.oldsystem;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.oldsystem.AdminUserLoginInput;
import innovitics.azimut.rest.entities.oldsystem.AdminUserLoginOutput;
import innovitics.azimut.rest.models.old.AdminUserLoginRequest;
import innovitics.azimut.rest.models.old.AdminUserLoginResponse;
import innovitics.azimut.utilities.logging.MyLogger;

@Service
public class AdminLoginApiConsumer extends  RestOldSystemApiConsumer<AdminUserLoginRequest, AdminUserLoginResponse, AdminUserLoginInput,AdminUserLoginOutput>{

	public static final String PATH="/adminLogin";
	@Override
	public HttpEntity<String> generateRequestFromInput(AdminUserLoginInput input) {

		AdminUserLoginRequest adminUserLoginRequest=new AdminUserLoginRequest(input.getUsername(),input.getPassword());	
		HttpEntity<String> httpEntity=this.stringfy(adminUserLoginRequest, this.generateHeaders(input.getLang(),this.getContentLength(adminUserLoginRequest)));
		return httpEntity;
		
	}
	@Override
	public AdminUserLoginOutput generateOutPutFromResponse(ResponseEntity<AdminUserLoginResponse> responseEntity) {
		return new AdminUserLoginOutput();
	}

	@Override
	public String generateURL(String params) {
		return this.generateBaseURL(params)+PATH;
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public Class<AdminUserLoginResponse> getResponseClassType() {
		return AdminUserLoginResponse.class;
	}
	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
			HttpHeaders headers=this.httpHeaders();
			headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setContentLength(contentLength);
			InetSocketAddress inetSocketAddress=null;
			try {
				inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),8090);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				MyLogger.error("Could not extract the Host Address");
			}

			headers.setHost(inetSocketAddress);
			MyLogger.info("Generated Headers:::"+headers.toString());
			return headers;
		}	
		
	@Override
	public
	ResponseEntity<AdminUserLoginResponse> consumeRestAPI(HttpEntity<?> httpEntity,HttpMethod httpMethod,Class<AdminUserLoginResponse> clazz,String params,AdminUserLoginInput input) throws Exception,HttpClientErrorException, IntegrationException
	{					
		MyLogger.info("Admin Login API:::::");
		this.restTemplate().getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<String> responseStringEntity = this.restTemplate().exchange(this.generateURL(params), this.chooseHttpMethod(), this.generateRequestFromInput(input),String.class);
        MyLogger.info("Response:::::"+responseStringEntity.toString());
        ResponseEntity<AdminUserLoginResponse> responseEntity=new ResponseEntity<AdminUserLoginResponse>(responseStringEntity.getStatusCode());
        
		return responseEntity;
			
	}


}
