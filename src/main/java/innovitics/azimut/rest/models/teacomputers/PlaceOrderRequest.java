package innovitics.azimut.rest.models.teacomputers;

public class PlaceOrderRequest extends TeaComputerRequest{

	private Integer Quanity;
	
	private Long OrderValue;
	
	private String ExternalOrderID;
	
	private String OrderDate;
	
	private Long OrderTypeId;
	
	private Long FundID;

	public Integer getQuanity() {
		return Quanity;
	}

	public void setQuanity(Integer quanity) {
		Quanity = quanity;
	}

	public Long getOrderValue() {
		return OrderValue;
	}

	public void setOrderValue(Long orderValue) {
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
	
	
	
	
}
