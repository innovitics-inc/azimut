package innovitics.azimut.rest.models.paytabs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResult {
	@JsonProperty("response_status")
	 private String responseStatus;
  	 @JsonProperty("response_code")
	 private String responseCode;
  	 @JsonProperty("response_message")
	 private String responseMessage;
  	 @JsonProperty("transaction_time")
	 private String responseTime;
	 
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
  	 
  	
}
