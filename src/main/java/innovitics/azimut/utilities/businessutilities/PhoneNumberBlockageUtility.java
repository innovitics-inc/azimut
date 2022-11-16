package innovitics.azimut.utilities.businessutilities;

import org.springframework.stereotype.Component;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.UserMapper;
@Component
public class PhoneNumberBlockageUtility extends UserBlockageUtility{

	
	
	public Object checkUserBlockage(Integer numberOfTrials,String blockageDurationMinutes,String userPhone,Object object, String methodName,Object[] parameters,Class<?>[] paramterTypes,ErrorCode errorCode) throws BusinessException
	{
		
		this.logger.info("Checking User Blockage in PhoneNumberBlockageUtility::::");
		int actualNumberOfTrials= numberOfTrials!=null?numberOfTrials.intValue():0;
		UserBlockage userBlockage=this.getUserBlockage(userPhone,false);
		if(userBlockage!=null)
		{
				if((userBlockage.getErrorCount()!=null&&userBlockage.getErrorCount()>=actualNumberOfTrials)&&(this.getMinutesBefore(blockageDurationMinutes).before(userBlockage.getUpdatedAt())))
				{
					throw new BusinessException(ErrorCode.USER_BLOCKED);					
				}				
				else				
				{		
					Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
					userBlockage.setErrorCount(0);
					userBlockage.setUserPhone(userPhone);
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
	
	
	public UserBlockage addUserBlockage(String userPhone)
	{
		
		return this.userBlockageService.addUserBlockage(userPhone);
		
	}

	public UserBlockage getUserBlockage(String userPhone,boolean shouldBeFull)
	{	
		
		try 
		{
			UserBlockage userBlockage= this.userBlockageService.findByUserPhone(userPhone);
			
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

}
