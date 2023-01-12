package innovitics.azimut.businessmodels.user;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class AuthenticationRequest extends BaseBusinessEntity {


	private String userPhone;
	private	String countryPhoneCode;
	private String phoneNumber;
	private String password;
	private String refreshToken;
	private String newPassword;
	private String email;

	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCountryPhoneCode() {
		return countryPhoneCode;
	}
	public void setCountryPhoneCode(String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public AuthenticationRequest(String userPhone, String countryPhoneCode, String phoneNumber, String password) {
		super();
		this.userPhone = userPhone;
		this.countryPhoneCode = countryPhoneCode;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
	public AuthenticationRequest(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}
	public AuthenticationRequest() {
	}
	@Override
	public String toString() {
		return "AuthenticationRequest [userPhone=" + userPhone + ", countryPhoneCode=" + countryPhoneCode
				+ ", phoneNumber=" + phoneNumber + ", password=" + password +", refreshToken="
				+ refreshToken + ", newPassword=" + newPassword + "]";
	}

}
