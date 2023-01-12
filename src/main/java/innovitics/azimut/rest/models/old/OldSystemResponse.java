package innovitics.azimut.rest.models.old;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.rest.models.BaseRestResponse;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OldSystemResponse extends BaseRestResponse{

	@JsonProperty("message")
	private String message;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
