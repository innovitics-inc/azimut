package innovitics.azimut.rest.models.teacomputers;

public class GetReportResponse extends TeaComputerResponse{

	private String filePath;

	private String url;
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
