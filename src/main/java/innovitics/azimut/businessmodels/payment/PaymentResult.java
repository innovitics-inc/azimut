package innovitics.azimut.businessmodels.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResult {
	   
    	 @JsonProperty("response_status")
    	 private String responseStatus;
	   	 @JsonProperty("response_code")
    	 private String responseCode;
	   	 @JsonProperty("response_message")
    	 private String responseMessage;
	   	 @JsonProperty("cvv_result")
	   	 private String cvvResult;
	   	 @JsonProperty("avs_result")
	   	 private String avsResult;
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
		public String getCvvResult() {
			return cvvResult;
		}
		public void setCvvResult(String cvvResult) {
			this.cvvResult = cvvResult;
		}
		public String getAvsResult() {
			return avsResult;
		}
		public void setAvsResult(String avsResult) {
			this.avsResult = avsResult;
		}
	   	 
	   	 
    	   
}
