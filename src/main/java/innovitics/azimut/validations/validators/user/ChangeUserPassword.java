package innovitics.azimut.validations.validators.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.RegexPattern;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class ChangeUserPassword extends BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) 
	
	{
		return BusinessUser.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
        //ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        BusinessUser businessUser = (BusinessUser) target;
		
        if(businessUser!=null)
        {
		if (!StringUtility.isStringPopulated(businessUser.getPassword()))
			errors.rejectValue("password", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getNewPassword(),RegexPattern.PASSWORD.getPattern(), true)))
			errors.rejectValue("newPassword", "invalidValue");
        }
	}

}
