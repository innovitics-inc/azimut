package innovitics.azimut.rest.entities.paytabs;

public class QueryPaymentInput extends PaytabsInput{

	private String transactionReference;

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	
	
}
