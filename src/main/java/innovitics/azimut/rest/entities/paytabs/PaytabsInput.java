package innovitics.azimut.rest.entities.paytabs;

import innovitics.azimut.rest.entities.BaseInput;

public class PaytabsInput extends BaseInput{

	
	protected String transactionType;
	protected String transactionClass;
	protected String payPageLang;
	
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
	public String getPayPageLang() {
		return payPageLang;
	}
	public void setPayPageLang(String payPageLang) {
		this.payPageLang = payPageLang;
	}
	
	
}
