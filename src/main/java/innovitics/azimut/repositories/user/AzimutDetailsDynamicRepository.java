package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.azimutdetails.AzimutDetails;
@Repository
public interface AzimutDetailsDynamicRepository extends JpaRepository<AzimutDetails, Long>,JpaSpecificationExecutor<AzimutDetails>{

	
	
}
