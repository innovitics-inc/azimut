package innovitics.azimut.validations.validators.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.security.PasswordValidation;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.RegexPattern;
import innovitics.azimut.validations.validators.BaseValidator;

@Component
public class EditBusinessUserValidator  extends BaseValidator{

	
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
		
        if(businessUser!=null)
        {
        if (businessUser.getId()!=null&&businessUser.getId() < 0)
			errors.rejectValue("id", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getEmailAddress(),RegexPattern.EMAIL.getPattern(), false)))
			errors.rejectValue("emailAddress", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getCountryPhoneCode()+businessUser.getPhoneNumber(),RegexPattern.PHONE_NUMBER.getPattern(), false)))
			errors.rejectValue("userPhone", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getPassword(),RegexPattern.PASSWORD.getPattern(), false)))
			errors.rejectValue("password", "invalidValue");
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getNewPassword(),RegexPattern.PASSWORD.getPattern(), false)))
		errors.rejectValue("newPassword", "invalidValue");
        }
	}

	

}
