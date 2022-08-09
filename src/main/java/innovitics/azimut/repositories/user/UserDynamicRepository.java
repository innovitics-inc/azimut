package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.User;

@Repository
public interface UserDynamicRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{

}
