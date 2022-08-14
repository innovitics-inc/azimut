package innovitics.azimut.utilities.datautilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import innovitics.azimut.utilities.ParentUtility;

@Component
public final class NumberUtility {
	protected static final Logger logger = LoggerFactory.getLogger(NumberUtility.class);

	public static boolean areLongValuesMatching(Long value1,Long value2)
	{
		boolean	result=false;
		logger.info("Are the values "+ value1+" and "+ value1+"matching?");
		
		if(value1!=null&&value2!=null&&value1.longValue()==value2.longValue())
			result= true;
		else
			result= false;
		
		logger.info("result="+result);
		return result;
	}
	public static boolean areIntegerValuesMatching(Integer value1,Integer value2)
	{
		boolean	result=false;
		logger.info("Are the values "+ value1+" and "+ value1+"matching?");
		
		if(value1!=null&&value2!=null&&value1.longValue()==value2.longValue())
			result= true;
		else
			result= false;
		
		logger.info("result="+result);
		return result;
	}
	public static boolean isNewValueLessThanOrEqualOldValue(Integer oldValue,Integer newValue)
	{
		boolean	result=false;

		
		if(oldValue!=null&&newValue!=null&&newValue.longValue()<=oldValue.longValue())
		result= true;
		
		
		logger.info("result="+result);
		return result;
	}
	
}
