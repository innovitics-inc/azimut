package innovitics.azimut.businessmodels.valify;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "clientDetails", singular = "clientDetails")
public class BusinessValify extends BaseBusinessEntity {

	private String documentType;
	private String firstImage;
	private String secondImage;
	
	private String straightFace;
	private String smilingFace;
	
	private String leftSide;
	private String rightSide;
	
	
	private String frontImage;
	private String backImage;
	private String passportImage;
	private String token;
	private Boolean imagesSimilar;
	private Float confidence;
	private String firstName;
	private String fullName;
	private String street;
	private String area;
	private String frontNid;
	private String serialNumber;
	private String backNid;
	private String releaseDate;
	private String gender;
	private String maritalStatus;
	private String profession;
	private String religion;
	private String husbandName;
	private String expiryDate;
	private String name;
	private String surname;
	private String passportNumber;
	private String expirationDate;
	private String dateOfBirth;
	private String sex;
	private String nationality;
	private Integer validity;
	private Integer userStep;
	private String city;
	private String lastName;
	private String country;
	private String valifyTransactionId;
	private String userId;
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getSecondImage() {
		return secondImage;
	}
	public void setSecondImage(String secondImage) {
		this.secondImage = secondImage;
	}
	public String getFrontImage() {
		return frontImage;
	}
	public void setFrontImage(String frontImage) {
		this.frontImage = frontImage;
	}
	public String getBackImage() {
		return backImage;
	}
	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}
	public String getPassportImage() {
		return passportImage;
	}
	public void setPassportImage(String passportImage) {
		this.passportImage = passportImage;
	}
	
	public String getStraightFace() {
		return straightFace;
	}
	public void setStraightFace(String straightFace) {
		this.straightFace = straightFace;
	}
	public String getSmilingFace() {
		return smilingFace;
	}
	public void setSmilingFace(String smilingFace) {
		this.smilingFace = smilingFace;
	}
	public String getLeftSide() {
		return leftSide;
	}
	public void setLeftSide(String leftSide) {
		this.leftSide = leftSide;
	}
	public String getRightSide() {
		return rightSide;
	}
	public void setRightSide(String rightSide) {
		this.rightSide = rightSide;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getImagesSimilar() {
		return imagesSimilar;
	}
	public void setImagesSimilar(Boolean imagesSimilar) {
		this.imagesSimilar = imagesSimilar;
	}
	public Float getConfidence() {
		return confidence;
	}
	public void setConfidence(Float confidence) {
		this.confidence = confidence;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFrontNid() {
		return frontNid;
	}
	public void setFrontNid(String frontNid) {
		this.frontNid = frontNid;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getBackNid() {
		return backNid;
	}
	public void setBackNid(String backNid) {
		this.backNid = backNid;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getHusbandName() {
		return husbandName;
	}
	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Integer getValidity() {
		return validity;
	}
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	public Integer getUserStep() {
		return userStep;
	}
	public void setUserStep(Integer userStep) {
		this.userStep = userStep;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getValifyTransactionId() {
		return valifyTransactionId;
	}
	public void setValifyTransactionId(String valifyTransactionId) {
		this.valifyTransactionId = valifyTransactionId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "BusinessValify [documentType=" + documentType + ", firstImage=" + firstImage + ", secondImage="
				+ secondImage + ", straightFace=" + straightFace + ", smilingFace=" + smilingFace + ", leftSide="
				+ leftSide + ", rightSide=" + rightSide + ", frontImage=" + frontImage + ", backImage=" + backImage
				+ ", passportImage=" + passportImage + ", token=" + token + ", imagesSimilar=" + imagesSimilar
				+ ", confidence=" + confidence + ", firstName=" + firstName + ", fullName=" + fullName + ", street="
				+ street + ", area=" + area + ", frontNid=" + frontNid + ", serialNumber=" + serialNumber + ", backNid="
				+ backNid + ", releaseDate=" + releaseDate + ", gender=" + gender + ", maritalStatus=" + maritalStatus
				+ ", profession=" + profession + ", religion=" + religion + ", husbandName=" + husbandName
				+ ", expiryDate=" + expiryDate + ", name=" + name + ", surname=" + surname + ", passportNumber="
				+ passportNumber + ", expirationDate=" + expirationDate + ", dateOfBirth=" + dateOfBirth + ", sex="
				+ sex + ", nationality=" + nationality + ", validity=" + validity + ", userStep=" + userStep + "]";
	}
	
	
	
	
}
