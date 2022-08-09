package innovitics.azimut.repositories.fund;

import org.springframework.data.jpa.repository.JpaRepository;

import innovitics.azimut.models.Fund;

public interface FundRepository extends JpaRepository<Fund, Long>,FundRepositoryCustom{

}
