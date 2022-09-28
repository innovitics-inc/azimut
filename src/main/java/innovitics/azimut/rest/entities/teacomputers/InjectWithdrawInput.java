package innovitics.azimut.rest.entities.teacomputers;

public class InjectWithdrawInput extends TeaComputerInput{
	
    private Long currencyId;
    private Double  orderValue;
    private String accountNo;
	
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
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
    
    

}
