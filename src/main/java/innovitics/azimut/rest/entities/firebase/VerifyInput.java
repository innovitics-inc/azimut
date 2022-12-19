package innovitics.azimut.rest.entities.firebase;

public class VerifyInput extends FirebaseInput {

	private String sessionInfo;
	private String code;
	public String getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(String sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
