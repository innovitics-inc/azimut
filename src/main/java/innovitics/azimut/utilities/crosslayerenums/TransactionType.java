package innovitics.azimut.utilities.crosslayerenums;

public enum TransactionType {

	DEPOSIT(1,"DEPOSIT"),
	SWIFT(2,"SWIFT"),
	OTHER(3,"OTHER")
	;

	TransactionType(int typeId, String type) {
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
