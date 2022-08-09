package innovitics.azimut.utilities.crosslayerenums;

public enum AnswerType {

	RADIO(1,"RADIO"),
	CHECK(2,"CHECK"),
	DROP(3,"DROP"),
	TEXT(4,"TEXT"),
	RICH(5,"RICH"),
	EMAIL(6,"EMAIL"),
	CALENDER(7,"CALENDER"),
	DOCUMENT(8,"DOCUMENT"),
	PHONE(9,"PHONE")
	;

	AnswerType(int typeId, String type) {
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
