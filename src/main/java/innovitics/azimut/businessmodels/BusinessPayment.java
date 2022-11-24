package innovitics.azimut.businessmodels;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "response", singular = "response")
public class BusinessPayment extends BusinessAzimutClient{
	
	private Double amount;
	private String notes;
	private String referenceTransactionId;
	private Long transactionId;
	private String transactionStatus;
	private String redirectUrl;
	
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
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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
	
	public BusinessPayment() {
		
	}
	

}
