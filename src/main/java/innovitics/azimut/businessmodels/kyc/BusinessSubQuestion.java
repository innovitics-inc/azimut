package innovitics.azimut.businessmodels.kyc;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessSubQuestion extends BaseBusinessEntity{

	private Long id;
	private String questionText;
	private String answerType;
	private int questionOrder;
	private Boolean isAnswerMandatory;
	private Long parentKYCPageId;
	List<BusinessAnswer> answers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public int getQuestionOrder() {
		return questionOrder;
	}
	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}
	public Boolean getIsAnswerMandatory() {
		return isAnswerMandatory;
	}
	public void setIsAnswerMandatory(Boolean isAnswerMandatory) {
		this.isAnswerMandatory = isAnswerMandatory;
	}
	public Long getParentKYCPageId() {
		return parentKYCPageId;
	}
	public void setParentKYCPageId(Long parentKYCPageId) {
		this.parentKYCPageId = parentKYCPageId;
	}
	public List<BusinessAnswer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<BusinessAnswer> answers) {
		this.answers = answers;
	}
	
}
