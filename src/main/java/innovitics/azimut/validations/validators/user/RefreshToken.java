package innovitics.azimut.validations.validators.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.RegexPattern;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class RefreshToken extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthenticationRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		AuthenticationRequest authenticationRequest = (AuthenticationRequest) target;
		
        if(authenticationRequest!=null)
        {
        	if(!StringUtility.isStringPopulated(authenticationRequest.getRefreshToken()))
        	{
        		errors.rejectValue("refreshToken", "invalidValue");
        	}
        }
	}
	

}
