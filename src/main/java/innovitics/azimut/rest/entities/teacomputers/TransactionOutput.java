package innovitics.azimut.rest.entities.teacomputers;

public class TransactionOutput {

	private String transDate;
	private String transTypeName;
	private String orderTypeName;
	private String transStatusName;
	private String transValue;
	private String netValue;
	private String currencyId;
	private String currencyName;
	private Integer statusType;
	private Integer orderType;
	private String signature;
	
	
	
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
	public Integer getStatusType() {
		return statusType;
	}
	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}


	
	
}
