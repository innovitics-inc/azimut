package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.valify.ValifyAccessTokenApiConsumer;
import innovitics.azimut.rest.entities.valify.ValifyAccessTokenInput;
import innovitics.azimut.rest.entities.valify.ValifyAccessTokenOutput;
import innovitics.azimut.rest.models.valify.ValifyAccessTokenRequest;
import innovitics.azimut.rest.models.valify.ValifyAccessTokenResponse;
@Component
public class ValifyAccessTokenMapper extends RestMapper<ValifyAccessTokenInput,ValifyAccessTokenOutput,ValifyAccessTokenRequest ,ValifyAccessTokenResponse, BusinessValify> {

	@Autowired ValifyAccessTokenApiConsumer valifyAccessTokenApiConsumer;
	
	@Override
	public BusinessValify consumeRestService(BusinessValify businessValify, String params) throws HttpClientErrorException, Exception {
		
		return this.createBusinessEntityFromOutput(this.valifyAccessTokenApiConsumer.invoke(this.createInput(businessValify), ValifyAccessTokenResponse.class, params));
	}

	@Override
	List<BusinessValify> consumeListRestService(BusinessValify baseBusinessEntity, String params)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	ValifyAccessTokenInput createInput(BusinessValify baseBusinessEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	BusinessValify createBusinessEntityFromOutput(ValifyAccessTokenOutput valifyAccessTokenOutput) {
		BusinessValify businessValify=new BusinessValify();
		this.logger.info("ValifyAccessTokenOutput in the Mapper:::"+valifyAccessTokenOutput.toString());
		if(valifyAccessTokenOutput!=null)
		{
			businessValify.setToken(valifyAccessTokenOutput.getAccessToken());
		}
		this.logger.info("Business Valify token retrieval:::"+businessValify.toString());
		return businessValify;
	}

	@Override
	protected List<BusinessValify> createListBusinessEntityFromOutput(ValifyAccessTokenOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setConsumer(BusinessValify baseBusinessEntity) {
		this.consumer=valifyAccessTokenApiConsumer;
		
	}

}
