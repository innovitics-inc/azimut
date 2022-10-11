package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.HoldClientBankAccountApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.HoldClientBankAccountInput;
import innovitics.azimut.rest.entities.teacomputers.HoldClientBankAccountOutput;
import innovitics.azimut.rest.models.teacomputers.HoldClientBankAccountResponse;

@Component
public class HoldClientBankAccountMapper extends RestMapper<HoldClientBankAccountInput, HoldClientBankAccountOutput, HoldClientBankAccountResponse, BusinessAzimutClient>{

	@Autowired HoldClientBankAccountApiConsumer holdClientBankAccountApiConsumer;
	@Override
	BusinessAzimutClient consumeRestService(BusinessAzimutClient businessAzimutClient, String params) throws IntegrationException, HttpClientErrorException, Exception {
		
		return this.createBusinessEntityFromOutput(this.holdClientBankAccountApiConsumer.invoke(this.createInput(businessAzimutClient), HoldClientBankAccountResponse.class, params));

	}

	@Override
	List<BusinessAzimutClient> consumeListRestService(BusinessAzimutClient businessAzimutClient, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	HoldClientBankAccountInput createInput(BusinessAzimutClient businessAzimutClient) {
		HoldClientBankAccountInput input=new HoldClientBankAccountInput();
		input.setIdTypeId(businessAzimutClient.getAzIdType());
		input.setIdNumber(businessAzimutClient.getAzId());
		input.setAccountId(businessAzimutClient.getAccountId());
		return input;
	}

	@Override
	BusinessAzimutClient createBusinessEntityFromOutput(HoldClientBankAccountOutput BaseOutput) {
		return new BusinessAzimutClient();
	}

	@Override
	protected List<BusinessAzimutClient> createListBusinessEntityFromOutput(HoldClientBankAccountOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

}
