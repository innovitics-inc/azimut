package innovitics.azimut.rest.apis.firebase;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.firebase.SendVerificationCodeInput;
import innovitics.azimut.rest.entities.firebase.SendVerificationCodeOutput;
import innovitics.azimut.rest.models.firebase.SendVerificationCodeRequest;
import innovitics.azimut.rest.models.firebase.SendVerificationCodeResponse;
@Service
public class SendVerificationCodeApiConsumer extends RestFirebaseApiConsumer<SendVerificationCodeRequest,SendVerificationCodeResponse, SendVerificationCodeInput, SendVerificationCodeOutput> {

	private static final String PATH=":sendVerificationCode?key=";
	@Override
	public HttpEntity<String> generateRequestFromInput(SendVerificationCodeInput input) {
		SendVerificationCodeRequest request=new SendVerificationCodeRequest();
		request.setPhoneNumber(input.getPhoneNumber());
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(null, this.getContentLength(request)));
		return httpEntity;
	}


	@Override
	public SendVerificationCodeOutput generateOutPutFromResponse(ResponseEntity<SendVerificationCodeResponse> responseEntity) 
	{
		SendVerificationCodeOutput sendVerificationCodeOutput=new SendVerificationCodeOutput();
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{
			sendVerificationCodeOutput.setSessionInfo(responseEntity.getBody().getSessionInfo());
		}
		return sendVerificationCodeOutput;
	}
	@Override
	public String generateURL(String params) {
		return this.addWebKey(this.generateBaseURL(params)+PATH);
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public Class<SendVerificationCodeResponse> getResponseClassType() {
		return SendVerificationCodeResponse.class;
	}
	@Override
	public void transferFromInputToOutput(SendVerificationCodeInput input, SendVerificationCodeOutput output) {
		output.setUserPhone(input.getPhoneNumber());
		
	}
}
