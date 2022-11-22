package innovitics.azimut.businessmodels.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInfo {

	@JsonProperty("payment_method")
	private String paymentMethod;
	@JsonProperty("payment_description")
	private String paymentDescription;
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
	

	
	
}
