package innovitics.azimut.businessmodels.user;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessClientCashBalance extends BaseBusinessEntity{

	private Long idType;
	private String idNumber;
	
	private Long currencyID;
    private String currencyName;
    private Long balance;
    private Long pendingTransfer;
	
	
	public Long getIdType() {
		return idType;
	}
	public void setIdType(Long idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
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
    

	
	
}
