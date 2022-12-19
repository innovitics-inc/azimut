package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.firebase.SendVerificationCodeApiConsumer;
import innovitics.azimut.rest.entities.firebase.SendVerificationCodeInput;
import innovitics.azimut.rest.entities.firebase.SendVerificationCodeOutput;
import innovitics.azimut.rest.models.firebase.SendVerificationCodeRequest;
import innovitics.azimut.rest.models.firebase.SendVerificationCodeResponse;
@Component
public class SendVerificationCodeMapper extends RestMapper<SendVerificationCodeInput, SendVerificationCodeOutput,SendVerificationCodeRequest,SendVerificationCodeResponse, BusinessUserOTP>{

	
	@Autowired SendVerificationCodeApiConsumer sendVerificationCodeApiConsumer;
	@Override
	BusinessUserOTP consumeRestService(BusinessUserOTP businessUserOTP, String params)throws IntegrationException, HttpClientErrorException, Exception {
		return null;
	}

	@Override
	List<BusinessUserOTP> consumeListRestService(BusinessUserOTP businessUserOTP, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return null;
	}

	@Override
	SendVerificationCodeInput createInput(BusinessUserOTP businessUserOTP) {
		SendVerificationCodeInput sendVerificationCodeInput=new SendVerificationCodeInput();
		sendVerificationCodeInput.setPhoneNumber(businessUserOTP.getUserPhone());
		return sendVerificationCodeInput;
	}

	@Override
	BusinessUserOTP createBusinessEntityFromOutput(SendVerificationCodeOutput sendVerificationCodeOutput) {
		BusinessUserOTP businessUserOTP=new BusinessUserOTP();
		businessUserOTP.setSessionInfo(sendVerificationCodeOutput.getSessionInfo());
		businessUserOTP.setUserPhone(sendVerificationCodeOutput.getUserPhone());
		return businessUserOTP;
	}

	@Override
	protected List<BusinessUserOTP> createListBusinessEntityFromOutput(SendVerificationCodeOutput BaseOutput) {
	
		return null;
	}

	@Override
	protected void setConsumer(BusinessUserOTP baseBusinessEntity) {
		// 
		this.consumer=sendVerificationCodeApiConsumer;
	}

}
