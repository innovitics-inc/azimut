package innovitics.azimut.utilities.dbutilities.specifications.childparent;

import org.springframework.stereotype.Component;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;
import innovitics.azimut.utilities.dbutilities.specifications.EntityChildParentSpecification;

@Component
public class UserDeviceSpecification extends EntityChildParentSpecification<UserDevice, User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5281875069541046665L;

}
