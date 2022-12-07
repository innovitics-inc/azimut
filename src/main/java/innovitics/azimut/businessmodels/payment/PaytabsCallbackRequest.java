package innovitics.azimut.businessmodels.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "response", singular = "response")
public class PaytabsCallbackRequest extends BaseBusinessEntity {

	@JsonProperty("tran_ref")
	private String transactionReference;
	
	@JsonProperty("merchant_id")
	private String merchantId;
	
	@JsonProperty("profile_id")
	private String profileId;
	
	@JsonProperty("cart_id")
	private String cartId;
	
	@JsonProperty("cart_amount")
	private String cartAmount;
	
	@JsonProperty("cart_description")
	private String cartDescription;
	
	@JsonProperty("cart_currency")
	private String cartCurrency;
	
	@JsonProperty("tran_currency")
	private String transactionCurrency;
	
	@JsonProperty("tran_total")
	private String transactionTotal;
	
	@JsonProperty("tran_type")
	private String transactionType;
	
	@JsonProperty("tran_class")
	private String transactionClass;
	
	@JsonProperty("payment_info")
	private PaymentInfo paymentInfo;
	
	
	@JsonProperty("payment_result")
	private PaymentResult paymentResult;
	
	@JsonProperty("customer_details")
	private CustomerDetails customerDetails;
	
	@JsonProperty("ipn_trace")
	private String ipnTrace;
	
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getCartDescription() {
		return cartDescription;
	}
	public void setCartDescription(String cartDescription) {
		this.cartDescription = cartDescription;
	}
	public String getCartCurrency() {
		return cartCurrency;
	}
	public void setCartCurrency(String cartCurrency) {
		this.cartCurrency = cartCurrency;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	public String getTransactionTotal() {
		return transactionTotal;
	}
	public void setTransactionTotal(String transactionTotal) {
		this.transactionTotal = transactionTotal;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionClass() {
		return transactionClass;
	}
	public void setTransactionClass(String transactionClass) {
		this.transactionClass = transactionClass;
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
	public String getCartAmount() {
		return cartAmount;
	}
	public void setCartAmount(String cartAmount) {
		this.cartAmount = cartAmount;
	}
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	public String getIpnTrace() {
		return ipnTrace;
	}
	public void setIpnTrace(String ipnTrace) {
		this.ipnTrace = ipnTrace;
	}
	@Override
	public String toString() {
		return "PaytabsCallbackRequest [transactionReference=" + transactionReference + ", paymentResult="
				+ paymentResult + ", paymentInfo=" + paymentInfo + ", cartId=" + cartId + "]";
	}
	
	
	
	
}
