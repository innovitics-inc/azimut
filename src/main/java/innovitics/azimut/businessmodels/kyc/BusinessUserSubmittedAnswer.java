package innovitics.azimut.businessmodels.kyc;

import java.util.Arrays;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessUserSubmittedAnswer extends BaseBusinessEntity{

	private Long questionId;
	private BusinessSubmittedAnswer[] answers;
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public BusinessSubmittedAnswer[] getAnswers() {
		return answers;
	}
	public void setAnswers(BusinessSubmittedAnswer[] answers) {
		this.answers = answers;
	}
	@Override
	public String toString() {
		return "BusinessUserSubmittedAnswer [questionId=" + questionId + ", answers=" + Arrays.toString(answers) + "]";
	}
	
}
