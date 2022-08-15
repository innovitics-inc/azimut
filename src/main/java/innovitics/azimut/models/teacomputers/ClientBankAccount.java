package innovitics.azimut.models.teacomputers;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="client_bank_accounts")

public class ClientBankAccount extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private Long userId; 
    private Long idTypeId;
    private String  idNumber;
    
	private String englishBankName;
	private String arabicBankName;

    private Long bankId;

    private String englishBranchName;
	private String arabicBranchName;

    private Long branchId;

	private String englishCurrencyName;
	private String arabicCurrencyName;
    

    private Long  currencyId;
    
    private String accountNo;
    private String swiftCode;
    private String iban;
	

	private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getIdTypeId() {
		return idTypeId;
	}
	public void setIdTypeId(Long idTypeId) {
		this.idTypeId = idTypeId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
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
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

}
