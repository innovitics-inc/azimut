package innovitics.azimut.businessmodels.kyc;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "questions", singular = "question")
public class BusinessQuestion extends BaseBusinessEntity{
	
	private Long id;
	private String questionText;
	private String answerType;
	private int questionOrder;
	private Boolean isAnswerMandatory;
	private Long parentKYCPageId;
	private List<BusinessQuestion> subQuestions;
	List<BusinessAnswer> answers;
	private List<BusinessSubmittedAnswer> userAnswers;
	private Integer objectType;
	private Integer width;
	private String questionPlaceHolder;
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
	public List<BusinessQuestion> getSubQuestions() {
		return subQuestions;
	}
	public void setSubQuestions(List<BusinessQuestion> subQuestions) {
		this.subQuestions = subQuestions;
	}
	public List<BusinessSubmittedAnswer> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(List<BusinessSubmittedAnswer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	public Integer getObjectType() {
		return objectType;
	}
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public String getQuestionPlaceHolder() {
		return questionPlaceHolder;
	}
	public void setQuestionPlaceHolder(String questionPlaceHolder) {
		this.questionPlaceHolder = questionPlaceHolder;
	}
	@Override
	public String toString() {
		return "BusinessQuestion [id=" + id + ", questionText=" + questionText + ", answerType=" + answerType
				+ ", questionOrder=" + questionOrder + ", isAnswerMandatory=" + isAnswerMandatory + ", parentKYCPageId="
				+ parentKYCPageId + ", subQuestions=" + subQuestions + ", answers=" + answers + ", userAnswers="
				+ userAnswers + ", objectType=" + objectType + ", width=" + width + "]";
	}



	
	
}
