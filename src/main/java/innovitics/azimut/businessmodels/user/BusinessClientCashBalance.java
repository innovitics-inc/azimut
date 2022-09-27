package innovitics.azimut.businessmodels.user;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessClientCashBalance extends BaseBusinessEntity{

	private Long idType;
	private String idNumber;
	private Long currencyID;
    private String currencyName;
    private Double balance;
    private Double pendingTransfer;
    private Double inPendingTrans;
    private Double outPendingTrans;
    private Double currencyRate;
	
	
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
	@Override
	public String toString() {
		return "BusinessClientCashBalance [idType=" + idType + ", idNumber=" + idNumber + ", currencyID=" + currencyID
				+ ", currencyName=" + currencyName + ", balance=" + balance + ", pendingTransfer=" + pendingTransfer
				+ ", inPendingTrans=" + inPendingTrans + ", outPendingTrans=" + outPendingTrans + ", currencyRate="
				+ currencyRate + "]";
	}
    

	
	
}
