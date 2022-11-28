package innovitics.azimut.rest.models.teacomputers;

public class CancelOrderRequest extends TeaComputerRequest {

	
	 private String transactionID;
	 private String externalOrderID;
	 private Integer externalcode;
	  
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getExternalOrderID() {
		return externalOrderID;
	}
	public void setExternalOrderID(String externalOrderID) {
		this.externalOrderID = externalOrderID;
	}
	public Integer getExternalcode() {
		return externalcode;
	}
	public void setExternalcode(Integer externalcode) {
		this.externalcode = externalcode;
	}
	  
	  
}
