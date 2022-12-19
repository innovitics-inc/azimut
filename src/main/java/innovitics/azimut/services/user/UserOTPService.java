package innovitics.azimut.services.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.UserOTP;
import innovitics.azimut.repositories.user.UserOtpRepository;
import innovitics.azimut.services.AbstractService;
@Service
public class UserOTPService extends AbstractService<UserOTP, String>{

	@Autowired UserOtpRepository userOtpRepository;
	
	
	public UserOTP save(UserOTP userOTP)
	{
		return this.userOtpRepository.save(userOTP);
	}
	
	
}
