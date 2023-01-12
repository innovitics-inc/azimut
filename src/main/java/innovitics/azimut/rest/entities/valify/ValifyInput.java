package innovitics.azimut.rest.entities.valify;

import innovitics.azimut.rest.entities.BaseInput;

public class ValifyInput extends BaseInput {

	private String bundleKey;
	private String token;
	
	public String getBundleKey() {
		return bundleKey;
	}
	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
