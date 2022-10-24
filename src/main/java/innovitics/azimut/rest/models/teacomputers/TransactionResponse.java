package innovitics.azimut.rest.models.teacomputers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse extends TeaComputerResponse {

	private String transDate;
	private String transTypeName;
	private String orderTypeName;
	private String transStatusName;
	private String statusName;
	private String transValue;
	private String netValue;
	private String currencyId;
	private String currencyName;
	private String statusType;
	private String orderType;


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
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
