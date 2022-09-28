package innovitics.azimut.utilities.crosslayerenums;

public enum ClientBankAccountStatus {

	PENDING(3,"Pending"),
	DEACTIVATED(2,"Deactivated"),
	ACTIVE(1,"Active");

	
	ClientBankAccountStatus(int statusId, String status) {
		this.statusId=statusId;
		this.status=status;
	}

	private final int statusId;
	private final String status;
	public long getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}

	
	
}
