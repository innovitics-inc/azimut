package innovitics.azimut.repositories.kyc;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.UserAnswer;

public interface UserAnswerDynamicRepository extends JpaRepository<UserAnswer, Long>,JpaSpecificationExecutor<UserAnswer>,EntityGraphJpaSpecificationExecutor<UserAnswer> {

	 @EntityGraph(value = "UserAnswer.details",attributePaths = {"answerId","countryPhoneCode","answerValue","answerType","documentName","documentSize","documentUrl","documentSubDirectory","countryCode"},type = EntityGraphType.FETCH )
	 Optional<UserAnswer> findById(Long id);

	 
	 @Query(value="update user_answers set deleted_at=sysdate() where page_id=? and user_id=? and deleted_at is null",nativeQuery = true)
     @Modifying
     @Transactional
	 void deleteOldUserAnswersForThePage(Long pageId,Long userId);
	 
}
