package innovitics.azimut.rest.apis.valify;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.valify.ValifyAccessTokenInput;
import innovitics.azimut.rest.entities.valify.ValifyAccessTokenOutput;
import innovitics.azimut.rest.entities.valify.ValifyInput;
import innovitics.azimut.rest.models.valify.ValifyAccessTokenRequest;
import innovitics.azimut.rest.models.valify.ValifyAccessTokenResponse;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.MyLogger;
@Service
public class ValifyAccessTokenApiConsumer extends RestValifyApiConsumer<ValifyAccessTokenRequest, ValifyAccessTokenResponse,ValifyAccessTokenInput ,ValifyAccessTokenOutput> {

	final static String PATH="/o/token/";
	@Override
	public HttpEntity<String> generateRequestFromInput(ValifyAccessTokenInput input) {
		
		return null;
	}
	@Override
	public ValifyAccessTokenOutput generateOutPutFromResponse(ResponseEntity<ValifyAccessTokenResponse> responseEntity) {
		ValifyAccessTokenOutput output=new ValifyAccessTokenOutput();
		ValifyAccessTokenResponse response=responseEntity.getBody();
		
		output.setAccessToken(response.getAccess_token());
		output.setExpiresIn(response.getExpires_in());
		output.setTokenType(response.getToken_type());
		output.setScope(response.getScope());
		output.setRefreshToken(response.getRefresh_token());
		MyLogger.info("ValifyAccessTokenOutput:::"+output.toString());
		return output;
		
	}

	@Override
	public void validateResponse(ResponseEntity<ValifyAccessTokenResponse> responseEntity) throws IntegrationException {
		if(!this.validateResponseStatus(responseEntity))
		{
			if(responseEntity!=null&&responseEntity.getBody()!=null)
			{
				throw this.populateIntegrationException(responseEntity.getBody());
			}
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.validateExceptionType(exception);
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
		HttpHeaders headers=this.httpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MyLogger.info("Generated Headers:::"+headers.toString());
		return headers;
	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
		
	}

	@Override
	public HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(ValifyAccessTokenInput input) {
MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		
		map.add("grant_type","password");
		map.add("username",this.configProperties.getValifyUsername());
		map.add("password",this.configProperties.getValifyPassword());
		map.add("client_id",this.configProperties.getValifyClientId());
		map.add("client_secret",this.configProperties.getValifyClientSecret());
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, this.generateHeaders(null, 0));
		return httpEntity;
	}
	@Override
	public Class<ValifyAccessTokenResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return ValifyAccessTokenResponse.class;
	}
}
