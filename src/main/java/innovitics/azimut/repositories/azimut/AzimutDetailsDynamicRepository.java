package innovitics.azimut.repositories.azimut;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.azimutdetails.AzimutDetailsLookup;
@Repository
public interface AzimutDetailsDynamicRepository extends JpaRepository<AzimutDetailsLookup, Long>,JpaSpecificationExecutor<AzimutDetailsLookup>{

	
	
}
