package innovitics.azimut.rest.models.teacomputers;

public class PlaceOrderRequest extends TeaComputerRequest{

	private Integer Quantity;
	
	private Double OrderValue;
	
	private String ExternalOrderID;
	
	private String OrderDate;
	
	private Long OrderTypeId;
	
	private Long FundID;

	private String TransactionID;


	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}

	public Double getOrderValue() {
		return OrderValue;
	}

	public void setOrderValue(Double orderValue) {
		OrderValue = orderValue;
	}

	public String getExternalOrderID() {
		return ExternalOrderID;
	}

	public void setExternalOrderID(String externalOrderID) {
		ExternalOrderID = externalOrderID;
	}

	public String getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}

	public Long getOrderTypeId() {
		return OrderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		OrderTypeId = orderTypeId;
	}

	public Long getFundID() {
		return FundID;
	}

	public void setFundID(Long fundID) {
		FundID = fundID;
	}

	public String getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	
	
	
	
}
