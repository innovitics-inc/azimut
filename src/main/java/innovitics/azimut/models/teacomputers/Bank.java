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
@Table(name="azimut_banks")

public class Bank extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private Long bankId;
	private Long systemBankCode;
	private String englishBankName;
	private String arabicBankName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Long getSystemBankCode() {
		return systemBankCode;
	}
	public void setSystemBankCode(Long systemBankCode) {
		this.systemBankCode = systemBankCode;
	}
	public String getEnglishBankName() {
		return englishBankName;
	}
	public void setEnglishBankName(String englishBankName) {
		this.englishBankName = englishBankName;
	}
	public String getArabicBankName() {
		return arabicBankName;
	}
	public void setArabicBankName(String arabicBankName) {
		this.arabicBankName = arabicBankName;
	}
	
	
	
	
}
