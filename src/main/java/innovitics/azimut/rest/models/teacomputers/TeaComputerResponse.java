package innovitics.azimut.rest.models.teacomputers;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.rest.models.BaseRestResponse;

public class TeaComputerResponse extends BaseRestResponse{
	@JsonProperty("Signature")
	protected String Signature;
	@JsonProperty("Message")
	protected String Message;
	@JsonProperty("ErrorCode")
	protected String ErrorCode;

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String Signature) {
		this.Signature = Signature;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String ErrorCode) {
		this.ErrorCode = ErrorCode;
	}

	
	


	
	
	
	
}
