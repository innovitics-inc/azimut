package innovitics.azimut.utilities.dbutilities.specifications.childparent;

import org.springframework.stereotype.Component;

import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.utilities.dbutilities.specifications.EntityChildParentSpecification;
@Component
public class AnswerChildParentSpecification extends EntityChildParentSpecification<Answer, Question>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1622347388424533319L;

}
