package innovitics.azimut.validations.validators.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.RegexPattern;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class ChangePhoneNumber extends BaseValidator{

	
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
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getUserPhone(),RegexPattern.PHONE_NUMBER.getPattern(), true)))
			errors.rejectValue("userPhone", "invalidValue");
        }
	}
}
