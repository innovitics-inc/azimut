package innovitics.azimut.businessmodels.kyc;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessSubmittedAnswer extends BaseBusinessEntity{
	
	private Long parentAnswerId;
	private Long answerId;
	private String answerType;
	private String answerValue;
	private MultipartFile document;
	private String countryPhoneCode;
	private String countryCode;
	private Long questionId;
	private BusinessRelatedAnswer [] relatedAnswers;
	private List<BusinessSubmittedAnswer> relatedUserAnswers;
	
	public Long getParentAnswerId() {
		return parentAnswerId;
	}
	public void setParentAnswerId(Long parentAnswerId) {
		this.parentAnswerId = parentAnswerId;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}
	public MultipartFile getDocument() {
		return document;
	}
	public void setDocument(MultipartFile document) {
		this.document = document;
	}
	public String getCountryPhoneCode() {
		return countryPhoneCode;
	}
	public void setCountryPhoneCode(String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}
	
	public BusinessRelatedAnswer[] getRelatedAnswers() {
		return relatedAnswers;
	}
	public void setRelatedAnswers(BusinessRelatedAnswer[] relatedAnswers) {
		this.relatedAnswers = relatedAnswers;
	}
	public List<BusinessSubmittedAnswer> getRelatedUserAnswers() {
		return relatedUserAnswers;
	}
	public void setRelatedUserAnswers(List<BusinessSubmittedAnswer> relatedUserAnswers) {
		this.relatedUserAnswers = relatedUserAnswers;
	}

	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	@Override
	public String toString() {
		return "BusinessSubmittedAnswer [answerId=" + answerId + ", answerType=" + answerType + ", answerValue="
				+ answerValue + ", document=" + document + ", countryPhoneCode=" + countryPhoneCode
				+ ", relatedAnswers=" + Arrays.toString(relatedAnswers) + "]";
	}
	
	
	
}
