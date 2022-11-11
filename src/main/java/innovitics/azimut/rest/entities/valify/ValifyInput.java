package innovitics.azimut.rest.entities.valify;

import innovitics.azimut.rest.entities.BaseInput;

public class ValifyInput extends BaseInput {

	private String bundleKey;
	private String token;
	private String lang;
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
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setBaseUrl() {
		return this.configProperties.getValifyUrl()+"/";
	}
	
	
	
	
}
