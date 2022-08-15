package innovitics.azimut.businessmodels.user;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.crosslayerenums.ClientBankAccountStatus;

public class BusinessClientBankAccountDetails extends BaseBusinessEntity{

	
	private Long id;
	private String image;
	private String bankName;
	private String accountNumber;
	private ClientBankAccountStatus clientBankAccountStatus;
	private String statusName;
	private Long status;
	private String swiftCode;
	private String currencyName;
	private String branchName;
	private Long branchId;
	private String iban;
	private Long accountId;
	private Long accountStatus;
	private Long currencyId;
	private String englishBankName;
	private String arabicBankName;
    private Long bankId;
    private String englishBranchName;
	private String arabicBranchName;
	private String englishCurrencyName;
	private String arabicCurrencyName;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public ClientBankAccountStatus getClientBankAccountStatus() {
		return clientBankAccountStatus;
	}
	public void setClientBankAccountStatus(ClientBankAccountStatus clientBankAccountStatus) {
		this.clientBankAccountStatus = clientBankAccountStatus;
	}
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(Long accountStatus) {
		this.accountStatus = accountStatus;
	}	
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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
	@Override
	public String toString() {
		return "BusinessClientBankAccountDetails [id=" + id + ", image=" + image + ", bankName=" + bankName
				+ ", accountNumber=" + accountNumber + ", clientBankAccountStatus=" + clientBankAccountStatus
				+ ", statusName=" + statusName + ", status=" + status + ", swiftCode=" + swiftCode + ", currencyName="
				+ currencyName + ", branchName=" + branchName + ", branchId=" + branchId + ", iban=" + iban
				+ ", accountId=" + accountId + ", accountStatus=" + accountStatus + "]";
	}
	
	
	
	
	
	
}
