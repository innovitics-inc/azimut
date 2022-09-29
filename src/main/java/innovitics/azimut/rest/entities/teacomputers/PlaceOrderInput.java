package innovitics.azimut.rest.entities.teacomputers;

public class PlaceOrderInput extends TeaComputerInput{

	private Long orderTypeId;
	private Double orderValue;
	private Integer Quantity;
	private String ExternalOrderID;

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
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public String getExternalOrderID() {
		return ExternalOrderID;
	}

	public void setExternalOrderID(String externalOrderID) {
		ExternalOrderID = externalOrderID;
	}
	
}