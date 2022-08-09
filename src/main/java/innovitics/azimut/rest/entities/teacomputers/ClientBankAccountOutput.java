package innovitics.azimut.rest.entities.teacomputers;

public class ClientBankAccountOutput {

	private Long bankId;
	private String bankName;
	private Long branchId;
	private String branchName;
	private Long currencyId;
	private String currencyName;
	private String accountNo;
	private Long accountId;
	private String swiftCode;
	private String iban;
	private Long accountStatus;
	private Long statusId;
	private String accountStatusName;
	private String signature;
	
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getAccountStatusName() {
		return accountStatusName;
	}
	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
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
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "ClientBankAccountOutput [bankId=" + bankId + ", bankName=" + bankName + ", branchId=" + branchId
				+ ", branchName=" + branchName + ", currencyId=" + currencyId + ", currencyName=" + currencyName
				+ ", accountNo=" + accountNo + ", accountId=" + accountId + ", swiftCode=" + swiftCode + ", iban="
				+ iban + ", accountStatus=" + accountStatus + ", statusId=" + statusId + ", accountStatusName="
				+ accountStatusName + ", signature=" + signature + "]";
	}
	
	
	
	
}
