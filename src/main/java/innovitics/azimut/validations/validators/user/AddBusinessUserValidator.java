package innovitics.azimut.validations.validators.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.RegexPattern;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class AddBusinessUserValidator extends BaseValidator{

	
	@Override
	public boolean supports(Class<?> clazz) 
	
	{
		return BusinessUser.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) 
	
	{
        //ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        BusinessUser businessUser = (BusinessUser) target;
		
        
        if (businessUser.getId() < 0)
			errors.rejectValue("id", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getEmailAddress(),RegexPattern.EMAIL.getPattern(), true)))
			errors.rejectValue("emailAddress", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getPhoneNumber(),RegexPattern.PHONE_NUMBER.getPattern(), true)))
			errors.rejectValue("phoneNumber", "invalidValue");

	}
}