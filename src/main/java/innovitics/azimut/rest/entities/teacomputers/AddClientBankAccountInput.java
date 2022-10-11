package innovitics.azimut.rest.entities.teacomputers;

public class AddClientBankAccountInput extends TeaComputerInput {

	private Long bankId;
	private Long branchId;
	private Long currencyId;
	private String currencyName;
	private String accountNo;
	private String swiftCode;
	private String iban;
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
	@Override
	public String toString() {
		return "AddClientBankAccountInput [bankId=" + bankId + ", branchId=" + branchId + ", currencyId=" + currencyId
				+ ", currencyName=" + currencyName + ", accountNo=" + accountNo + ", swiftCode=" + swiftCode + ", iban="
				+ iban + "]";
	}

	
	
	
	
	
	
	
}
