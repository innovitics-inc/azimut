package innovitics.azimut.rest.entities.teacomputers;

public class FundTransactionOutput {

	private Long transactionId;
	private Long fundId;
	private String orderDate;
	private String valueDate;
	private Integer quantity;
	private Integer orderTypeId;
	private Double orderValue;
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public Double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}
	@Override
	public String toString() {
		return "FundTransactionOutput [transactionId=" + transactionId + ", fundId=" + fundId + ", orderDate="
				+ orderDate + ", valueDate=" + valueDate + ", quantity=" + quantity + ", orderTypeId=" + orderTypeId
				+ ", orderValue=" + orderValue + "]";
	}
	
	
	
}
