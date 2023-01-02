package innovitics.azimut.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.security.JwtUtil;
import innovitics.azimut.utilities.businessutilities.PhoneNumberBlockageUtility;
import innovitics.azimut.utilities.businessutilities.UserBlockageUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.UserMapper;

@Component
public class UserBlockageFilter extends GenericFilter implements Filter 
{
	private static final String AUTHORIZATION_HEADER = "Authorization";
	@Autowired private JwtUtil jwtUtil;
	@Autowired BusinessUserService businessUserService;
	@Autowired UserBlockageUtility userBlockageUtility;
	@Autowired PhoneNumberBlockageUtility phoneNumberBlockageUtility;
	@Autowired UserMapper userMapper;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest wrapper= ((HttpServletRequest)request);
		MyLogger.info("URI:::"+wrapper.getRequestURI());
		final String authorizationHeader = wrapper.getHeader(AUTHORIZATION_HEADER);			
		Boolean hasToken=authorizationHeader != null &&StringUtility.isStringPopulated(authorizationHeader)&&authorizationHeader.startsWith("Bearer ");
		BusinessUser businessUser=new BusinessUser();
			try {
				if(this.applyFilterOnPath(this.getFilterablePaths(), wrapper.getRequestURI()))
				{
					MyLogger.info("User Blockage Filter::");
					int actualNumberOfTrials= this.configProperties.getBlockageNumberOfTrials()!=null?this.configProperties.getBlockageNumberOfTrials().intValue():0;
					UserBlockage userBlockage=this.checkUserBlockageType(businessUser, wrapper,hasToken,authorizationHeader);
					
						if(userBlockage!=null)
						{
								if((userBlockage.getErrorCount()!=null&&userBlockage.getErrorCount()>=actualNumberOfTrials)&&(this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt())))
								{
									throw new BusinessException(ErrorCode.USER_BLOCKED);					
								}				
								else				
								{		
									chain.doFilter(request, response);
									this.updateUserBlockage(hasToken, userBlockage, businessUser, wrapper);
									userBlockage.setErrorCount(0);
									userBlockageUtility.updateUserBlockage(userBlockage);
									return;
								}								
						}
						else
						{
							chain.doFilter(request, response);
							return;
						}	
				  	}
				else
					{
						chain.doFilter(request, response);
					}
				} 			
				catch (IOException e) 
				{
					e.printStackTrace();
					setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,(HttpServletResponse)response,ErrorCode.OPERATION_NOT_PERFORMED);
				} 				
				catch (ServletException e) 
				{
					e.printStackTrace();
					setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,(HttpServletResponse)response,ErrorCode.OPERATION_NOT_PERFORMED);
				} catch (BusinessException exception) {
					exception.printStackTrace();
					setErrorResponse(HttpStatus.BAD_REQUEST,(HttpServletResponse)response,exception.getError());
				} 
		
	}

	@Override
	List<String> getFilterablePaths() {
		//return Arrays.asList("/api/azimut/trading/inject","/api/azimut/trading/withdraw","/api/azimut/trading/placeOrder","/api/forgotPassword","/api/saveUserTemporarily");
		return Arrays.asList("/azimut-0.0.1-SNAPSHOT/api/azimut/trading/cancelOrder");
	}

	
	UserBlockage checkUserBlockageType(BusinessUser businessUser,HttpServletRequest wrapper,boolean hasToken,String authorizationHeader) throws BusinessException
	{
		
		
		if (hasToken) 
		{
				businessUser=this.businessUserService.getByUserPhone(jwtUtil.extractUsername(authorizationHeader.substring(7)));
				UserBlockage userBlockage = userBlockageUtility.getUserBlockage(businessUser.getId(),false);
				return userBlockage;
		}
		else
		{
			UserBlockage userBlockage = phoneNumberBlockageUtility.getUserBlockage(this.getUserPhone(wrapper), false);
			return userBlockage;
		}
	}
	
	String getUserPhone(HttpServletRequest wrapper)
	{
		MyLogger.info("Parameters:::"+wrapper.getParameter("countryPhoneCode")+"::::::"+wrapper.getParameter("phoneNumber"));
		return "+"+wrapper.getParameter("countryPhoneCode")+wrapper.getParameter("phoneNumber");
	}
	
	void updateUserBlockage(boolean hasToken,UserBlockage userBlockage,BusinessUser businessUser,HttpServletRequest wrapper)
	{
		if(hasToken)
		{
			userBlockage.setUser(userMapper.convertBusinessUnitToBasicUnit(businessUser, false));
		}
		else
		{
			userBlockage.setUserPhone(this.getUserPhone(wrapper));
		}
	}
}
