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
@Table(name="azimut_countries")

public class Country extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private Long systemCountryCode;
	private Long countryId;
	private String englishCountryName;
	private String arabicCountryName;
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
	
	
	
	
	
}
