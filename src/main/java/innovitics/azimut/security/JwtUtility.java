package innovitics.azimut.security;

import java.util.UUID;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.Token;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
public class JwtUtility extends JwtUtil{

	
	@Override 
	public Token generateTokenUsingUserDetails(BusinessUser businessUser)
	{
		Token token=new Token();
		StringBuffer valueToEncrypt=new StringBuffer();
		
		if(businessUser!=null)
		{	
			
			valueToEncrypt.append(businessUser.getCountryPhoneCode());
			valueToEncrypt.append(businessUser.getPhoneNumber());
			valueToEncrypt.append(".");
			valueToEncrypt.append(businessUser.getId());
			valueToEncrypt.append(".");
			valueToEncrypt.append(businessUser.getUserId());
			valueToEncrypt.append(".");
			valueToEncrypt.append(UUID.randomUUID());
			valueToEncrypt.append(".");
			valueToEncrypt.append(this.configProperties.getJwTokenKey());
			
		}
		String tokenString=this.aes.encrypt(valueToEncrypt.toString());
				
		token.setTokenString(tokenString);
		token.setTokenExpiry(this.calculateExpirationDate());
		token.setRefreshTokenString(this.aes.encrypt(tokenString));
		token.setRefreshTokenExpiry(this.calculateExpirationDate());
		
		try 
		{
			this.businessUserService.editUser(businessUser);
		} 
		catch (BusinessException exception) 
		{
			exception.printStackTrace();
		}
		
		return token;
		
	}
	
	@Override
	public Token validateTokenInBodyThenGenerateNewToken(String token,String refreshToken) throws BusinessException
	{
		String tokenSubString=token.substring(7);
		if(StringUtility.stringsMatch(tokenSubString,this.aes.decrypt(refreshToken)))
		{
				BusinessUser businessUser=this.businessUserService.getByUserPhone(this.extractUsername(tokenSubString));
				return this.generateTokenUsingUserDetails(businessUser);
		}	
		else
		{
			throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
		}
	}
}
