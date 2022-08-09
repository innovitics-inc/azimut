package innovitics.azimut.repositories.kyc;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.kyc.KYCPage;
@Repository
public interface KYCPageDynamicRepository  extends JpaRepository<KYCPage, Long>,JpaSpecificationExecutor<KYCPage>,EntityGraphJpaSpecificationExecutor<KYCPage>{

     @EntityGraph(value = "KYCPage.details",attributePaths = {"questions"},type = EntityGraphType.LOAD )
	 Optional<KYCPage> findById(Long id);
     
     @Query(value="Select weight from kyc_pages where id= ? ",nativeQuery = true)
	 Integer findPageWeightById(Long id);
     
     
}
