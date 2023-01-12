package innovitics.azimut.repositories.kyc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.kyc.Review;

@Repository
public interface ReviewDynamicRepository extends JpaRepository<Review, Long>,JpaSpecificationExecutor<Review>,EntityGraphJpaSpecificationExecutor<Review>{

	
	@Query(value = "select page_id from reviews where page_order in (select min(page_order) from reviews where user_id =?)", nativeQuery = true)
	Long   getIdOfThePageWithLeastOrder(Long userId);
}
