package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.funds.BusinessFund;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetFundByIdInput;
import innovitics.azimut.rest.entities.teacomputers.GetFundByIdOutput;
import innovitics.azimut.rest.entities.teacomputers.GetFundOutput;
import innovitics.azimut.rest.models.BaseRestResponse;
import innovitics.azimut.rest.models.teacomputers.GetFundResponse;
import innovitics.azimut.rest.models.teacomputers.GetSingleFundResponse;
import innovitics.azimut.utilities.datautilities.ListUtility;

public class GetFundMapper extends RestMapper<GetFundByIdInput,GetFundOutput, GetFundResponse, BusinessFund> {
	
	@Override
	BusinessFund consumeRestService(BusinessFund businessFund, String params) throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessFund> consumeListRestService(BusinessFund businessFund, String params)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	GetFundByIdInput createInput(BusinessFund businessFund) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	BusinessFund createBusinessEntityFromOutput(GetFundOutput getFundOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<BusinessFund> createListBusinessEntityFromOutput(GetFundOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	BusinessFund getConversion(GetFundByIdOutput output)
	{
		
		return null; 
		
	}
	

}
