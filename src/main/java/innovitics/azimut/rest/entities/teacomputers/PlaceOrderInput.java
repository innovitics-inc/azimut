package innovitics.azimut.rest.entities.teacomputers;

public class PlaceOrderInput extends TeaComputerInput{

	private Long orderTypeId;
	private Double orderValue;
	private Integer quantity;
	private String externalOrderID;
	private String transactionId;

	public Long getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public Double getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getExternalOrderID() {
		return externalOrderID;
	}

	public void setExternalOrderID(String externalOrderID) {
		this.externalOrderID = externalOrderID;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
