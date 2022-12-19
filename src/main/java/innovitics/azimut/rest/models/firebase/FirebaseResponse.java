package innovitics.azimut.rest.models.firebase;

import innovitics.azimut.rest.models.BaseRestResponse;

public class FirebaseResponse extends BaseRestResponse{

	private Integer code;
	private String message;
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
	
	
}
