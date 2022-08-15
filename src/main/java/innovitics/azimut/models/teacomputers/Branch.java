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
@Table(name="azimut_branches")

public class Branch extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private Long bankId;
	private Long systemBankCode;
	private Long branchId;
	private Long systemBranchCode;
	private String englishBranchName;
	private String arabicBranchName;
	
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
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getSystemBranchCode() {
		return systemBranchCode;
	}
	public void setSystemBranchCode(Long systemBranchCode) {
		this.systemBranchCode = systemBranchCode;
	}
	public String getEnglishBranchName() {
		return englishBranchName;
	}
	public void setEnglishBranchName(String englishBranchName) {
		this.englishBranchName = englishBranchName;
	}
	public String getArabicBranchName() {
		return arabicBranchName;
	}
	public void setArabicBranchName(String arabicBranchName) {
		this.arabicBranchName = arabicBranchName;
	}
	
	
	
	
}
