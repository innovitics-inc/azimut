package innovitics.azimut.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.Token;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.fileutilities.FileReaderProp;
import innovitics.azimut.validations.Validation;
import innovitics.azimut.validations.validators.user.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtUtil {
	protected static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	
	@Autowired BusinessUserService businessUserService;
	@Autowired ConfigProperties configProperties;
	@Autowired RefreshToken  refreshToken;
	@Autowired Validation<AuthenticationRequest> validation;
	@Autowired ExceptionHandler exceptionHandler;
	@Autowired AES aes;
	@Autowired Hashing hashing;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser().setSigningKey(this.configProperties.getJwTokenKey()).parseClaimsJws(token).getBody();
	}
	
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Token generateToken(UserDetails userDetails)
	{
		Map<String,Object> claims= new HashMap<>();
		String tokenString=this.createToken(claims, userDetails.getUsername());
		Token token=new Token();
		token.setTokenString(tokenString);
		token.setTokenExpiry(this.calculateExpirationDate());
		token.setRefreshTokenString(this.aes.encrypt(tokenString));
		token.setRefreshTokenExpiry(this.calculateExpirationDate());
		return token;
	}
	
	private String createToken(Map<String, Object> claims, String subject)
	{
		String token="";
		token= Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(this.calculateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, this.configProperties.getJwTokenKey()).compact();
		
		return token;

	}
	
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String username= extractUsername(token);
		return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
	}

	public BusinessUser getBusinessUserFromToken(String token) throws BusinessException
	{
		return this.businessUserService.getByUserPhone(this.extractUsername(token));
		
	}
	
	 public Token refreshToken(AuthenticationRequest authenticationRequest) throws BusinessException 
	 {
		 /*
		 Token token=new Token();
		 this.validation.validate(authenticationRequest, refreshToken, StringUtility.AUTHENTICATION_CLASS_NAME);
		 //try
		 //{
	       if(StringUtility.isStringPopulated(this.extractUsername(authenticationRequest.getOldToken())))
	       {	
	    	   if(this.isTokenExpired(authenticationRequest.getOldToken()))
	    	   {
	    		   this.logger.info("Token was found to be expired::::::");
	    		   throw new BusinessException(ErrorCode.FAILED_TO_VALIDATE_TOKEN);
	    	   }
	    	   
	    	   
	    	   final Date createdDate = new Date();
	    	   final Date expirationDate = this.calculateExpirationDate();
	    	   final Claims claims = getAllClaimsFromToken(authenticationRequest.getOldToken());
	    	   claims.setIssuedAt(createdDate);
	    	   claims.setExpiration(expirationDate);
	    	   
	    	   token.setTokenString(Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, this.configProperties.getJwTokenKey()).compact());
	    	   token.setTokenExpiry(expirationDate);
	    	   
	       }
	       else
	       {
	    	   this.logger.info("User was not found::::::");
	    	   throw new BusinessException(ErrorCode.USER_NOT_FOUND);
	       }
	       
		 //}
	/*	   catch(ExpiredJwtException expiredJwtException)
  	   {
			   
		  this.logger.info("ExpiredJwtException caught:::::");
		  this.logger.info("ExpiredJwtException caught:::::"+expiredJwtException.getMessage());
  		  throw this.exceptionHandler.handleAsBusinessException(expiredJwtException, ErrorCode.FAILED_TO_VALIDATE_TOKEN); 
  	   }*/

	       
	    //   return token;
	    return null;
	    }
	 
	 private Date calculateExpirationDate() {
	        return new Date(System.currentTimeMillis()+(1000*60)*Long.parseLong(this.configProperties.getJwTokenDurationInMinutes()));
	    }
	 
	 private Claims getAllClaimsFromToken(String token) {
	        return Jwts.parser().setSigningKey(this.configProperties.getJwTokenKey()).parseClaimsJws(token).getBody();
	    }

		
	public Token generateTokenUsingUserDetails(BusinessUser businessUser)
	{
		UserDetails userDetails = new User(businessUser.getUserPhone(), " ",
				new ArrayList<>());

		Token token = this.generateToken(userDetails);
		return token;
	}
	
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
