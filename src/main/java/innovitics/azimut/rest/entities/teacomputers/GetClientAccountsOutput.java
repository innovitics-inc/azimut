package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetClientAccountsOutput extends TeaComputerOutput {

	private List<ClientAccountOutput> clientAccountOutputs;

	public List<ClientAccountOutput> getClientAccountOutputs() {
		return clientAccountOutputs;
	}

	public void setClientAccountOutputs(List<ClientAccountOutput> clientAccountOutputs) {
		this.clientAccountOutputs = clientAccountOutputs;
	}
	
	
	
	
}
