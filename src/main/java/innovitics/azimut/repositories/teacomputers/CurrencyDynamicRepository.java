package innovitics.azimut.repositories.teacomputers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.teacomputers.Currency;
@Repository
public interface CurrencyDynamicRepository extends JpaRepository<Currency, Long>,JpaSpecificationExecutor<Currency>,EntityGraphJpaSpecificationExecutor<Currency>  {

}
