package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.firebase.VerifyApiConsumer;
import innovitics.azimut.rest.entities.firebase.VerifyInput;
import innovitics.azimut.rest.entities.firebase.VerifyOutput;
import innovitics.azimut.rest.models.firebase.VerifyRequest;
import innovitics.azimut.rest.models.firebase.VerifyResponse;
@Component
public class VerifyMapper extends RestMapper<VerifyInput, VerifyOutput,VerifyRequest,VerifyResponse, BusinessUserOTP>{

	@Autowired VerifyApiConsumer verifyApiConsumer;
	
	@Override
	BusinessUserOTP consumeRestService(BusinessUserOTP businessUserOTP, String params) throws IntegrationException, HttpClientErrorException, Exception {
		
		return null;
	}

	@Override
	List<BusinessUserOTP> consumeListRestService(BusinessUserOTP businessUserOTP, String params) throws IntegrationException, HttpClientErrorException, Exception {
		
		return null;
	}

	@Override
	VerifyInput createInput(BusinessUserOTP businessUserOTP) {
		VerifyInput verifyInput=new   VerifyInput();
		verifyInput.setSessionInfo(businessUserOTP.getSessionInfo());
		verifyInput.setCode(businessUserOTP.getOtp());
		return verifyInput;
	}

	@Override
	BusinessUserOTP createBusinessEntityFromOutput(VerifyOutput verifyOutput) {
		
		return new BusinessUserOTP();
	}

	@Override
	protected List<BusinessUserOTP> createListBusinessEntityFromOutput(VerifyOutput verifyOutput) {
		
		return null;
	}

	@Override
	protected void setConsumer(BusinessUserOTP businessUserOTP) {
		this.consumer=verifyApiConsumer;
		
	}

}
