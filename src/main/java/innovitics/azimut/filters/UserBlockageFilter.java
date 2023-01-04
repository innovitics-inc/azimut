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
import innovitics.azimut.utilities.threading.CurrentRequestHolder;

@Component
public class UserBlockageFilter extends GenericFilter implements Filter 
{
	private static final String PATH = "/azimut-0.0.1-SNAPSHOT/api/azimut/trading/";
	@Autowired BusinessUserService businessUserService;
	@Autowired UserBlockageUtility userBlockageUtility;
	@Autowired PhoneNumberBlockageUtility phoneNumberBlockageUtility;
	@Autowired UserMapper userMapper;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest wrapper= ((HttpServletRequest)request);
		MyLogger.info("URI:::"+wrapper.getRequestURI());
		Boolean hasToken=CurrentRequestHolder.get()!=null&&CurrentRequestHolder.get().getId()!=null;
			try {
				if(this.applyFilterOnPath(this.getFilterablePaths(), wrapper.getRequestURI()))
				{
					MyLogger.info("User Blockage Filter::");
					int actualNumberOfTrials= this.configProperties.getBlockageNumberOfTrials()!=null?this.configProperties.getBlockageNumberOfTrials().intValue():0;
					UserBlockage userBlockage=this.checkUserBlockageType(wrapper,hasToken);
					
						if(userBlockage!=null)
						{
								if((userBlockage.getErrorCount()!=null&&userBlockage.getErrorCount()>=actualNumberOfTrials)&&(this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt())))
								{
									throw new BusinessException(ErrorCode.USER_BLOCKED);					
								}				
								else				
								{		
									chain.doFilter(request, response);
									this.updateUserBlockage(hasToken, userBlockage,wrapper);
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
						return;
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
		return Arrays.asList(PATH+"placeOrder",PATH+"inject",PATH+"withdraw");
	}

	
	UserBlockage checkUserBlockageType(HttpServletRequest wrapper,boolean hasToken) throws BusinessException
	{
		
		
		if (hasToken) 
		{
				UserBlockage userBlockage = userBlockageUtility.getUserBlockage(CurrentRequestHolder.get().getId(),false);
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
	
	void updateUserBlockage(boolean hasToken,UserBlockage userBlockage,HttpServletRequest wrapper)
	{
		if(hasToken)
		{
			userBlockage.setUser(userMapper.convertBusinessUnitToBasicUnit(CurrentRequestHolder.get(), false));
		}
		else
		{
			userBlockage.setUserPhone(this.getUserPhone(wrapper));
		}
		userBlockage.setErrorCount(0);
		userBlockageUtility.updateUserBlockage(userBlockage);
	}
}
