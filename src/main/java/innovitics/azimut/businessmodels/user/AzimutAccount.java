package innovitics.azimut.businessmodels.user;

public class AzimutAccount extends BusinessUser{
	private String fullName;
	private String idDate;
	private String idMaturityDate;
	private Long clientAML;
	private String iDIssueCityId;
	private String iDIssueCountryId;
	private String iDIssueDistrictId;
	private String customerNameAr;
	private String customerNameEn;
	private String birthDate;
	private Long sexId;
	private Long clientTypeId;
	private String email;
	private String phone;
	private String addressAr;
	private String addressEn;
	private Long cityId;
	private Long countryId;
	private Long nationalityId;
	private String externalcode;
	private String occupation;
	private String postalNo;


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdDate() {
		return idDate;
	}

	public void setIdDate(String idDate) {
		this.idDate = idDate;
	}

	public String getIdMaturityDate() {
		return idMaturityDate;
	}

	public void setIdMaturityDate(String idMaturityDate) {
		this.idMaturityDate = idMaturityDate;
	}

	public Long getClientAML() {
		return clientAML;
	}

	public void setClientAML(Long clientAML) {
		this.clientAML = clientAML;
	}

	public String getiDIssueCityId() {
		return iDIssueCityId;
	}

	public void setiDIssueCityId(String iDIssueCityId) {
		this.iDIssueCityId = iDIssueCityId;
	}

	public String getiDIssueCountryId() {
		return iDIssueCountryId;
	}

	public void setiDIssueCountryId(String iDIssueCountryId) {
		this.iDIssueCountryId = iDIssueCountryId;
	}

	public String getiDIssueDistrictId() {
		return iDIssueDistrictId;
	}

	public void setiDIssueDistrictId(String iDIssueDistrictId) {
		this.iDIssueDistrictId = iDIssueDistrictId;
	}

	public String getCustomerNameAr() {
		return customerNameAr;
	}

	public void setCustomerNameAr(String customerNameAr) {
		this.customerNameAr = customerNameAr;
	}

	public String getCustomerNameEn() {
		return customerNameEn;
	}

	public void setCustomerNameEn(String customerNameEn) {
		this.customerNameEn = customerNameEn;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Long getSexId() {
		return sexId;
	}

	public void setSexId(Long sexId) {
		this.sexId = sexId;
	}

	public Long getClientTypeId() {
		return clientTypeId;
	}

	public void setClientTypeId(Long clientTypeId) {
		this.clientTypeId = clientTypeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressAr() {
		return addressAr;
	}

	public void setAddressAr(String addressAr) {
		this.addressAr = addressAr;
	}

	public String getAddressEn() {
		return addressEn;
	}

	public void setAddressEn(String addressEn) {
		this.addressEn = addressEn;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(Long nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getExternalcode() {
		return externalcode;
	}

	public void setExternalcode(String externalcode) {
		this.externalcode = externalcode;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPostalNo() {
		return postalNo;
	}

	public void setPostalNo(String postalNo) {
		this.postalNo = postalNo;
	}

	@Override
	public String toString() {
		return "AzimutAccount [fullName=" + fullName + ", idDate=" + idDate + ", idMaturityDate=" + idMaturityDate
				+ ", clientAML=" + clientAML + ", iDIssueCityId=" + iDIssueCityId + ", iDIssueCountryId="
				+ iDIssueCountryId + ", iDIssueDistrictId=" + iDIssueDistrictId + ", customerNameAr=" + customerNameAr
				+ ", customerNameEn=" + customerNameEn + ", birthDate=" + birthDate + ", sexId=" + sexId
				+ ", clientTypeId=" + clientTypeId + ", email=" + email + ", phone=" + phone + ", addressAr="
				+ addressAr + ", addressEn=" + addressEn + ", cityId=" + cityId + ", countryId=" + countryId
				+ ", nationalityId=" + nationalityId + ", externalcode=" + externalcode + ", occupation=" + occupation
				+ ", postalNo=" + postalNo + "]";
	}
	
	
	
	

}
