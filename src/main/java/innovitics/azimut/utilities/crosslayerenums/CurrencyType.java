package innovitics.azimut.utilities.crosslayerenums;

public enum CurrencyType {

	EGYPTIAN_POUND(1,"EGP"),
	US_DOLLAR(2,"USD"),
	OTHER(3,"OTHER")
	;

	CurrencyType(long typeId, String type) {
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
