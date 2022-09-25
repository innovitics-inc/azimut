package innovitics.azimut.utilities.crosslayerenums;

public enum BankAccountStatus {

	
	ACTIVE(1,"ACTIVE"),
	INACTIVE(2,"INACTIVE"),
	PENDING(3,"PENDING"),
	OTHER(4,"OTHER")
	;

	BankAccountStatus(long statusId, String status) {
		this.statusId=statusId;
		this.status=status;
	}

	private final long statusId;
	private final String status;
	public long getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	
}
