package innovitics.azimut.rest.entities.teacomputers;

public class LookUpOutputs {
	//Country
	private Long systemCountryCode;
	private Long countryId;
	private String englishCountryName;
	private String arabicCountryName;
	
	//City
	
	private Long systemCityCode;
	private Long cityId;
	private String englishCityName;
	private String arabicCityName;
	

	private Long systemNationalityCode;
	private Long nationalityId;
	private String englishNationalityName;
	private String arabicNationalityName;
	public Long getSystemCountryCode() {
		return systemCountryCode;
	}
	public void setSystemCountryCode(Long systemCountryCode) {
		this.systemCountryCode = systemCountryCode;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getEnglishCountryName() {
		return englishCountryName;
	}
	public void setEnglishCountryName(String englishCountryName) {
		this.englishCountryName = englishCountryName;
	}
	public String getArabicCountryName() {
		return arabicCountryName;
	}
	public void setArabicCountryName(String arabicCountryName) {
		this.arabicCountryName = arabicCountryName;
	}
	public Long getSystemCityCode() {
		return systemCityCode;
	}
	public void setSystemCityCode(Long systemCityCode) {
		this.systemCityCode = systemCityCode;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getEnglishCityName() {
		return englishCityName;
	}
	public void setEnglishCityName(String englishCityName) {
		this.englishCityName = englishCityName;
	}
	public String getArabicCityName() {
		return arabicCityName;
	}
	public void setArabicCityName(String arabicCityName) {
		this.arabicCityName = arabicCityName;
	}
	public Long getSystemNationalityCode() {
		return systemNationalityCode;
	}
	public void setSystemNationalityCode(Long systemNationalityCode) {
		this.systemNationalityCode = systemNationalityCode;
	}
	public Long getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(Long nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getEnglishNationalityName() {
		return englishNationalityName;
	}
	public void setEnglishNationalityName(String englishNationalityName) {
		this.englishNationalityName = englishNationalityName;
	}
	public String getArabicNationalityName() {
		return arabicNationalityName;
	}
	public void setArabicNationalityName(String arabicNationalityName) {
		this.arabicNationalityName = arabicNationalityName;
	}
	@Override
	public String toString() {
		return "LookUpOutputs [systemCountryCode=" + systemCountryCode + ", countryId=" + countryId
				+ ", englishCountryName=" + englishCountryName + ", arabicCountryName=" + arabicCountryName
				+ ", systemCityCode=" + systemCityCode + ", cityId=" + cityId + ", englishCityName=" + englishCityName
				+ ", arabicCityName=" + arabicCityName + ", systemNationalityCode=" + systemNationalityCode
				+ ", nationalityId=" + nationalityId + ", englishNationalityName=" + englishNationalityName
				+ ", arabicNationalityName=" + arabicNationalityName + "]";
	}
	
	
	
	
	
}
