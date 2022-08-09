package innovitics.azimut.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.models.user.AzimutEntity;

@Repository
public interface AzimutEntityDynamicRepository extends JpaRepository<AzimutEntity, Long>,JpaSpecificationExecutor<AzimutEntity>,EntityGraphJpaSpecificationExecutor<AzimutEntity> {
	
	@EntityGraph(value = "AzimutEntity.details",attributePaths = {"details"},type = EntityGraphType.LOAD )
	 Optional<AzimutEntity> findById(Long id);
    

}
