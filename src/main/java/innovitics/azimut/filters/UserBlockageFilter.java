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

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.security.JwtUtil;
import innovitics.azimut.utilities.businessutilities.UserBlockageUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.UserMapper;

public class UserBlockageFilter extends GenericFilter //implements Filter 
{
	private static final String AUTHORIZATION_HEADER = "Authorization";
	@Autowired private JwtUtil jwtUtil;
	@Autowired BusinessUserService businessUserService;
	@Autowired UserBlockageUtility userBlockageUtility;
	@Autowired UserMapper userMapper;
	//@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		MyLogger.info("User Blockage Filter::");
		
		BusinessUser businessUser=new BusinessUser();
			try {
				final String authorizationHeader = ((HttpServletRequest)request).getHeader(AUTHORIZATION_HEADER);			

				if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
				{
						businessUser=this.businessUserService.getByUserPhone(jwtUtil.extractUsername(authorizationHeader.substring(7)));				
				}
				if(this.applyFilterOnPath(this.getFilterablePaths(), ((HttpServletRequest)request).getRequestURI()))
				{
					MyLogger.info("Checking User Blockage::::");
					int actualNumberOfTrials= this.configProperties.getBlockageNumberOfTrials()!=null?this.configProperties.getBlockageNumberOfTrials().intValue():0;
					UserBlockage userBlockage;
						userBlockage = userBlockageUtility.getUserBlockage(businessUser.getId(),false);
						if(userBlockage!=null)
						{
								if((userBlockage.getErrorCount()!=null&&userBlockage.getErrorCount()>=actualNumberOfTrials)&&(this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt())))
								{
									throw new BusinessException(ErrorCode.USER_BLOCKED);					
								}				
								else				
								{		
									chain.doFilter(request, response);
									userBlockage.setUser(userMapper.convertBusinessUnitToBasicUnit(businessUser, false));
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
		return Arrays.asList("/api/azimut/trading/inject","/api/azimut/trading/withdraw","/api/azimut/trading/placeOrder");
	}

}
