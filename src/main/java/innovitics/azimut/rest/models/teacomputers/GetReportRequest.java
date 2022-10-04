package innovitics.azimut.rest.models.teacomputers;

public class GetReportRequest extends TeaComputerRequest {

	private Boolean showAbsReturn;
	private String  atDate;
	private String title;
	
	private String fromDate;
	private String toDate;
	
	private String reportType;
	
	private Long currencyId;
	
	
	public Boolean getShowAbsReturn() {
		return showAbsReturn;
	}
	public void setShowAbsReturn(Boolean showAbsReturn) {
		this.showAbsReturn = showAbsReturn;
	}
	public String getAtDate() {
		return atDate;
	}
	public void setAtDate(String atDate) {
		this.atDate = atDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
	
}
