package innovitics.azimut.rest.entities.teacomputers;

public class GetClientBalanceOutput extends TeaComputerOutput{
	private Long currencyID;
    private String currencyName;
    private Long balance;
    private Long pendingTransfer;
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
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getPendingTransfer() {
		return pendingTransfer;
	}
	public void setPendingTransfer(Long pendingTransfer) {
		this.pendingTransfer = pendingTransfer;
	}
	@Override
	public String toString() {
		return "GetClientBalanceOutput [currencyID=" + currencyID + ", currencyName=" + currencyName + ", balance="
				+ balance + ", pendingTransfer=" + pendingTransfer + "]";
	}
    

	
	
}
