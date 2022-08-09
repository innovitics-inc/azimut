package innovitics.azimut.utilities.crosslayerenums;

public enum TransactionStatus {

	PENDING(1,"PENDING"),
	TIKCET(2,"POSTED"),
	CANCELED(3,"CANCELED"),
	OTHER(4,"OTHER")
	;

	TransactionStatus(int statusId, String status) {
		this.statusId=statusId;
		this.status=status;
	}

	private final int statusId;
	private final String status;
	public int getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	
	

	
}
