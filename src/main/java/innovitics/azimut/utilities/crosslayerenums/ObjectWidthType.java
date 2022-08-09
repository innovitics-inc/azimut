package innovitics.azimut.utilities.crosslayerenums;

public enum ObjectWidthType {

	WIDTH_100(1,"100"),
	WIDTH_50OR100(2,"50/100")
	;

	ObjectWidthType(int typeId, String type) {
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
