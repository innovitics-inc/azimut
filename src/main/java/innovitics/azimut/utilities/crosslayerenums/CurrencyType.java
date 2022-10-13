package innovitics.azimut.utilities.crosslayerenums;

public enum CurrencyType {

	EGYPTIAN_POUND(1,"EGP"),
	US_DOLLAR(32,"USD")
	
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
	
	public static CurrencyType getById(long id) 
	{
	    for(CurrencyType currencyType : values()) 
	    {
	        if(currencyType.typeId==id)
	        {
	        	return currencyType;
	        }
	    }
	    return null;
	}
}
