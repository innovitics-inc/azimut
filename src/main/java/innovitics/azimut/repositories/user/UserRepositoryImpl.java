package innovitics.azimut.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.AbstractRepository;

public class UserRepositoryImpl extends AbstractRepository<User>  implements UserRepositoryCustom{




	@Override
	public List<User> findByParam(String param) {
		return (List<User>)this.generateQuery("Select * from app_users where password= ?", User.class, param).getResultList();
		
	}
	@Override
	public User findByUserPhone(String userPhone) {
		return (User) this.generateQuery("Select * from app_users where user_phone= ?",User.class, userPhone).getSingleResult();
	}

	@Override
	public User findByUserPhoneAndPassword(String userPhone, String password) {
		return (User) this.generateQuery("Select * from app_users where user_phone= ? and password= binary ?",User.class, userPhone,password).getSingleResult();
	}
	@Override
	public User findByPhoneNumber(String phoneCode,String phoneNumber) {
		return (User) this.generateQuery("Select * from app_users where country_phone_code=? and phone_number=?",User.class,phoneCode,phoneNumber).getSingleResult();
	}
	@Override
	public User findByPhoneCodePhoneNumberPassword(String phoneCode, String phoneNumber, String password) {
		return (User) this.generateQuery("Select * from app_users where country_phone_code=? and phone_number=? and password= binary ?",User.class,phoneCode,phoneNumber,password).getSingleResult();
	}
	
}