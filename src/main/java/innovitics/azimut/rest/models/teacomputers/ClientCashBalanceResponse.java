package innovitics.azimut.rest.models.teacomputers;

public class ClientCashBalanceResponse extends TeaComputerResponse {

	private String currencyID;
    private String currencyName;
    private String balance;
    private Double pendingTransfer;
    private Double inPendingTrans;
    private Double outPendingTrans;
    private String currencyRate;
    private Double totalBuyValue;
    
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
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
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
	public String getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}
	public Double getTotalBuyValue() {
		return totalBuyValue;
	}
	public void setTotalBuyValue(Double totalBuyValue) {
		this.totalBuyValue = totalBuyValue;
	}
	
	
}
