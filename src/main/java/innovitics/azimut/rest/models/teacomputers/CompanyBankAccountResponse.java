package innovitics.azimut.rest.models.teacomputers;

public class CompanyBankAccountResponse extends TeaComputerResponse{

	
	private Long accountID;
	private String bankId;
	private String bankName;
	private String branchId;
	private String branchName;
	private String accountNo;
	private String iban;
	private String accountStatus;
	private String accountStatusName;
	private String swiftCode;
	private String currencyID;
	private String currencyName;
	public Long getAccountID() {
		return accountID;
	}
	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountStatusName() {
		return accountStatusName;
	}
	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	@Override
	public String toString() {
		return "CompanyBankAccountResponse [accountID=" + accountID + ", bankId=" + bankId + ", bankName=" + bankName
				+ ", branchId=" + branchId + ", branchName=" + branchName + ", accountNo=" + accountNo + ", iban="
				+ iban + ", accountStatus=" + accountStatus + ", accountStatusName=" + accountStatusName
				+ ", swiftCode=" + swiftCode + ", currencyID=" + currencyID + ", currencyName=" + currencyName + "]";
	}
	
	
	

}
