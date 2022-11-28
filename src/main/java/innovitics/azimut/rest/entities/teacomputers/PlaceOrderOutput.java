package innovitics.azimut.rest.entities.teacomputers;

public class PlaceOrderOutput extends TeaComputerOutput{

	
	private Long transactionId;
	
	private String message;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
