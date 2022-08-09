package innovitics.azimut.rest.models.teacomputers;

public class GetFundsPricesRequest extends TeaComputerRequest {

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
		return "GetFundsPricesRequest [fromDate=" + fromDate + ", toDate=" + toDate + ", Signature=" + Signature
				+ ", IdTypeId=" + IdTypeId + ", IdNumber=" + IdNumber + ", UserName=" + UserName + ", Password="
				+ Password + ", FundId=" + FundId + "]";
	}
	
	
	
}
