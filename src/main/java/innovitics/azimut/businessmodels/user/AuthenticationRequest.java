package innovitics.azimut.businessmodels.user;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class AuthenticationRequest extends BaseBusinessEntity {


	private String userPhone;
	private	String countryPhoneCode;
	private String phoneNumber;
	private String password;
	private String deviceId;
	private String refreshToken;
	private String newPassword;

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
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	public AuthenticationRequest(String userPhone, String countryPhoneCode, String phoneNumber, String password) {
		super();
		this.userPhone = userPhone;
		this.countryPhoneCode = countryPhoneCode;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	public AuthenticationRequest() {
	}
	@Override
	public String toString() {
		return "AuthenticationRequest [userPhone=" + userPhone + ", countryPhoneCode=" + countryPhoneCode
				+ ", phoneNumber=" + phoneNumber + ", password=" + password + ", deviceId=" + deviceId + ", refreshToken="
				+ refreshToken + ", newPassword=" + newPassword + "]";
	}

}
