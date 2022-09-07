package innovitics.azimut.utilities.crosslayerenums;

public enum OrderStatus {
	EXECUTED(1,"Executed"),
	PENDING(2,"Pending"),
	
	
	;

	OrderStatus(int typeId, String type) {
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
