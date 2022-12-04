package innovitics.azimut.businessmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "response", singular = "response")
public class BusinessPayment extends BusinessAzimutClient{
	
	@JsonProperty("orderValue")
	private Double amount;
	private String notes;
	private String referenceTransactionId;
	
	private String transactionStatus;
	private String redirectUrl;
	private Integer action;
	private String returnUrl;
	private String cartId;
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getReferenceTransactionId() {
		return referenceTransactionId;
	}
	public void setReferenceTransactionId(String referenceTransactionId) {
		this.referenceTransactionId = referenceTransactionId;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public BusinessPayment(String referenceTransactionId) {
	
		this.referenceTransactionId = referenceTransactionId;
	}
	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public BusinessPayment() {
		
	}
	

}
