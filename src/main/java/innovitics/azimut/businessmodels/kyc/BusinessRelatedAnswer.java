package innovitics.azimut.businessmodels.kyc;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessRelatedAnswer extends BaseBusinessEntity{

	private Long relatedAnswerId;
	private String relatedAnswerType;
	private String relatedAnswerValue;
	private String countryPhoneCode;
	String countryCode;
	public Long getRelatedAnswerId() {
		return relatedAnswerId;
	}
	public void setRelatedAnswerId(Long relatedAnswerId) {
		this.relatedAnswerId = relatedAnswerId;
	}
	public String getRelatedAnswerType() {
		return relatedAnswerType;
	}
	public void setRelatedAnswerType(String relatedAnswerType) {
		this.relatedAnswerType = relatedAnswerType;
	}
	public String getRelatedAnswerValue() {
		return relatedAnswerValue;
	}
	public void setRelatedAnswerValue(String relatedAnswerValue) {
		this.relatedAnswerValue = relatedAnswerValue;
	}
	public String getCountryPhoneCode() {
		return countryPhoneCode;
	}
	public void setCountryPhoneCode(String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Override
	public String toString() {
		return "BusinessRelatedAnswer [relatedAnswerId=" + relatedAnswerId + ", relatedAnswerType=" + relatedAnswerType
				+ ", relatedAnswerValue=" + relatedAnswerValue + ", countryPhoneCode="
				+ countryPhoneCode + "]";
	}
	
	
}
