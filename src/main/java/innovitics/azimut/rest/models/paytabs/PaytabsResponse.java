package innovitics.azimut.rest.models.paytabs;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.rest.models.BaseRestResponse;

public class PaytabsResponse extends BaseRestResponse{

	@JsonProperty("code")
	private Integer code;
	@JsonProperty("message")
    private String message;
	@JsonProperty("trace")
    private String trace;
	@JsonProperty("tran_ref")
    private String transactionReference;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTrace() {
		return trace;
	}
	public void setTrace(String trace) {
		this.trace = trace;
	}
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
    
    
    
}
