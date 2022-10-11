package innovitics.azimut.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessservices.AbstractBusinessService;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;

@Component
public class ParentUtility {
	@Autowired protected ExceptionHandler exceptionHandler;
@Autowired protected ConfigProperties configProperties;
protected static final Logger logger = LoggerFactory.getLogger(ParentUtility.class);
	
protected Timestamp getMinutesBefore(String value)
	{
		Timestamp current=new Timestamp(System.currentTimeMillis());		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(current.getTime());	    
		int valueExpiryInMinutes=Integer.valueOf(value);
		cal.add(Calendar.MINUTE, -valueExpiryInMinutes);
		Timestamp currentMinusMinutesInValue = new Timestamp(cal.getTime().getTime());
		return currentMinusMinutesInValue;
	}


protected Object getValueUsingReflection(Object object,String methodName,Object[] parameters,Class<?>[] paramterTypes)
{				
	try 
	{
		Method method = object.getClass().getDeclaredMethod(methodName,paramterTypes);
		this.logger.info("Method Name::"+methodName);
		Object result = method.invoke(object, parameters); 
		return result;
	}
	catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) 
	{
		this.logger.info("Could not return the method invocation");
		exception.printStackTrace();
		return null;
	}  
}

}
