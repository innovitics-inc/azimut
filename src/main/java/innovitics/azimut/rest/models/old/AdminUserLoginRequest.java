package innovitics.azimut.rest.models.old;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminUserLoginRequest extends OldSystemRequest{

	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AdminUserLoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	
	
}
