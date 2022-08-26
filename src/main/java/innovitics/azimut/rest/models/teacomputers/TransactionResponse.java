package innovitics.azimut.rest.models.teacomputers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse extends TeaComputerResponse {

	private String transDate;
	private String transTypeName;
	private String orderTypeName;
	private String transStatusName;
	private String transValue;
	private String netValue;
	private String currencyId;
	private String currencyName;


	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	
	public String getOrderTypeName() {
		return orderTypeName;
	}
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	public String getTransStatusName() {
		return transStatusName;
	}
	public void setTransStatusName(String transStatusName) {
		this.transStatusName = transStatusName;
	}
	public String getTransValue() {
		return transValue;
	}
	public void setTransValue(String transValue) {
		this.transValue = transValue;
	}
	public String getNetValue() {
		return netValue;
	}
	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	@Override
	public String toString() {
		return "TransactionResponse [transDate=" + transDate + ", transTypeName=" + transTypeName + ", orderTypeName="
				+ orderTypeName + ", transStatusName=" + transStatusName + ", transValue=" + transValue + ", netValue="
				+ netValue + ", currencyId=" + currencyId + ", currencyName=" + currencyName + ", Signature="
				+ Signature + ", Message=" + Message + ", ErrorCode=" + ErrorCode + "]";
	}



}
