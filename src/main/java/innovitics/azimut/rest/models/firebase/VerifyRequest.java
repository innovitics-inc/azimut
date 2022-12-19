package innovitics.azimut.rest.models.firebase;

public class VerifyRequest extends FirebaseRequest{

	private String code;
	
	private String sessionInfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(String sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
	
	
}
