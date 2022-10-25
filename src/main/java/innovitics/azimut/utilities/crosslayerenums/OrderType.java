
package innovitics.azimut.utilities.crosslayerenums;


public enum OrderType {
	BUY(1,1,"Buy","شراء"),
	SELL(2,2,"Sell","بيع"),
	OTHER(3,3,"Other","أخرى")
	
	;

	private OrderType(int typeId, int teacomputerTypeId, String type, String typeAr) {
		this.typeId = typeId;
		this.teacomputerTypeId = teacomputerTypeId;
		this.type = type;
		this.typeAr = typeAr;
	}
	private final int typeId;
	private final int teacomputerTypeId;
	private final String type;
	private final String typeAr;
	
	public int getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}

	public int getTeacomputerTypeId() {
		return teacomputerTypeId;
	}
	public String getTypeAr() {
		return typeAr;
	}
	public static OrderType getById(int id) 
	{
	    for(OrderType orderType : values()) 
	    {
	        if(orderType.teacomputerTypeId==id)
	        {
	        	return orderType;
	        }
	    }
	    return OrderType.OTHER;
	}

}
