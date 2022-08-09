package innovitics.azimut.utilities.dbutilities.specifications.childparent;

import org.springframework.stereotype.Component;

import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.utilities.dbutilities.specifications.EntityChildParentSpecification;
@Component
public class QuestionSpecification extends EntityChildParentSpecification<Question, KYCPage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5557533202688471962L;

}
