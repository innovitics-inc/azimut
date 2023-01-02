package innovitics.azimut.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import innovitics.azimut.businessmodels.user.BusinessUser;
public class BusinessUserHolder extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892591689952752165L;
	
	private BusinessUser businessUser;
	public BusinessUserHolder(String username, String password, Collection<? extends GrantedAuthority> authorities,BusinessUser businessUser) {
		super(username, password, authorities);
		this.businessUser=businessUser;
	}
	public BusinessUser getBusinessUser() {
		return businessUser;
	}
	public void setBusinessUser(BusinessUser businessUser) {
		this.businessUser = businessUser;
	}
	
	

}
