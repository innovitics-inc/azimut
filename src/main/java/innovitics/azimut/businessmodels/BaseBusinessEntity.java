package innovitics.azimut.businessmodels;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import innovitics.azimut.models.kyc.Review;

public class BaseBusinessEntity implements BusinessInterface,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3988427385021157139L;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	protected Date createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	protected Date updatedAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	protected Date deletedAt;
	
	protected String documentURL;
	protected String documentName;
	protected Double documentSize;
	protected String documentSubDirectory;
	protected String searchFromDate;
	protected String searchToDate;
	protected String searchDates;
	protected Long azIdType;
	protected String azId;
	protected String sorting;
	protected Long bankId; 
	protected Long accountId;
	protected String language;
	protected Integer [] imageTypes;
	protected Integer verificationPercentage;
	protected String country;
	protected String city;
	protected String firstName;
	protected String lastName;
	protected String dateOfBirth;
	private String dateOfIdExpiry;
	protected String  userPhone;
	protected Integer userStep;
	protected Long [] fieldNames;
	protected Long firstPageId;
	protected Long countryId; 
	private Boolean draw;
	private Boolean isMobile;
	private Boolean isWeb;
	private Long fundId;
	private Long teacomputerId;
	private Boolean isLocal;
	private Boolean isActive;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean persist;
	protected Long transactionId;
	protected String deviceId;
	protected String systemTrx;
	protected List<Review> reviews;

	
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
	public String getDocumentURL() {
		return documentURL;
	}
	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}
	public String getSearchFromDate() {
		return searchFromDate;
	}
	public void setSearchFromDate(String searchFromDate) {
		this.searchFromDate = searchFromDate;
	}
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	public Long getAzIdType() {
		return azIdType;
	}
	public void setAzIdType(Long azIdType) {
		this.azIdType = azIdType;
	}
	public String getAzId() {
		return azId;
	}
	public void setAzId(String azId) {
		this.azId = azId;
	}
	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getSearchDates() {
		return searchDates;
	}
	public void setSearchDates(String searchDates) {
		this.searchDates = searchDates;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public Double getDocumentSize() {
		return documentSize;
	}
	public void setDocumentSize(Double documentSize) {
		this.documentSize = documentSize;
	}
	public String getDocumentSubDirectory() {
		return documentSubDirectory;
	}
	public void setDocumentSubDirectory(String documentSubDirectory) {
		this.documentSubDirectory = documentSubDirectory;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Integer[] getImageTypes() {
		return imageTypes;
	}
	public void setImageTypes(Integer[] imageTypes) {
		this.imageTypes = imageTypes;
	}
	public Integer getVerificationPercentage() {
		return verificationPercentage;
	}
	public void setVerificationPercentage(Integer verificationPercentage) {
		this.verificationPercentage = verificationPercentage;
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
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public Integer getUserStep() {
		return userStep;
	}
	public void setUserStep(Integer userStep) {
		this.userStep = userStep;
	}
	public Long[] getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(Long[] fieldNames) {
		this.fieldNames = fieldNames;
	}
	public Long getFirstPageId() {
		return firstPageId;
	}
	public void setFirstPageId(Long firstPageId) {
		this.firstPageId = firstPageId;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public Boolean getDraw() {
		return draw;
	}
	public void setDraw(Boolean draw) {
		this.draw = draw;
	}
	public Boolean getIsMobile() {
		return isMobile;
	}
	public void setIsMobile(Boolean isMobile) {
		this.isMobile = isMobile;
	}
	public Boolean getIsWeb() {
		return isWeb;
	}
	public void setIsWeb(Boolean isWeb) {
		this.isWeb = isWeb;
	}
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public Long getTeacomputerId() {
		return teacomputerId;
	}
	public void setTeacomputerId(Long teacomputerId) {
		this.teacomputerId = teacomputerId;
	}
	public Boolean getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getPersist() {
		return persist;
	}
	public void setPersist(Boolean persist) {
		this.persist = persist;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSystemTrx() {
		return systemTrx;
	}
	public void setSystemTrx(String systemTrx) {
		this.systemTrx = systemTrx;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
}
