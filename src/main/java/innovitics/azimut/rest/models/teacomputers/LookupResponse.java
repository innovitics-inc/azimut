package innovitics.azimut.rest.models.teacomputers;

public class LookupResponse extends TeaComputerResponse{
	

	//Country
	private Long sytemCountryCode;
	private Long countryId;
	private String englishCountryName;
	private String arabicCountryName;
	
	//City
	
	private Long sytemCityCode;
	private Long cityId;
	private String englishCityName;
	private String arabicCityName;
	

	private Long sytemNationalityCode;
	private Long nationalityId;
	private String englishNationalityName;
	private String arabicNationalityName;

	
	
	public Long getSytemCountryCode() {
		return sytemCountryCode;
	}
	public void setSytemCountryCode(Long sytemCountryCode) {
		this.sytemCountryCode = sytemCountryCode;
	}
	public Long getSytemCityCode() {
		return sytemCityCode;
	}
	public void setSytemCityCode(Long sytemCityCode) {
		this.sytemCityCode = sytemCityCode;
	}
	public Long getSytemNationalityCode() {
		return sytemNationalityCode;
	}
	public void setSytemNationalityCode(Long sytemNationalityCode) {
		this.sytemNationalityCode = sytemNationalityCode;
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
	
}