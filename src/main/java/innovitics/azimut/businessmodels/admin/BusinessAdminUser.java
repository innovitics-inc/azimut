package innovitics.azimut.businessmodels.admin;

import innovitics.azimut.businessmodels.user.BusinessUser;

public class BusinessAdminUser extends BusinessUser{

	public BusinessAdminUser() 
	{
	
	}
	
	public BusinessAdminUser(String email,String password) 
	{
		this.setEmailAddress(email);
		this.setPassword(password);
	}

	@Override
	public String getUsername() {
		return this.getEmailAddress();
	}
	
}
