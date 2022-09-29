package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.UserBlockage;

@Repository
public interface UserBlockageRepository extends JpaRepository<UserBlockage, Long>,JpaSpecificationExecutor<UserBlockage>{

}
