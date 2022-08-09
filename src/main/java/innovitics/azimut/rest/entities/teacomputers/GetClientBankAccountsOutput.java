package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetClientBankAccountsOutput extends TeaComputerOutput {

	List<ClientBankAccountOutput> clientBankAccountOutputs;
	
	ClientBankAccountOutput clientBankAccountOutput;

	public List<ClientBankAccountOutput> getClientBankAccountOutputs() {
		return clientBankAccountOutputs;
	}

	public void setClientBankAccountOutputs(List<ClientBankAccountOutput> clientBankAccountOutputs) {
		this.clientBankAccountOutputs = clientBankAccountOutputs;
	}

	public ClientBankAccountOutput getClientBankAccountOutput() {
		return clientBankAccountOutput;
	}

	public void setClientBankAccountOutput(ClientBankAccountOutput clientBankAccountOutput) {
		this.clientBankAccountOutput = clientBankAccountOutput;
	}
 	
}
