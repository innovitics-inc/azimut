package innovitics.azimut.utilities.crosslayerenums;

public enum UserIdType {


	NATIONAL_ID(1,"National Id"),
	PASSPORT(3,"passport"),
	INSTITUTIONAL(5,"Institutional")
	;

	UserIdType(long typeId, String type) {
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
