package innovitics.azimut.businessmodels.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class PaytabsCallbackRequest extends BaseBusinessEntity {

	@JsonProperty("tran_ref")
	private String transactionReference;
	@JsonProperty
	private PaymentResult paymentResult;
	@JsonProperty("payment_info")
	private PaymentInfo paymentInfo;
	@JsonProperty("cart_id")
	private String cartId;
	
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public PaymentResult getPaymentResult() {
		return paymentResult;
	}
	public void setPaymentResult(PaymentResult paymentResult) {
		this.paymentResult = paymentResult;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	@Override
	public String toString() {
		return "PaytabsCallbackRequest [transactionReference=" + transactionReference + ", paymentResult="
				+ paymentResult + ", paymentInfo=" + paymentInfo + ", cartId=" + cartId + "]";
	}
	
	
	
	
}
