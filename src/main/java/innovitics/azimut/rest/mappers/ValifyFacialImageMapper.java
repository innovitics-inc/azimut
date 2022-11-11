package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.valify.ValifyFaceMatchApiConsumer;
import innovitics.azimut.rest.entities.valify.ValifyFacialImageInput;
import innovitics.azimut.rest.entities.valify.ValifyFacialImageOutput;
import innovitics.azimut.rest.models.valify.ValifyFacialImageRequest;
import innovitics.azimut.rest.models.valify.ValifyFacialImageResponse;
@Component
public class ValifyFacialImageMapper extends RestMapper<ValifyFacialImageInput,ValifyFacialImageOutput,ValifyFacialImageRequest,ValifyFacialImageResponse, BusinessValify>   {

	@Autowired ValifyFaceMatchApiConsumer valifyFaceMatchApiConsumer;
	@Override
	public BusinessValify consumeRestService(BusinessValify businessValify, String params) throws HttpClientErrorException, Exception {
		
		return this.createBusinessEntityFromOutput(this.valifyFaceMatchApiConsumer.invoke(this.createInput(businessValify), ValifyFacialImageResponse.class, params));
	}

	@Override
	List<BusinessValify> consumeListRestService(BusinessValify baseBusinessEntity, String params)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	ValifyFacialImageInput createInput(BusinessValify businessValify) {
		ValifyFacialImageInput input =new ValifyFacialImageInput();
		input.setFirstImage(businessValify.getFirstImage());
		input.setSecondImage(businessValify.getSecondImage());
		input.setToken(businessValify.getToken());
		input.setLang(businessValify.getLanguage());
		return input;
			
	}

	@Override
	BusinessValify createBusinessEntityFromOutput(ValifyFacialImageOutput valifyFacialImageOutput) {

		BusinessValify businessValify=new BusinessValify();
		businessValify.setImagesSimilar(valifyFacialImageOutput.isSimilar());
		businessValify.setConfidence(valifyFacialImageOutput.getConfidence());
		businessValify.setValifyTransactionId(valifyFacialImageOutput.getTransactionId());
		return businessValify;
	}

	@Override
	protected List<BusinessValify> createListBusinessEntityFromOutput(ValifyFacialImageOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setConsumer(BusinessValify baseBusinessEntity) {
		this.consumer=valifyFaceMatchApiConsumer;
		
	}

}
