package innovitics.azimut.repositories.user;

import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.AbstractRepository;
@Repository
public class UserDeviceRepositoryImpl extends AbstractRepository<User>  implements UserDeviceRepositoryCustom {

}
