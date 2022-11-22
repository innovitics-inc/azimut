package innovitics.azimut.rest.models.paytabs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitiatePaymentRequest extends PaytabsRequest {
	
	@JsonProperty("cart_id")
	private String cartId;
	@JsonProperty("cart_amount")
	private Double cartAmount;
	@JsonProperty("cart_currency")
	private String cartCurrency;
	@JsonProperty("cart_description")
	private String cartDescription;
	@JsonProperty("customer_details")
	private CustomerDetails customerDetails;
	@JsonProperty("shipping_details")
	private ShippingDetails shippingDetails;

	
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getCartCurrency() {
		return cartCurrency;
	}
	public void setCartCurrency(String cartCurrency) {
		this.cartCurrency = cartCurrency;
	}
	public Double getCartAmount() {
		return cartAmount;
	}
	public void setCartAmount(Double cartAmount) {
		this.cartAmount = cartAmount;
	}
	public String getCartDescription() {
		return cartDescription;
	}
	public void setCartDescription(String cartDescription) {
		this.cartDescription = cartDescription;
	}
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	public ShippingDetails getShippingDetails() {
		return shippingDetails;
	}
	public void setShippingDetails(ShippingDetails shippingDetails) {
		this.shippingDetails = shippingDetails;
	}
	
    
}
