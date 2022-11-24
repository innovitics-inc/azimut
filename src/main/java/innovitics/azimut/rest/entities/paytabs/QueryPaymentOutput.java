package innovitics.azimut.rest.entities.paytabs;

public class QueryPaymentOutput extends PaytabsOutput{

	
	private Double cartAmount;
	
	private String responseStatus;

	public Double getCartAmount() {
		return cartAmount;
	}

	public void setCartAmount(Double cartAmount) {
		this.cartAmount = cartAmount;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	
	
}
