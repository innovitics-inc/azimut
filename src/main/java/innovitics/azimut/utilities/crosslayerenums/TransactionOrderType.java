package innovitics.azimut.utilities.crosslayerenums;

public enum TransactionOrderType {

	WITHDRAW(1,"Withdraw"),
	INJECT(2,"Inject"),
	OTHER(3,"Other")
	
	;

	TransactionOrderType(int typeId, String type) {
		this.typeId=typeId;
		this.type=type;
	}

	private final int typeId;
	private final String type;
	public int getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}

	
	
}
