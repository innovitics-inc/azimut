package innovitics.azimut.rest.models;
import org.springframework.http.client.ClientHttpResponse;

public class BaseRestResponse  {

	protected String status;
	protected String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
