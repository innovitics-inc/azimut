package innovitics.azimut.repositories.user;

import java.util.List;

import innovitics.azimut.models.user.UserOTP;

public interface UserOtpRepositoryCustom {

	public List<UserOTP> getFilteredUserOTP(String userPhone,String functionality);
	
}
