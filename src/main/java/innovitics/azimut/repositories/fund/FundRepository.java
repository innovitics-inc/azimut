package innovitics.azimut.repositories.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import innovitics.azimut.models.Fund;
import innovitics.azimut.models.azimutdetails.FundPrice;

public interface FundRepository extends JpaRepository<Fund, Long>,JpaSpecificationExecutor<Fund>,FundRepositoryCustom{

}
