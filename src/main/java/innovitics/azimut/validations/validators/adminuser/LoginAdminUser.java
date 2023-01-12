package innovitics.azimut.validations.validators.adminuser;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.validators.BaseValidator;

@Component
public class LoginAdminUser extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) 
	
	{
		return AuthenticationRequest.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) 	
	{
		AuthenticationRequest authenticationRequest = (AuthenticationRequest) target;
		
        if(authenticationRequest!=null)
        {
        if (!StringUtility.isStringPopulated(authenticationRequest.getEmail()))
			errors.rejectValue("email", "invalidValue");
        if (!StringUtility.isStringPopulated(authenticationRequest.getPassword()))
			errors.rejectValue("password", "invalidValue");
        }
	}

	
}
