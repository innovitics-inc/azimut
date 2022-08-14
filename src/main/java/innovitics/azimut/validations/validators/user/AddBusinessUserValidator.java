package innovitics.azimut.validations.validators.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import innovitics.azimut.businessmodels.user.AzimutAccount;
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

		BusinessUser businessUser = (BusinessUser) target;
        
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getEmailAddress(),RegexPattern.EMAIL.getPattern(), true)))
			errors.rejectValue("emailAddress", "invalidValue");
		
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getUserPhone(),RegexPattern.PHONE_NUMBER.getPattern(), true)))
			errors.rejectValue("phoneNumber", "invalidValue");
		
		if (!(StringUtility.validateStringValueWithRegexPattern(businessUser.getPassword(),RegexPattern.PASSWORD.getPattern(), true)))
			errors.rejectValue("password", "invalidValue");
		
		if (!(StringUtility.isStringPopulated(businessUser.getNickName())))
			errors.rejectValue("nickName", "invalidValue");
		
		if(businessUser!=null&&businessUser.getAzimutAccount()!=null)
		{
			AzimutAccount businessAzimutAccount=businessUser.getAzimutAccount();
			
			if(businessAzimutAccount.getCountryId()==null)
				errors.rejectValue("azimutAccount", "invalidValue");
			if(businessAzimutAccount.getCityId()==null)
				errors.rejectValue("azimutAccount", "invalidValue");
				
		}
		
		

	}
}