package innovitics.azimut.rest.entities.teacomputers;

public class GetTransactionsInput extends TeaComputerInput {

	private String fromDate;
	private String toDate;
	private String username;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "GetTransactionsInput [fromDate=" + fromDate + ", toDate=" + toDate + ", username=" + username
				+ ", locale=" + locale + ", idNumber=" + idNumber + ", idTypeId=" + idTypeId + ", password=" + password
				+ "]";
	}
	
	
	
}
