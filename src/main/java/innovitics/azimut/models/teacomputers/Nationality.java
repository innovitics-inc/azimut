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
@Table(name="azimut_nationalities")

public class Nationality extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private Long systemNationalityCode;
	private Long NationalityId;
	private String arabicNationalityName;
	private String englishNationalityName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSystemNationalityCode() {
		return systemNationalityCode;
	}
	public void setSystemNationalityCode(Long systemNationalityCode) {
		this.systemNationalityCode = systemNationalityCode;
	}
	public Long getNationalityId() {
		return NationalityId;
	}
	public void setNationalityId(Long nationalityId) {
		NationalityId = nationalityId;
	}
	public String getArabicNationalityName() {
		return arabicNationalityName;
	}
	public void setArabicNationalityName(String arabicNationalityName) {
		this.arabicNationalityName = arabicNationalityName;
	}
	public String getEnglishNationalityName() {
		return englishNationalityName;
	}
	public void setEnglishNationalityName(String englishNationalityName) {
		this.englishNationalityName = englishNationalityName;
	}
	

}
