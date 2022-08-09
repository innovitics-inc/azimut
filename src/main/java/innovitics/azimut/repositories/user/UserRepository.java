package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.Notification;
import innovitics.azimut.models.user.User;
@Repository
public interface UserRepository  extends JpaRepository<User, Long>,UserRepositoryCustom{

}
