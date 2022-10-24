package innovitics.azimut.utilities.crosslayerenums;

public enum TransactionStatus {

	PENDING(1,610,"PENDING","معلق"),
	TIKCET(2,593,"POSTED","تنفيذ"),
	CANCELED(3,595,"CANCELED","الغاء"),
	OTHER(4,0,"OTHER","أخرى")
	;

	TransactionStatus(int statusId,int teacomputerId,String status,String statusAr) {
		this.statusId=statusId;
		this.status=status;
		this.teacomputerTypeId = teacomputerId;
		this.statusAr = statusAr;
	}

	private final int statusId;
	private final String status;
	private final int teacomputerTypeId;
	private final String statusAr;
	
	public int getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	public int getTeacomputerTypeId() {
		return teacomputerTypeId;
	}
	public String getStatusAr() {
		return statusAr;
	}
	
	public static TransactionStatus getById(int id) 
	{
	    for(TransactionStatus transactionStatus : values()) 
	    {
	        if(transactionStatus.statusId==id)
	        {
	        	return transactionStatus;
	        }
	    }
	    return TransactionStatus.OTHER;
	}

	
}
