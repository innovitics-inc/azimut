package innovitics.azimut.rest.entities.teacomputers;

public class InjectWithdrawInput extends TeaComputerInput{
	
    private Long currencyId;
    private Double  orderValue;
    private Long accountId;
    private Long moduleType;
    private String ticketDoc;
    private String externalCode;
    private String referenceNo;
	
    public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public Double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getModuleType() {
		return moduleType;
	}
	public void setModuleType(Long moduleType) {
		this.moduleType = moduleType;
	}
	public String getTicketDoc() {
		return ticketDoc;
	}
	public void setTicketDoc(String ticketDoc) {
		this.ticketDoc = ticketDoc;
	}
	public String getExternalCode() {
		return externalCode;
	}
	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
    
    

}
