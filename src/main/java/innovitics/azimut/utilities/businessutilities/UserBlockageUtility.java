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
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.UserMapper;
@Component
public class UserBlockageUtility extends ParentUtility

{
	@Autowired protected UserBlockageService userBlockageService; 

	
	public Object checkUserBlockage(Integer numberOfTrials,String blockageDurationMinutes,BusinessUser tokenizedBusinessUser,UserMapper userMapper,Object object, String methodName,Object[] parameters,Class<?>[] paramterTypes,ErrorCode errorCode) throws BusinessException
	{
		
		MyLogger.info("Checking User Blockage::::");
		int actualNumberOfTrials= numberOfTrials!=null?numberOfTrials.intValue():0;
		UserBlockage userBlockage=this.getUserBlockage(tokenizedBusinessUser.getId(),false);
		if(userBlockage!=null)
		{
				if((userBlockage.getErrorCount()!=null&&userBlockage.getErrorCount()>=actualNumberOfTrials)&&(this.getMinutesBefore(blockageDurationMinutes).before(userBlockage.getUpdatedAt())))
				{
					throw new BusinessException(ErrorCode.USER_BLOCKED);					
				}				
				else				
				{		
					Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
					userBlockage.setUser(userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));
					userBlockage.setErrorCount(0);
					this.updateUserBlockage(userBlockage);
					return result;						
				}								
		}
		else
		{
			Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
			return result;
		}
			
	}
	
	public UserBlockage addUserBlockage(User user)
	{
		
		return this.userBlockageService.addUserBlockage(user);
		
	}
	public UserBlockage updateUserBlockage(UserBlockage userBlockage)
	{
		
		return this.userBlockageService.updateUserBlockage(userBlockage);
		
	}
	public UserBlockage getUserBlockage(Long userId,boolean shouldBeFull) throws BusinessException
	{	
		
		try 
		{
			UserBlockage userBlockage= this.userBlockageService.findByUserId(userId);
			
			if(this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt()))
			{
				return userBlockage;
			}
			else
			{
				userBlockage.setBlock(false);
				userBlockage.setErrorCount(0);
				return userBlockage;
			}
			
		}
		catch(Exception exception)
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
			if(shouldBeFull)
			{
				return respondWithEmptyObject();
			}
			
			return null;
		}
		
	}
	
	UserBlockage respondWithEmptyObject()
	{
		UserBlockage userBlockage=new UserBlockage();
		userBlockage.setErrorCount(0);
		userBlockage.setBlock(false);
		return userBlockage;
	}
	
}
