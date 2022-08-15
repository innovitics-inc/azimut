package innovitics.azimut.rest.models.teacomputers;

public class AddClientBankAccountRequest extends TeaComputerRequest {

	
    private Long IdTypeId;
    private String IdNumber;
    private Long BankId;
    private Long BranchId;
    private Long CurrencyId;
    private String AccountNo;
    private String SwiftCode;
    private String IBAN;
	public Long getIdTypeId() {
		return IdTypeId;
	}
	public void setIdTypeId(Long idTypeId) {
		IdTypeId = idTypeId;
	}
	public String getIdNumber() {
		return IdNumber;
	}
	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}
	public Long getBankId() {
		return BankId;
	}
	public void setBankId(Long bankId) {
		BankId = bankId;
	}
	public Long getBranchId() {
		return BranchId;
	}
	public void setBranchId(Long branchId) {
		BranchId = branchId;
	}
	public Long getCurrencyId() {
		return CurrencyId;
	}
	public void setCurrencyId(Long currencyId) {
		CurrencyId = currencyId;
	}
	public String getAccountNo() {
		return AccountNo;
	}
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	public String getSwiftCode() {
		return SwiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		SwiftCode = swiftCode;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
    
    
    
    
}
