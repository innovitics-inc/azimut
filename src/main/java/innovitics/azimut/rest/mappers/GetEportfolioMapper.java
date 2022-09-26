package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import innovitics.azimut.businessmodels.user.EportfolioDetail;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetEportfolioApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.EportfolioOutput;
import innovitics.azimut.rest.entities.teacomputers.GetEportfolioInput;
import innovitics.azimut.rest.entities.teacomputers.GetEportfolioOutput;
import innovitics.azimut.rest.models.teacomputers.GetEportfolioResponse;
import innovitics.azimut.utilities.datautilities.ListUtility;


@Component
public class GetEportfolioMapper extends RestMapper<GetEportfolioInput, GetEportfolioOutput, GetEportfolioResponse, EportfolioDetail>{

	@Autowired GetEportfolioApiConsumer getEportfolioApiConsumer;
	@Autowired ListUtility<EportfolioOutput> eportfolioOutputListUtility;
	
	
	@Override
	EportfolioDetail consumeRestService(EportfolioDetail baseBusinessEntity, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return null;
	}

	@Override
	List<EportfolioDetail> consumeListRestService(EportfolioDetail eportfolioDetail, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return this.createListBusinessEntityFromOutput(this.getEportfolioApiConsumer.invoke(this.createInput(eportfolioDetail),GetEportfolioResponse.class, params));
	}

	@Override
	GetEportfolioInput createInput(EportfolioDetail eportfolioDetail) {
		GetEportfolioInput input=new GetEportfolioInput();
		input.setIdNumber(eportfolioDetail.getAzId());		
		input.setIdTypeId(eportfolioDetail.getAzIdType());
		input.setLocale(eportfolioDetail.getLanguage());
		return  input;
	}

	@Override
	EportfolioDetail createBusinessEntityFromOutput(GetEportfolioOutput BaseOutput) {
		return null;
	}

	@Override
	protected List<EportfolioDetail> createListBusinessEntityFromOutput(GetEportfolioOutput getEportfolioOutput) {
		List<EportfolioDetail> eportfolioDetails= new ArrayList<EportfolioDetail>();
		if(this.eportfolioOutputListUtility.isListPopulated(getEportfolioOutput.getEportfolioOutputs()))
		{
			for(EportfolioOutput eportfolioOutput:getEportfolioOutput.getEportfolioOutputs())
			{
				EportfolioDetail eportfolioDetail=new EportfolioDetail();
				eportfolioDetail.setId(eportfolioOutput.getId());
				eportfolioDetail.setClientId(eportfolioOutput.getClientId());
				eportfolioDetail.setPortfolioId(eportfolioOutput.getPortfolioId());
				eportfolioDetail.setName(eportfolioOutput.getName());
				eportfolioDetail.setValue(eportfolioOutput.getValue());
				eportfolioDetail.setWeight(eportfolioOutput.getWeight());
				eportfolioDetails.add(eportfolioDetail);
			}
		}
		
		return eportfolioDetails;
	}

}
