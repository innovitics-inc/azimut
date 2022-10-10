package innovitics.azimut.utilities.businessutilities;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.services.user.UserBlockageService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.UserMapper;
@Component
public class UserBlockageUtility extends ParentUtility

{
	@Autowired UserBlockageService userBlockageService; 

	
	public Object checkUserBlockage(Integer numberOfTrials,String blockageDurationMinutes,BusinessUser tokenizedBusinessUser,UserMapper userMapper,Object object, String methodName,Object[] parameters,Class<?>[] paramterTypes,ErrorCode errorCode) throws BusinessException
	{
		int actualNumberOfTrials= numberOfTrials!=null?numberOfTrials.intValue():0;
		UserBlockage userBlockage=this.userBlockageService.findByUserId(tokenizedBusinessUser.getId());
		if(userBlockage!=null)
		{
				if(userBlockage.getErrorCount()!=null&&userBlockage.getErrorCount()>=actualNumberOfTrials)
				{
					if(this.getMinutesBefore(blockageDurationMinutes).before(userBlockage.getUpdatedAt()))	
					{	
						throw new BusinessException(ErrorCode.USER_BLOCKED);
					}
					
				}				
				else				
				{
						try 
						{
							Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
							userBlockage.setErrorCount(0);
							this.userBlockageService.updateUserBlockage(userBlockage);
							return result;
						}					
						catch(Exception exception)
						{
							this.logger.info("Could not invoke method:::");							
							exception.printStackTrace();
						}
				}				
				
		}
			return null;
	}
	
	public UserBlockage addUserBlockage(User user)
	{
		
		return this.userBlockageService.addUserBlockage(user);
		
	}
	public UserBlockage updateUserBlockage(UserBlockage userBlockage)
	{
		
		return this.userBlockageService.updateUserBlockage(userBlockage);
		
	}
	public UserBlockage getUserBlockage(Long userId)
	{	
		UserBlockage userBlockage=this.userBlockageService.findByUserId(userId);
		if(userBlockage!=null)
		{
			userBlockage.setUser(null);
		}
		return 	userBlockage;
	}	
	
}
