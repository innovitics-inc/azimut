package innovitics.azimut.repositories.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import innovitics.azimut.models.azimutdetails.FundPrice;

@Repository
public interface FundPriceDynamicRepository extends JpaRepository<FundPrice, Long>,JpaSpecificationExecutor<FundPrice>{

}
