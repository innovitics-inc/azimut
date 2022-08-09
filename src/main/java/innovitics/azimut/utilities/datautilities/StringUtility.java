package innovitics.azimut.utilities.datautilities;

import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public final class StringUtility extends ParentUtility{
	
	public static final String SUCCESS="success";
	public static final int SUCCESS_CODE=0;
	public static final String AUTHORIZATION_HEADER="Authorization";
	public static final String USER_CLASS_NAME="user";
	public static final String AZIMUT_Client_CLASS_NAME="azimut client";
	public static final String AUTHENTICATION_CLASS_NAME="authentication request";
	public static final String SHA_256_ALGORITHM="SHA-256";
	public static final String UTF_8_ENCODING="UTF-8";
	public final static String NATIONAL_ID_DOCUMENT_TYPE="egy_nid";
	public final static String PASSPORT_DOCUMENT_TYPE="passport";
	public final static String EGYPT="Egypt";

	public static String getClassName(Object object)
	{
		return object.getClass().getName();
	}
	
	public static Boolean isStringPopulated(String string)
	{
		logger.info(string);
		return (string!=null&&!string.isEmpty()&&!string.equals("")&&!string.equals("NULL"));
	}
	
	public static Boolean isStringArrayPopulated(String[] strings)
	{
		return (strings!=null&&strings.length!=0);
	}
	public static Boolean isStringListPopulated(List<String> strings)
	{
		return (strings!=null&&strings.size()!=0);
	}
	
	public static boolean matchesPattern(String input,String regexPattern) 
	{
		
		boolean match = true;
		final Pattern pattern= Pattern.compile(regexPattern);
		if (isStringPopulated(input)&&!pattern.matcher(input).matches()) {
			match = false;
		}
		return match;
	}
	
	public static String convertToJson(ErrorCode errorCode) throws JsonProcessingException {
	       
        ObjectMapper mapper = new ObjectMapper();
        /*mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);*/

        BaseGenericResponse<String> baseGenericResponse = new BaseGenericResponse<String>();
		baseGenericResponse.setMessage(errorCode.getMessage());
		baseGenericResponse.setStatus(errorCode.getCode());

        return mapper.writeValueAsString(baseGenericResponse);
    }
	
	public static String addLeadingAndTrailingSpaces(String string)
	{
		return " "+string+" ";
	}
	
	public static String surroundWithBrackets(String[] strings)
	{
		return " in ("+String.join(",", strings)+")";
	}
	public static String surroundForLike(String string)
	{
		return " like '%"+string+"%'";
	}
	
	public static boolean validateStringValueWithRegexPattern(String input, String regexPattern, boolean mandatory) 
	{
		logger.info("Input::"+input);
		logger.info("Pattern::"+regexPattern);
		logger.info("Mandatory?::"+mandatory);
		
		boolean result = false;
		if (mandatory) 
		{
			result = isStringPopulated(input) && matchesPattern(input, regexPattern);	
		} 
		
		else if (!mandatory) 
		{
			if (isStringPopulated(input)) 
			{
				result = matchesPattern(input, regexPattern);
			} 
			else 
			{
				result = true;
			}
		}
		logger.info("Result::"+result);
		return result;
	}
	
	public static String getSubstring(String input,int beginningIndex)
	{
				return input.substring(beginningIndex);
	}
	
	public static boolean stringsMatch(String input1,String input2)
	{
		boolean result=isStringPopulated(input1)&&isStringPopulated(input2)&&input1.equals(input2);
		logger.info("Comparison result:::"+result);
		return result;
	}
	
	public static boolean stringsDontMatch(String input1,String input2)
	{
		return isStringPopulated(input1)&&isStringPopulated(input2)&&!input1.equals(input2);
	}
	
	public static String generateSubStringStartingFromCertainIndex(String input,char value)
	{
		  
        int index= input.indexOf(value);
		return input.substring(index);
	}
	public static String generateSubStringStartingFromCertainIndex(String input,int value)
	{
		return input.substring(value);
	}

}
