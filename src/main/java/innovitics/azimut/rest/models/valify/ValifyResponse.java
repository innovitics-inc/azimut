package innovitics.azimut.rest.models.valify;

import innovitics.azimut.rest.models.BaseRestResponse;

public class ValifyResponse extends BaseRestResponse {

	  private String   transaction_id; 
	  private int      trials_remaining;
	  private String error_code;
	
	  public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public int getTrials_remaining() {
		return trials_remaining;
	}
	public void setTrials_remaining(int trials_remaining) {
		this.trials_remaining = trials_remaining;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
  	 

}
