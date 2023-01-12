package innovitics.azimut.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;

@Service
public class MyAdminDetailsService extends MyUserDetailsService{

	
	
	@Override
	public BusinessUserHolder loadUserByUsername(String userPhone) throws UsernameNotFoundException {
		
		BusinessUser businessUser=new BusinessUser();
		try {
			businessUser = this.businessUserService.getByUserPhone(userPhone);
		} catch (BusinessException exception) {
		
			exception.printStackTrace();
		}
				
		return new BusinessUserHolder(businessUser.getUserPhone()," ",new ArrayList<>(),businessUser);
	}
}
