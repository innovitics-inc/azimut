package innovitics.azimut.rest.entities.paytabs;

import innovitics.azimut.rest.entities.BaseOutput;

public class PaytabsOutput extends BaseOutput {

	private String referenceTransaction;

	public String getReferenceTransaction() {
		return referenceTransaction;
	}

	public void setReferenceTransaction(String referenceTransaction) {
		this.referenceTransaction = referenceTransaction;
	}
	
	
}
