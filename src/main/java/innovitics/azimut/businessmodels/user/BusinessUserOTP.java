package innovitics.azimut.businessmodels.user;

import java.util.Date;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.OTPMethod;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "otps", singular = "otp")
public class BusinessUserOTP extends BaseBusinessEntity{

	private Long id;
	protected String countryPhoneCode;
	protected String phoneNumber;
	private String userPhone;
	private String otp;
	private Integer numberOfTimes;
	private Date nextTrial;
	private String functionality;
	private OTPMethod otpMethod;
	private String sessionInfo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Integer getNumberOfTimes() {
		return numberOfTimes;
	}
	public void setNumberOfTimes(Integer numberOfTimes) {
		this.numberOfTimes = numberOfTimes;
	}
	public Date getNextTrial() {
		return nextTrial;
	}
	public void setNextTrial(Date nextTrial) {
		this.nextTrial = nextTrial;
	}
	public String getFunctionality() {
		return functionality;
	}
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	public OTPMethod getOtpMethod() {
		return otpMethod;
	}
	public void setOtpMethod(OTPMethod otpMethod) {
		this.otpMethod = otpMethod;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public String getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(String sessionInfo) {
		this.sessionInfo = sessionInfo;
	}
	public BusinessUserOTP(String userPhone) {
		super();
		this.userPhone = userPhone;
	}
	
	
	
	public BusinessUserOTP() {
		// TODO Auto-generated constructor stub
	}
	
		
	
}
