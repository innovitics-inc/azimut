package innovitics.azimut.rest.models.paytabs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitiatePaymentResponse extends PaytabsResponse{
	
	
	@JsonProperty("redirect_url")
	private String redirectUrl;
	@JsonProperty("serviceId")
    private Integer serviceId;
    @JsonProperty("profileId")
    private Integer profileId;
    @JsonProperty("merchantId")
    private Integer merchantId;
    @JsonProperty("trace")
    private String trace;
    
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public String getTrace() {
		return trace;
	}
	public void setTrace(String trace) {
		this.trace = trace;
	}
	
	  
}
