package innovitics.azimut.repositories.teacomputers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.teacomputers.City;

@Repository
public interface CityDynamicRepository extends JpaRepository<City, Long>,JpaSpecificationExecutor<City>,EntityGraphJpaSpecificationExecutor<City>  {

}
