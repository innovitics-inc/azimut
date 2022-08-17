package innovitics.azimut.validations.validators.azimutclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class SaveClientBankAccountsTemporarily extends BaseValidator{
@Autowired ArrayUtility arrayUtility;
	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessAzimutClient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BusinessAzimutClient businessAzimutClient=(BusinessAzimutClient) target;
		
		if(!arrayUtility.isArrayPopulated(businessAzimutClient.getClientBankAccounts()))
		{
			errors.rejectValue("clientBankAccounts", "invalidValue");
		}
		
		
		
	}

}
