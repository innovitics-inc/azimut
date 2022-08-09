package innovitics.azimut.utilities.mapping.kyc;

import java.util.List;

import innovitics.azimut.businessmodels.kyc.BusinessRelatedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessUserSubmittedAnswer;
import innovitics.azimut.models.kyc.UserAnswer;

public class UserAnswersIntermediary {

	UserAnswer parentAnswer;
	List<UserAnswer> relatedAnswers;
	BusinessSubmittedAnswer parentBusinessAnswer;
	List<BusinessUserSubmittedAnswer> relatedUserAnswers;
	List<BusinessRelatedAnswer> relatedBusinessAnswers ;
	
	
	

	public UserAnswer getParentAnswer() {
		return parentAnswer;
	}

	public void setParentAnswer(UserAnswer parentAnswer) {
		this.parentAnswer = parentAnswer;
	}

	public List<UserAnswer> getRelatedAnswers() {
		return relatedAnswers;
	}

	public void setRelatedAnswers(List<UserAnswer> relatedAnswers) {
		this.relatedAnswers = relatedAnswers;
	}

	
	
	
	public BusinessSubmittedAnswer getParentBusinessAnswer() {
		return parentBusinessAnswer;
	}

	public void setParentBusinessAnswer(BusinessSubmittedAnswer parentBusinessAnswer) {
		this.parentBusinessAnswer = parentBusinessAnswer;
	}


	
	
	public UserAnswersIntermediary(BusinessSubmittedAnswer parentBusinessAnswer,
			List<BusinessRelatedAnswer> relatedBusinessAnswers) {
		super();
		this.parentBusinessAnswer = parentBusinessAnswer;
		this.relatedBusinessAnswers = relatedBusinessAnswers;
	}

	public UserAnswersIntermediary(UserAnswer parentAnswer, List<UserAnswer> relatedAnswers) {
		super();
		this.parentAnswer = parentAnswer;
		this.relatedAnswers = relatedAnswers;
	}

	
	
	public UserAnswersIntermediary() {
		super();
	}

	@Override
	public String toString() {
		return "UserAnswersIntermediary [parentAnswer=" + parentAnswer + ", relatedAnswers=" + relatedAnswers
				+ ", parentBusinessAnswer=" + parentBusinessAnswer + ", relatedUserAnswers=" + relatedUserAnswers
				+ ", relatedBusinessAnswers=" + relatedBusinessAnswers + "]";
	}

}
