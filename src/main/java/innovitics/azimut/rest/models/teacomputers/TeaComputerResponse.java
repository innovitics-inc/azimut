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
	
	@JsonProperty("errorMessage")
	protected String errorMessage;
	
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
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "TeaComputerResponse [Signature=" + Signature + ", Message=" + Message + ", ErrorCode=" + ErrorCode
				+ "]";
	}

	
	


	
	
	
	
}
