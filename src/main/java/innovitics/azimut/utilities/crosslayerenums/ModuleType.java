package innovitics.azimut.utilities.crosslayerenums;

public enum ModuleType {
	
	CASH(5,"Cash"),
	SWIFT(22,"SWIFT"),
	OTHER(3,"OTHER")
	;

	ModuleType(long typeId, String type) {
		this.typeId=typeId;
		this.type=type;
	}

	private final long typeId;
	private final String type;
	public long getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	
}
