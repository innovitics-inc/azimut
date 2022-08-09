package innovitics.azimut.rest.entities.teacomputers;

public class CompanyBankAccountOutput {

	private Long bankId;
	private String bankName;
	private Long branchId;
	private String branchName;
	private String accountNo;
	private String swiftCode;
	private String iban;

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
	@Override
	public String toString() {
		return "CompanyBankAccountOutput [bankId=" + bankId + ", bankName=" + bankName + ", branchId=" + branchId
				+ ", branchName=" + branchName + ", accountNo=" + accountNo + ", swiftCode=" + swiftCode + ", iban="
				+ iban + "]";
	}
	
	
}
