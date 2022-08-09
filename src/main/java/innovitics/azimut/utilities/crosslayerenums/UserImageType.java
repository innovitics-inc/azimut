package innovitics.azimut.utilities.crosslayerenums;

public enum UserImageType {

	STRAIGHT(1,"Straight face"),
	SMILING(2,"Smiling face"),
	LEFT(3,"Left side"),
	RIGHT(4,"Right side"),
	FRONT_IMAGE(5,"Front image"),
	BACK_IMAGE(6,"Back image"),
	PASSPORT_IMAGE(7,"Passport image")
	;

	UserImageType(int typeId, String type) {
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
