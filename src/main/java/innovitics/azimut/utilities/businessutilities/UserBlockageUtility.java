package innovitics.azimut.utilities.businessutilities;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
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
		int oldErrorCount=0;
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
					else
					{
						userBlockage.setErrorCount(oldErrorCount);
						userBlockage.setUpdatedAt(new Date());
					}
				}
				else
				{
						try 
						{
							Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
							return result;
						}					
						catch(Exception exception)
						{							
							if((exception instanceof IntegrationException)&&
									(NumberUtility.areIntegerValuesMatching(((IntegrationException)exception).getErrorCode(), errorCode.getCode())))
							{
								oldErrorCount=userBlockage.getErrorCount()!=null?userBlockage.getErrorCount():0;
								userBlockage.setErrorCount(oldErrorCount+1);
								userBlockage.setUpdatedAt(new Date());
							}							
						}
				  }				
					this.userBlockageService.updateUserBlockage(userBlockage);
		}
		else
		{
			try 
			{
				Object result=this.getValueUsingReflection(object,methodName,parameters,paramterTypes);
				return result;
			}					
			catch(Exception exception)
			{							
				if((exception instanceof IntegrationException)&&
						(NumberUtility.areIntegerValuesMatching(((IntegrationException)exception).getErrorCode(), errorCode.getCode())))
				{
					this.userBlockageService.addUserBlockage(userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));
				}							
			}			
		}
		return null;
	}
	
}
