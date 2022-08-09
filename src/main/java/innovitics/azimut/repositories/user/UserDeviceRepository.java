package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;
@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long>, UserDeviceRepositoryCustom{

}
