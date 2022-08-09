package innovitics.azimut.repositories.kyc;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.kyc.Question;

public interface QuestionDynamicRepository  extends JpaRepository<Question, Long>,JpaSpecificationExecutor<Question>,EntityGraphJpaSpecificationExecutor<Question> {

    	@EntityGraph(value = "Question.details",attributePaths = {"kycPage","parentQuestion","questionText","answerType","questionOrder","isAnswerMandatory","subQuestions","answers"},type = EntityGraphType.FETCH )
    	Optional<Question> findById(Long id);
    	
    	
    	@Query(value = "Select id from questions where is_answer_mandatory=1 and page_id= ?", nativeQuery = true)
    	List<Object>   getMandatoryQuestionCount(Long pageId);

}
