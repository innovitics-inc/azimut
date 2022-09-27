package innovitics.azimut.rest.models.teacomputers;

public class GetValuationReportRequest extends TeaComputerRequest {

	private Boolean showAbsReturn;
	private String  atDate;
	private String title;
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
	
	
}
