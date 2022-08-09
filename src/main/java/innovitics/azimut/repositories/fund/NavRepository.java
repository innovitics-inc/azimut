package innovitics.azimut.repositories.fund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.Nav;
@Repository
public interface NavRepository extends JpaRepository<Nav, Long>,NavRepositoryCustom {

}
