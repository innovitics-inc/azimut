package innovitics.azimut.businessmodels.user;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.user.UserImage;
import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "users", singular = "user")
public class BusinessUser  extends BaseBusinessEntity{
	

	protected Long id;
	protected String password;
	protected String secondPassword;
	protected String oldPassword;
	protected String newPassword;
	protected String deviceId;
	protected String userId;
	protected String userIdType;
	protected Long 	 idType; 
	protected String nickName;
	protected String emailAddress;
	protected String countryPhoneCode;
	protected String phoneNumber;
	protected String profilePicture;
	protected String signedPdf;
	protected String picturePath;
	protected String pdfPath;
	protected Boolean isChangeNoApproved;
	protected Boolean isVerified;
	protected Boolean migrated;
	protected BusinessFlow businessFlow;
	protected int flowId;
	protected String countryCode;
	protected MultipartFile file;
	private Long lastSolvedPageId;
	private Long nextPageId;
	private Integer nextUserStep;
	private Integer contractMap;
	private List<UserImage> userImages;
	private String otherUserIdType;
	private Long otherIdType;
	private String otherUserId;
	private String otherNationality;
	private Long genderId;
	private AzimutAccount azimutAccount;
	private Integer failureNumber;
	private Boolean isInstitutional;
	private Long azimutIdTypeId;
	private UserLocation userLocation;
	
	private BusinessClientBankAccountDetails[] clientBankAccounts;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecondPassword() {
		return secondPassword;
	}
	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
	public String getUserIdType() {
		return userIdType;
	}
	public void setUserIdType(String userIdType) {
		this.userIdType = userIdType;
	}
	public Long getIdType() {
		return idType;
	}
	public void setIdType(Long idType) {
		this.idType = idType;
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
	public BusinessFlow getBusinessFlow() {
		return businessFlow;
	}
	public void setBusinessFlow(BusinessFlow businessFlow) {
		this.businessFlow = businessFlow;
		this.flowId=businessFlow.getFlowId();
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
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
	public Integer getNextUserStep() {
		return nextUserStep;
	}
	public void setNextUserStep(Integer nextUserStep) {
		this.nextUserStep = nextUserStep;
	}
	public Integer getContractMap() {
		return contractMap;
	}
	public void setContractMap(Integer contractMap) {
		this.contractMap = contractMap;
	}
	public List<UserImage> getUserImages() {
		return userImages;
	}
	public void setUserImages(List<UserImage> userImages) {
		this.userImages = userImages;
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
	
	public AzimutAccount getAzimutAccount() {
		return azimutAccount;
	}
	public void setAzimutAccount(AzimutAccount azimutAccount) {
		this.azimutAccount = azimutAccount;
	}
	
	public Integer getFailureNumber() {
		return failureNumber;
	}
	public void setFailureNumber(Integer failureNumber) {
		this.failureNumber = failureNumber;
	}

	public BusinessClientBankAccountDetails[] getClientBankAccounts() {
		return clientBankAccounts;
	}
	public void setClientBankAccounts(BusinessClientBankAccountDetails[] clientBankAccounts) {
		this.clientBankAccounts = clientBankAccounts;
	}
	
	public Boolean getIsInstitutional() {
		return isInstitutional;
	}
	public void setIsInstitutional(Boolean isInstitutional) {
		this.isInstitutional = isInstitutional;
	}
	
	public Long getAzimutIdTypeId() {
		return azimutIdTypeId;
	}
	public void setAzimutIdTypeId(Long azimutIdTypeId) {
		this.azimutIdTypeId = azimutIdTypeId;
	}
	public UserLocation getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(UserLocation userLocation) {
		this.userLocation = userLocation;
	}
	public void concatinate()
	{
		this.setUserPhone(this.getCountryPhoneCode()+this.getPhoneNumber());
	}
	@Override
	public String toString() {
		return "BusinessUser [id=" + id + ", password=" + password + ", secondPassword=" + secondPassword
				+ ", oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", deviceId=" + deviceId
				+ ", userId=" + userId + ", userIdType=" + userIdType + ", idType=" + idType + ", nickName=" + nickName
				+ ", emailAddress=" + emailAddress + ", countryPhoneCode=" + countryPhoneCode + ", phoneNumber="
				+ phoneNumber + ", profilePicture=" + profilePicture + ", signedPdf=" + signedPdf + ", picturePath="
				+ picturePath + ", pdfPath=" + pdfPath + ", isChangeNoApproved=" + isChangeNoApproved + ", isVerified="
				+ isVerified + ", migrated=" + migrated + ", businessFlow=" + businessFlow + ", flowId=" + flowId
				+ ", countryCode=" + countryCode + ", file=" + file + ", lastSolvedPageId=" + lastSolvedPageId
				+ ", nextPageId=" + nextPageId + ", nextUserStep=" + nextUserStep + ", contractMap=" + contractMap
				+ ", userImages=" + userImages + ", otherUserIdType=" + otherUserIdType + ", otherIdType=" + otherIdType
				+ ", otherUserId=" + otherUserId + ", otherNationality=" + otherNationality + "]";
	}


}
