package innovitics.azimut.rest.models.valify;

import innovitics.azimut.rest.models.BaseRestResponse;

public class ValifyResponse extends BaseRestResponse {

	  protected String   transaction_id; 
	  private int      trials_remaining;
	  private String error_code;
	  protected String status;
	  protected String message;
	  public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
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
	@Override
	public String toString() {
		return "ValifyResponse [transaction_id=" + transaction_id + ", trials_remaining=" + trials_remaining
				+ ", error_code=" + error_code + ", status=" + status + ", message=" + message + "]";
	}

	
  	 

}
