package innovitics.azimut.models.teacomputers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="azimut_cities")

public class City extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private Long systemCountryCode;
	private Long countryId;
	private Long systemCityCode;
	private Long cityId;
	private String englishCityName;
	private String arabicCityName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

}
