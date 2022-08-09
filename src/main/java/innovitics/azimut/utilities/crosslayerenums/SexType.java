package innovitics.azimut.utilities.crosslayerenums;

public enum SexType {
	MALE(1,"male"),
	FEMALE(2,"female"),
	MORALE(3,"morale")
	;

	SexType(int typeId, String type) {
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
