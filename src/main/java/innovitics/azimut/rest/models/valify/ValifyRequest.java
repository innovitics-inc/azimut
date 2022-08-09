package innovitics.azimut.rest.models.valify;

import innovitics.azimut.rest.models.BaseRestRequest;

public class ValifyRequest extends BaseRestRequest{
	
	protected String bundle_key;
	protected String document_type;
	protected String lang;
	
	public String getBundle_key() {
		return bundle_key;
	}

	public void setBundle_key(String bundle_key) {
		this.bundle_key = bundle_key;
	}

	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
	 
}
