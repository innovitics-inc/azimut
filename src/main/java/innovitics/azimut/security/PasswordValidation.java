package innovitics.azimut.security;

import org.springframework.stereotype.Component;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.validations.RegexPattern;

@Component
public class PasswordValidation {

	
	public boolean validateTwoPasswords(String password1,String password2) throws BusinessException
	{
		boolean	result=true;
		if (StringUtility.stringsDontMatch(password1, password2))
			{
				result=false;
				throw new BusinessException(ErrorCode.PASSWORDS_NOT_MATHCING);
			}
		if(!(StringUtility.matchesPattern(password1, RegexPattern.PASSWORD.getPattern()))&&StringUtility.matchesPattern(password2, RegexPattern.PASSWORD.getPattern()))
			{
				result=false;
				throw new BusinessException(ErrorCode.WEAK_PASSWORD);
			}
		
		return result;
	}
	
}
