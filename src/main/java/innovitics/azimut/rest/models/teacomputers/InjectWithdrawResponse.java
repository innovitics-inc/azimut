package innovitics.azimut.rest.models.teacomputers;

public class InjectWithdrawResponse extends TeaComputerResponse{

	private String OrderId;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	@Override
	public String toString() {
		return "InjectWithdrawResponse [OrderId=" + OrderId + ", Signature=" + Signature + ", Message=" + Message
				+ ", ErrorCode=" + ErrorCode + ", errorMessage=" + errorMessage + "]";
	}
	
	
	
	
}
