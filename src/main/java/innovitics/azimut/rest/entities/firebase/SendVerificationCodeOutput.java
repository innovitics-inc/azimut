package innovitics.azimut.rest.entities.firebase;

public class SendVerificationCodeOutput extends FirebaseOutput {

	private String sessionInfo;
	private String userPhone;

	public String getSessionInfo() {
		return sessionInfo;
	}

	public void setSessionInfo(String sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	
}
