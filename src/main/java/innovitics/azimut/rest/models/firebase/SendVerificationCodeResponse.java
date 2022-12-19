package innovitics.azimut.rest.models.firebase;

public class SendVerificationCodeResponse extends FirebaseResponse{

	private String sessionInfo;

	public String getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(String sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
	
	
}
