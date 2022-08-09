package innovitics.azimut.repositories.user;

import java.util.List;

import innovitics.azimut.models.user.User;

public interface UserRepositoryCustom {

	List<User> findByParam(String param);
	
	User findByUserPhone(String username);
	
	User findByUserPhoneAndPassword(String username,String password);
	
	User findByPhoneNumber(String phoneCode,String phoneNumber);
	
	User findByPhoneCodePhoneNumberPassword(String phoneCode,String phoneNumber,String password);

}
