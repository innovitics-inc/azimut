package innovitics.azimut.validations.validators.azimutclient;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class SaveClientBankAccountTemporarily extends BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessClientBankAccountDetails.class.equals(clazz);	
		}

	@Override
	public void validate(Object target, Errors errors) 
	{
		BusinessClientBankAccountDetails businessClientBankAccountDetails=(BusinessClientBankAccountDetails) target;
		
	if(!StringUtility.isStringPopulated(businessClientBankAccountDetails.getAccountNumber()))
			errors.rejectValue("accountNumber", "invalidValue");
	if(!StringUtility.isStringPopulated(businessClientBankAccountDetails.getIban()))
			errors.rejectValue("iban", "invalidValue");
	if(businessClientBankAccountDetails.getBranchId()==null)
			errors.rejectValue("branchId", "invalidValue");
	if(businessClientBankAccountDetails.getBankId()==null)
			errors.rejectValue("bankId", "invalidValue");
	if(businessClientBankAccountDetails.getCurrencyId()==null)
			errors.rejectValue("currencyId", "invalidValue");

	}

}
