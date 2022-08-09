package innovitics.azimut.repositories.user;

import java.util.List;

import innovitics.azimut.models.user.UserOTP;
import innovitics.azimut.repositories.AbstractRepository;

public class UserOtpRepositoryImpl extends AbstractRepository<UserOTP>  implements UserOtpRepositoryCustom{

	@Override
	public List<UserOTP> getFilteredUserOTP(String userPhone, String functionality) {
		return (List<UserOTP>)this.generateQuery("select * from user_otp where user_phone=? and functionality=?", UserOTP.class, null).getResultList();
	}

}
