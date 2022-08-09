package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.AzimutDataLookup;
@Repository
public interface AzimutDataLookupDynamicRepository extends JpaRepository<AzimutDataLookup, Long>,JpaSpecificationExecutor<AzimutDataLookup>{

}
