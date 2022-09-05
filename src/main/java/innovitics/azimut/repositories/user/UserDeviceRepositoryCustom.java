package innovitics.azimut.repositories.user;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;

public interface UserDeviceRepositoryCustom {

	List<UserDevice> findLatestUserDeviceLogin(Long userId,String deviceId);

}
