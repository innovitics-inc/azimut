package innovitics.azimut.security;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.Token;
public class JwtUtility extends JwtUtil{

	
	@Override 
	public Token generateTokenUsingUserDetails(BusinessUser businessUser)
	{
		Token token=new Token();
		String valueToEncrypt="";
		
		
		token.setTokenString(this.aes.encrypt(businessUser.getId().toString()));
		
		this.aes.encrypt(null);
		return null;
		
	}
}
