package innovitics.azimut.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.exceptions.BusinessException;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	BusinessUserService businessUserService;
	@Override
	public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
		
		BusinessUser businessUser=new BusinessUser();
		try {
			businessUser = this.businessUserService.getByUserPhone(userPhone);
		} catch (BusinessException exception) {
		
			exception.printStackTrace();
		}
				
		return new User(businessUser.getUserPhone()," ",new ArrayList<>());
	}

}
