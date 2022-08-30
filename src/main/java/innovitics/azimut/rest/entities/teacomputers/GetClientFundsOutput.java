package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetClientFundsOutput extends TeaComputerOutput{

	

	List<ClientFundOutput> clientFundOutputs;

	public List<ClientFundOutput> getClientFundOutputs() {
		return clientFundOutputs;
	}

	public void setClientFundOutputs(List<ClientFundOutput> clientFundOutputs) {
		this.clientFundOutputs = clientFundOutputs;
	}

	@Override
	public String toString() {
		return "GetClientFundsOutput [clientFundOutputs=" + clientFundOutputs + "]";
	}


	
	
}
