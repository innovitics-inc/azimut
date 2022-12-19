package innovitics.azimut.rest.entities.firebase;

public class SendVerificationCodeInput extends FirebaseInput{

	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
