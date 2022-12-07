package innovitics.azimut.businessmodels.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInfo {

	@JsonProperty("payment_method")
	private String paymentMethod;
	@JsonProperty("payment_description")
	private String paymentDescription;
	
	@JsonProperty("card_type")
	private String cardType;
	
	@JsonProperty("card_scheme")
	private String cardScheme;
	
	@JsonProperty("expiry_month")
	private String expiryMonth;
	
	@JsonProperty("expiry_year")
	private String expiryYear;
	
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentDescription() {
		return paymentDescription;
	}
	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardScheme() {
		return cardScheme;
	}
	public void setCardScheme(String cardScheme) {
		this.cardScheme = cardScheme;
	}
	public String getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public String getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}
	

	
	
}
