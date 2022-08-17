package innovitics.azimut.validations.validators.azimutclient;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.validations.validators.BaseValidator;

@Component
public class RemoveClientBankAccount extends BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) 
	{
		return BusinessClientBankAccountDetails.class.equals(clazz);	
	}

	@Override
	public void validate(Object target, Errors errors) {
	
	
	BusinessClientBankAccountDetails businessClientBankAccountDetails=(BusinessClientBankAccountDetails) target;
			
	if(businessClientBankAccountDetails.getId()==null)
	{
		errors.rejectValue("id", "invalidValue");

	}
	

		
	}

}
