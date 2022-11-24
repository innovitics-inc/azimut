package innovitics.azimut.rest.models.paytabs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaytabsQueryRequest extends PaytabsRequest {

	@JsonProperty("tran_ref")
	private String transactionReference;

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	
	
}
