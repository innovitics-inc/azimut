package innovitics.azimut.models.user;

import java.util.Date;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="app_users")

public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String  userPhone;
	private String password;
	private String deviceId;
	private String userId;
	@ManyToOne
	@JoinColumn(name="user_id_type",foreignKey = @javax.persistence.ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
	private UserType userType;
	private String nickName;
	private String emailAddress;
	private String countryPhoneCode;
	private String phoneNumber;
	private String profilePicture;
	private String signedPdf;
	private String picturePath;
	private String pdfPath;
	private Boolean isChangeNoApproved;
	private Integer verificationPercentage;
	private Boolean isVerified;
	private Boolean migrated;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date updatedAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date deletedAt;
	private String countryCode;
	private Long lastSolvedPageId;
	private Long nextPageId;
	private Integer userStep;
	private Integer contractMap;
	
	private String country;
	private String city;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String dateOfIdExpiry;
	
	private String otherUserIdType;
	private Long otherIdType;
	private String otherUserId;
	private String otherNationality;
	private Long genderId;

	private String teacomputersAddressAr;
	private String teacomputersAddressEn;
	
	private Long teacomputersCityId;
	private Long teacomputersCountryId;
	
	private Long teacomputersIssueCityId;
	private Long teacomputersIssueCountryId;
	
	private Long teacomputersNationalityId;
	
	private Long teacomputersClientaml;
	
	private String  teacomputersOccupation;
	
	private Integer failureNumber;
	
	private Boolean isInstitutional;
	private Boolean livenessChecked;
	
	private String solvedPages;
	
	private Boolean isOld;
	
	
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getSignedPdf() {
		return signedPdf;
	}
	public void setSignedPdf(String signedPdf) {
		this.signedPdf = signedPdf;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	
	public Boolean isChangeNoApproved() {
		return isChangeNoApproved;
	}
	public void setChangeNoApproved(Boolean isChangeNoApproved) {
		this.isChangeNoApproved = isChangeNoApproved;
	}
	public Integer getVerificationPercentage() {
		return verificationPercentage;
	}
	public void setVerificationPercentage(Integer verificationPercentage) {
		this.verificationPercentage = verificationPercentage;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getMigrated() {
		return migrated;
	}
	public void setMigrated(Boolean migrated) {
		this.migrated = migrated;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public Long getLastSolvedPageId() {
		return lastSolvedPageId;
	}
	public void setLastSolvedPageId(Long lastSolvedPageId) {
		this.lastSolvedPageId = lastSolvedPageId;
	}
	public Long getNextPageId() {
		return nextPageId;
	}
	public void setNextPageId(Long nextPageId) {
		this.nextPageId = nextPageId;
	}
	public Integer getUserStep() {
		return userStep;
	}
	public void setUserStep(Integer userStep) {
		this.userStep = userStep;
	}
	public Integer getContractMap() {
		return contractMap;
	}
	public void setContractMap(Integer contractMap) {
		this.contractMap = contractMap;
	}
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getDateOfIdExpiry() {
		return dateOfIdExpiry;
	}
	public void setDateOfIdExpiry(String dateOfIdExpiry) {
		this.dateOfIdExpiry = dateOfIdExpiry;
	}
	public String getOtherUserIdType() {
		return otherUserIdType;
	}
	public void setOtherUserIdType(String otherUserIdType) {
		this.otherUserIdType = otherUserIdType;
	}
	public Long getOtherIdType() {
		return otherIdType;
	}
	public void setOtherIdType(Long otherIdType) {
		this.otherIdType = otherIdType;
	}
	public String getOtherUserId() {
		return otherUserId;
	}
	public void setOtherUserId(String otherUserId) {
		this.otherUserId = otherUserId;
	}
	
	public String getOtherNationality() {
		return otherNationality;
	}
	public void setOtherNationality(String otherNationality) {
		this.otherNationality = otherNationality;
	}	
	public Long getGenderId() {
		return genderId;
	}
	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public String getTeacomputersAddressAr() {
		return teacomputersAddressAr;
	}
	public void setTeacomputersAddressAr(String teacomputersAddressAr) {
		this.teacomputersAddressAr = teacomputersAddressAr;
	}
	public String getTeacomputersAddressEn() {
		return teacomputersAddressEn;
	}
	public void setTeacomputersAddressEn(String teacomputersAddressEn) {
		this.teacomputersAddressEn = teacomputersAddressEn;
	}
	public Long getTeacomputersCityId() {
		return teacomputersCityId;
	}
	public void setTeacomputersCityId(Long teacomputersCityId) {
		this.teacomputersCityId = teacomputersCityId;
	}
	public Long getTeacomputersCountryId() {
		return teacomputersCountryId;
	}
	public void setTeacomputersCountryId(Long teacomputersCountryId) {
		this.teacomputersCountryId = teacomputersCountryId;
	}
	public Long getTeacomputersIssueCityId() {
		return teacomputersIssueCityId;
	}
	public void setTeacomputersIssueCityId(Long teacomputersIssueCityId) {
		this.teacomputersIssueCityId = teacomputersIssueCityId;
	}
	public Long getTeacomputersIssueCountryId() {
		return teacomputersIssueCountryId;
	}
	public void setTeacomputersIssueCountryId(Long teacomputersIssueCountryId) {
		this.teacomputersIssueCountryId = teacomputersIssueCountryId;
	}
	public Long getTeacomputersNationalityId() {
		return teacomputersNationalityId;
	}
	public void setTeacomputersNationalityId(Long teacomputersNationalityId) {
		this.teacomputersNationalityId = teacomputersNationalityId;
	}
	public Long getTeacomputersClientaml() {
		return teacomputersClientaml;
	}
	public void setTeacomputersClientaml(Long teacomputersClientaml) {
		this.teacomputersClientaml = teacomputersClientaml;
	}
	public String getTeacomputersOccupation() {
		return teacomputersOccupation;
	}
	public void setTeacomputersOccupation(String teacomputersOccupation) {
		this.teacomputersOccupation = teacomputersOccupation;
	}
	public Integer getFailureNumber() {
		return failureNumber;
	}
	public void setFailureNumber(Integer failureNumber) {
		this.failureNumber = failureNumber;
	}
	public Boolean getIsInstitutional() {
		return isInstitutional;
	}
	public void setIsInstitutional(Boolean isInstitutional) {
		this.isInstitutional = isInstitutional;
	}
	
	public Boolean getLivenessChecked() {
		return livenessChecked;
	}
	public void setLivenessChecked(Boolean livenessChecked) {
		this.livenessChecked = livenessChecked;
	}
	public String getSolvedPages() {
		return solvedPages;
	}
	public void setSolvedPages(String solvedPages) {
		this.solvedPages = solvedPages;
	}
	
	
	public Boolean getIsOld() {
		return isOld;
	}
	public void setIsOld(Boolean isOld) {
		this.isOld = isOld;
	}
	public void concatinate()
	{
		this.setUserPhone(this.getCountryPhoneCode()+this.getPhoneNumber());
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userPhone=" + userPhone + ", password=" + password + ", deviceId=" + deviceId
				+ ", userId=" + userId + ", userType=" + userType + ", nickName=" + nickName + ", emailAddress="
				+ emailAddress + ", countryPhoneCode=" + countryPhoneCode + ", phoneNumber=" + phoneNumber
				+ ", profilePicture=" + profilePicture + ", signedPdf=" + signedPdf + ", picturePath=" + picturePath
				+ ", pdfPath=" + pdfPath + ", isChangeNoApproved=" + isChangeNoApproved + ", verificationPercentage="
				+ verificationPercentage + ", isVerified=" + isVerified + ", migrated=" + migrated + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", countryCode=" + countryCode
				+ ", lastSolvedPageId=" + lastSolvedPageId + ", nextPageId=" + nextPageId + ", userStep=" + userStep
				+ ", contractMap=" + contractMap + ", country=" + country + ", city=" + city + ", firstName="
				+ firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", dateOfIdExpiry="
				+ dateOfIdExpiry + ", otherUserIdType=" + otherUserIdType + ", otherIdType=" + otherIdType
				+ ", otherUserId=" + otherUserId + ", otherNationality=" + otherNationality + ", genderId=" + genderId
				+ ", teacomputersAddressAr=" + teacomputersAddressAr + ", teacomputersAddressEn="
				+ teacomputersAddressEn + ", teacomputersCityId=" + teacomputersCityId + ", teacomputersCountryId="
				+ teacomputersCountryId + ", teacomputersIssueCityId=" + teacomputersIssueCityId
				+ ", teacomputersIssueCountryId=" + teacomputersIssueCountryId + ", teacomputersNationalityId="
				+ teacomputersNationalityId + ", teacomputersClientaml=" + teacomputersClientaml
				+ ", teacomputersOccupation=" + teacomputersOccupation + ", failureNumber=" + failureNumber
				+ ", isInstitutional=" + isInstitutional + "]";
	}



	
}
