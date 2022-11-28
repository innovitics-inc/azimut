package innovitics.azimut.rest.entities.teacomputers;

public class CancelOrderInput extends TeaComputerInput{

	private String transactionId;
	private String externalOrderId;
	private Integer externalCode;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getExternalOrderId() {
		return externalOrderId;
	}
	public void setExternalOrderId(String externalOrderId) {
		this.externalOrderId = externalOrderId;
	}
	public Integer getExternalCode() {
		return externalCode;
	}
	public void setExternalCode(Integer externalCode) {
		this.externalCode = externalCode;
	}
	
	
	
}
