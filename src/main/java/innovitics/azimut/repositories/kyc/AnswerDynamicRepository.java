package innovitics.azimut.repositories.kyc;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.models.kyc.KYCPage;

public interface AnswerDynamicRepository extends JpaRepository<Answer, Long>,JpaSpecificationExecutor<Answer>{

     @EntityGraph(value = "Answer.details",attributePaths = {"question","parentAnswer","answerType","answerOrder","answerOption","answerPlaceHolder","isRelatedAnswerMandatory","relatedQuestionText","relatedAnswers"},type = EntityGraphType.FETCH )
	 Optional<Answer> findById(Long id);

}
