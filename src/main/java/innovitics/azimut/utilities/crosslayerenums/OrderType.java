
package innovitics.azimut.utilities.crosslayerenums;


public enum OrderType {
	WITHDRAW(1,"Withdraw"),
	INJECT(2,"Inject"),
	BUY(3,"Buy"),
	SELL(4,"Sell"),
	OTHER(5,"Other")
	
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
