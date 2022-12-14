package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetClientBalanceOutput extends TeaComputerOutput{
	private Long currencyID;
    private String currencyName;
    private Double balance;
    private Double pendingTransfer;
    
    List<ClientBalanceOutput> clientBalanceOutputs;
    
    
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
	
	public List<ClientBalanceOutput> getClientBalanceOutputs() {
		return clientBalanceOutputs;
	}
	public void setClientBalanceOutputs(List<ClientBalanceOutput> clientBalanceOutputs) {
		this.clientBalanceOutputs = clientBalanceOutputs;
	}
	@Override
	public String toString() {
		return "GetClientBalanceOutput [currencyID=" + currencyID + ", currencyName=" + currencyName + ", balance="
				+ balance + ", pendingTransfer=" + pendingTransfer + "]";
	}
    

	
	
}
