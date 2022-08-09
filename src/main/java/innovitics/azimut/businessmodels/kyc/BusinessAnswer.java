package innovitics.azimut.businessmodels.kyc;

import java.util.List;
import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;
import innovitics.azimut.utilities.mapping.kyc.UserAnswersIntermediary;
@CustomJsonRootName(plural = "answers", singular = "answer")
public class BusinessAnswer extends BaseBusinessEntity{

	private Long id;
	private List<BusinessAnswer> relatedAnswers;
	private String answerType;
	private String answerOrder;
	private String answerOption;
	private String answerPlaceHolder;
	private Boolean isRelatedAnswerMandatory;
	private String relatedQuestionText;
	private Boolean isAnswerMandatory;
	private List<BusinessSubmittedAnswer> businessSubmittedAnswers;
	private List<BusinessSubmittedAnswer> childBusinessSubmittedAnswers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<BusinessAnswer> getRelatedAnswers() {
		return relatedAnswers;
	}
	public void setRelatedAnswers(List<BusinessAnswer> relatedAnswers) {
		this.relatedAnswers = relatedAnswers;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public String getAnswerOrder() {
		return answerOrder;
	}
	public void setAnswerOrder(String answerOrder) {
		this.answerOrder = answerOrder;
	}
	public String getAnswerOption() {
		return answerOption;
	}
	public void setAnswerOption(String answerOption) {
		this.answerOption = answerOption;
	}
	
	public String getAnswerPlaceHolder() {
		return answerPlaceHolder;
	}
	public void setAnswerPlaceHolder(String answerPlaceHolder) {
		this.answerPlaceHolder = answerPlaceHolder;
	}
	public Boolean getIsRelatedAnswerMandatory() {
		return isRelatedAnswerMandatory;
	}
	public void setIsRelatedAnswerMandatory(Boolean isRelatedAnswerMandatory) {
		this.isRelatedAnswerMandatory = isRelatedAnswerMandatory;
	}
	public String getRelatedQuestionText() {
		return relatedQuestionText;
	}
	public void setRelatedQuestionText(String relatedQuestionText) {
		this.relatedQuestionText = relatedQuestionText;
	}
	public List<BusinessSubmittedAnswer> getBusinessSubmittedAnswers() {
		return businessSubmittedAnswers;
	}
	public void setBusinessSubmittedAnswers(List<BusinessSubmittedAnswer> businessSubmittedAnswers) {
		this.businessSubmittedAnswers = businessSubmittedAnswers;
	}
	public List<BusinessSubmittedAnswer> getChildBusinessSubmittedAnswers() {
		return childBusinessSubmittedAnswers;
	}
	public void setChildBusinessSubmittedAnswers(List<BusinessSubmittedAnswer> childBusinessSubmittedAnswers) {
		this.childBusinessSubmittedAnswers = childBusinessSubmittedAnswers;
	}
	public Boolean getIsAnswerMandatory() {
		return isAnswerMandatory;
	}
	public void setIsAnswerMandatory(Boolean isAnswerMandatory) {
		this.isAnswerMandatory = isAnswerMandatory;
	}

	
	
	

	
}
