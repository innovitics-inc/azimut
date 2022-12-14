package innovitics.azimut.rest.entities.teacomputers;

public class ClientBalanceOutput extends TeaComputerOutput{

	private Long currencyID;
    private String currencyName;
    private Double balance;
    private Double pendingTransfer;
    private Double inPendingTrans;
    private Double outPendingTrans;
    private Double currencyRate;
    private Double totalBuyValue;
    
	public Long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(Long currencyID) {
		this.currencyID = currencyID;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getPendingTransfer() {
		return pendingTransfer;
	}
	public void setPendingTransfer(Double pendingTransfer) {
		this.pendingTransfer = pendingTransfer;
	}
	public Double getInPendingTrans() {
		return inPendingTrans;
	}
	public void setInPendingTrans(Double inPendingTrans) {
		this.inPendingTrans = inPendingTrans;
	}
	public Double getOutPendingTrans() {
		return outPendingTrans;
	}
	public void setOutPendingTrans(Double outPendingTrans) {
		this.outPendingTrans = outPendingTrans;
	}
	public Double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}
	public Double getTotalBuyValue() {
		return totalBuyValue;
	}
	public void setTotalBuyValue(Double totalBuyValue) {
		this.totalBuyValue = totalBuyValue;
	}
	
}
