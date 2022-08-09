package innovitics.azimut.rest.entities.valify;

import innovitics.azimut.rest.entities.BaseOutput;

public class ValifyOutput extends BaseOutput {
	private String token;
	private String transactionId;
	private int trialsRemaining;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getTrialsRemaining() {
		return trialsRemaining;
	}

	public void setTrialsRemaining(int trialsRemaining) {
		this.trialsRemaining = trialsRemaining;
	}
	
	
}
