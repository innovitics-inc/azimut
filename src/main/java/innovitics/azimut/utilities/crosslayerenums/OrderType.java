
package innovitics.azimut.utilities.crosslayerenums;


public enum OrderType {
	BUY(1,"Buy"),
	SELL(2,"Sell"),
	OTHER(3,"Other")
	
	;

	OrderType(int typeId, String type) {
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
