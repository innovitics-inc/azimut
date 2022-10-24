package innovitics.azimut.utilities.crosslayerenums;

public enum UserIdType {


	NATIONAL_ID(1,1,"National Id","بطاقة شخصية"),
	PASSPORT(3,3,"passport","جواز سفر"),
	INSTITUTIONAL(5,5,"Institutional","مؤسسة"),
	OTHER(0,0,"Other","أخرى"),
	;

	UserIdType(long typeId,long teacomputerTypeId ,String type,String typeAr) {
		this.teacomputerTypeId=teacomputerTypeId;
		this.typeId=typeId;
		this.type=type;
		this.typeAr=typeAr;
	}

	private final long typeId;
	private final long teacomputerTypeId;
	private final String type;
	private final String typeAr;
	
	
	public long getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	public long getTeacomputerTypeId() {
		return teacomputerTypeId;
	}
	public String getTypeAr() {
		return typeAr;
	}
	
	public static UserIdType getById(long id) 
	{
	    for(UserIdType userIdType : values()) 
	    {
	        if(userIdType.typeId==id)
	        {
	        	return userIdType;
	        }
	    }
	    return UserIdType.OTHER;
	}
}
