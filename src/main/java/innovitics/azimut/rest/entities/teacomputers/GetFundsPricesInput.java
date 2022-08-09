package innovitics.azimut.rest.entities.teacomputers;

public class GetFundsPricesInput extends TeaComputerInput{

	private String fromDate;
	private String toDate;
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
	@Override
	public String toString() {
		return "GetFundsPricesInput [fromDate=" + fromDate + ", toDate=" + toDate + ", locale=" + locale + ", idNumber="
				+ idNumber + ", idTypeId=" + idTypeId + ", username=" + username + ", password=" + password
				+ ", bankId=" + bankId + "]";
	}
	
	
	
}
