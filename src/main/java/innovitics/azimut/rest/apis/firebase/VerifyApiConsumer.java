package innovitics.azimut.rest.apis.firebase;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.rest.entities.firebase.VerifyInput;
import innovitics.azimut.rest.entities.firebase.VerifyOutput;
import innovitics.azimut.rest.models.firebase.VerifyRequest;
import innovitics.azimut.rest.models.firebase.VerifyResponse;

@Service
public class VerifyApiConsumer extends RestFirebaseApiConsumer<VerifyRequest,VerifyResponse, VerifyInput, VerifyOutput> {

	private static final String PATH=":signInWithPhoneNumber?key=";
	@Override
	public HttpEntity<String> generateRequestFromInput(VerifyInput input) {
		VerifyRequest request=new VerifyRequest();
		request.setCode(input.getCode());
		request.setSessionInfo(input.getSessionInfo());
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(null, this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public VerifyOutput generateOutPutFromResponse(ResponseEntity<VerifyResponse> responseEntity) {
		
		return new VerifyOutput();
	}

	@Override
	public Class<VerifyResponse> getResponseClassType() {
		return VerifyResponse.class;
	}


	@Override
	public String generateURL(String params) {
		return this.addWebKey(this.generateBaseURL(params)+PATH);
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}


}
