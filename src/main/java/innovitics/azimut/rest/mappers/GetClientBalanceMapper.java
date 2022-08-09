package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessClientCashBalance;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetClientCashBalanceApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceOutput;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceResponse;
@Service
public class GetClientBalanceMapper extends RestMapper<GetClientBalanceInput, GetClientBalanceOutput, GetClientCashBalanceResponse, BusinessClientCashBalance>{

	@Autowired GetClientCashBalanceApiConsumer getClientBalanceApiConsumer ;
	
	@Override
	BusinessClientCashBalance consumeRestService(BusinessClientCashBalance businessClientBalance, String params)
			throws HttpClientErrorException, Exception {
		return  this.createBusinessEntityFromOutput(this.getClientBalanceApiConsumer.invoke(this.createInput(businessClientBalance),GetClientCashBalanceResponse.class, params));
	}

	@Override
	List<BusinessClientCashBalance> consumeListRestService(BusinessClientCashBalance businessClientBalance, String params)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	GetClientBalanceInput createInput(BusinessClientCashBalance businessClientBalance) {
		GetClientBalanceInput input=new GetClientBalanceInput();
		input.setIdTypeId(businessClientBalance.getAzIdType());
		input.setIdNumber(businessClientBalance.getAzId());
		return input;
	}

	@Override
	BusinessClientCashBalance createBusinessEntityFromOutput(GetClientBalanceOutput getClientBalanceOutput) {
		BusinessClientCashBalance businessClientBalance=new BusinessClientCashBalance();
		businessClientBalance.setBalance(getClientBalanceOutput.getBalance());
		businessClientBalance.setCurrencyID(getClientBalanceOutput.getCurrencyID());
		businessClientBalance.setCurrencyName(getClientBalanceOutput.getCurrencyName());
		businessClientBalance.setPendingTransfer(getClientBalanceOutput.getPendingTransfer());
		return businessClientBalance;
	}

	@Override
	protected List<BusinessClientCashBalance> createListBusinessEntityFromOutput(GetClientBalanceOutput getClientBalanceOutput) {
		// TODO Auto-generated method stub
		return null;
	}

}
