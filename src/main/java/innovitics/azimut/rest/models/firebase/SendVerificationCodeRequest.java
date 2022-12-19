package innovitics.azimut.rest.models.firebase;

public class SendVerificationCodeRequest extends FirebaseRequest{

	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	
}
