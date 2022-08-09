package innovitics.azimut.businessmodels.user;

public enum BusinessFlow {
	GO_TO_REGISTRATION(1,"GO_TO_REGISTRATION"),
	
	VERIFY_PASSWORD(2,"VERIFY_PASSWORD"),
	SET_PASSWORD(3,"SET_PASSWORD");

	


	BusinessFlow(int flowId, String flowMessage) {
		this.flowId=flowId;
		this.flowMessage=flowMessage;
	}

	private final int flowId;
	private final String flowMessage;
	
	public int getFlowId() {
		return flowId;
	}
	public String getFlowMessage() {
		return flowMessage;
	}
	
	
	

}
