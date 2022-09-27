package innovitics.azimut.rest.models.teacomputers;

public class PlaceOrderResponse extends TeaComputerResponse{

	 private Long TransactionID;
	 private String ValueDate;
	public Long getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(Long transactionID) {
		TransactionID = transactionID;
	}
	public String getValueDate() {
		return ValueDate;
	}
	public void setValueDate(String valueDate) {
		ValueDate = valueDate;
	}
	 
	 
}
