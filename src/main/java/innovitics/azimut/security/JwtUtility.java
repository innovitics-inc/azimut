package innovitics.azimut.security;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.BusinessUserInterface;
import innovitics.azimut.businessmodels.user.Token;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Component
public class JwtUtility extends JwtUtil{

	
	@Override 
	public Token generateTokenUsingUserDetails(BusinessUserInterface businessUser)
	{
		Token token=new Token();
		StringBuffer valueToEncrypt=new StringBuffer();
		
		if(businessUser!=null)
		{	
			/*
			valueToEncrypt.append(businessUser.getCountryPhoneCode());
			valueToEncrypt.append(businessUser.getPhoneNumber());
			valueToEncrypt.append(".");
			valueToEncrypt.append(businessUser.getId());
			valueToEncrypt.append(".");
			valueToEncrypt.append(businessUser.getUserId());
			*/
			valueToEncrypt.append(".");
			valueToEncrypt.append(UUID.randomUUID());
			valueToEncrypt.append(".");
			valueToEncrypt.append(this.configProperties.getJwTokenKey());
			valueToEncrypt.append(".");
			valueToEncrypt.append(DateUtility.getCurrentTimeInMilliSeconds());
			
		}
		String tokenString=this.aes.encrypt(valueToEncrypt.toString());
				
		token.setTokenString(tokenString);
		token.setTokenExpiry(this.calculateExpirationDate());
		token.setRefreshTokenString(this.aes.encrypt(tokenString));
		token.setRefreshTokenExpiry(this.calculateExpirationDate());
		
		//this.businessUserService.editUser(businessUser);
	
		return token;
		
	}
	
	@Override
	public Token validateTokenInBodyThenGenerateNewToken(String token,String refreshToken) throws BusinessException
	{
		String tokenSubString=token.substring(7);
		if(StringUtility.stringsMatch(tokenSubString,this.aes.decrypt(refreshToken)))
		{
			
				BusinessUser businessUser=this.businessUserService.getByUserPhone(this.extractUsername(tokenSubString,true));
				return this.generateTokenUsingUserDetails(businessUser);
		}	
		else
		{
			throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
		}
	}
	
	
	
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String username= extractUsername(token);
		return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
	}

	public BusinessUser getBusinessUserFromToken(String token) throws BusinessException
	{
		return this.businessUserService.getByUserPhone(this.extractUsername(token,false));
		
	}
	
	
	public String extractUsername(String token,boolean isDecrypted) 
	{
		return StringUtility.generateSubStringStartingFromCertainIndex(isDecrypted?token:this.aes.decrypt(token),'.');
	}
	
}
