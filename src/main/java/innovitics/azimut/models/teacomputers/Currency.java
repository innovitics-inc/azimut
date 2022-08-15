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
@Table(name="azimut_currencies")

public class Currency extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id; 
	private Long  systemCurrencyCode;
	private Long  currencyId;
	private String englishCurrencyName;
	private String arabicCurrencyName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSystemCurrencyCode() {
		return systemCurrencyCode;
	}
	public void setSystemCurrencyCode(Long systemCurrencyCode) {
		this.systemCurrencyCode = systemCurrencyCode;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public String getEnglishCurrencyName() {
		return englishCurrencyName;
	}
	public void setEnglishCurrencyName(String englishCurrencyName) {
		this.englishCurrencyName = englishCurrencyName;
	}
	public String getArabicCurrencyName() {
		return arabicCurrencyName;
	}
	public void setArabicCurrencyName(String arabicCurrencyName) {
		this.arabicCurrencyName = arabicCurrencyName;
	}
	
	
	
}
