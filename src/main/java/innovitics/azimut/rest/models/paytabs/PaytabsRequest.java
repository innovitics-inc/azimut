package innovitics.azimut.rest.models.paytabs;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.rest.models.BaseRestRequest;

public class PaytabsRequest extends BaseRestRequest{

	@JsonProperty("profile_id")
	protected Integer profileId;
	@JsonProperty("tran_type")
	protected String transType;
	@JsonProperty("tran_class")
	protected String transClass;
	@JsonProperty("paypage_lang")
	protected String payPageLang;
	@JsonProperty("callback")
	private String callbackUrl;
	@JsonProperty("return")
	private String returnUrl;
	
	
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransClass() {
		return transClass;
	}
	public void setTransClass(String transClass) {
		this.transClass = transClass;
	}
	public String getPayPageLang() {
		return payPageLang;
	}
	public void setPayPageLang(String payPageLang) {
		this.payPageLang = payPageLang;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}
