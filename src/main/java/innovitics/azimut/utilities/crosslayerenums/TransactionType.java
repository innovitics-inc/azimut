package innovitics.azimut.utilities.crosslayerenums;

public enum TransactionType {

	DEPOSIT(1,1,"DEPOSIT",""),
	SWIFT(2,2,"SWIFT",""),
	OTHER(3,3,"OTHER","")
	;


	

	private TransactionType(int typeId, int teacomputerId,  String type,String typeAr) {
		this.typeId = typeId;
		this.type = type;
		this.teacomputerId = teacomputerId;
		this.typeAr = typeAr;
	}

	private final int typeId;
	private final String type;
	private final int teacomputerId;
	private final String typeAr;
	
	public int getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	public int getTeacomputerId() {
		return teacomputerId;
	}
	public String getTypeAr() {
		return typeAr;
	}
	public static TransactionType getById(int id) 
	{
	    for(TransactionType transactionType : values()) 
	    {
	        if(transactionType.typeId==id)
	        {
	        	return transactionType;
	        }
	    }
	    return TransactionType.OTHER;
	}

	
}
