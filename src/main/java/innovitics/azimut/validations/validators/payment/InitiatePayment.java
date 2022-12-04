package innovitics.azimut.validations.validators.payment;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class InitiatePayment extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessPayment.class.equals(clazz);	
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		BusinessPayment businessPayment=(BusinessPayment) target;
		
		if(businessPayment.getAmount()==null)
				errors.rejectValue("amount", "invalidValue");
		if(businessPayment.getCurrencyId()==null||businessPayment.getCurrencyId()!=null&&!NumberUtility.areLongValuesMatching(businessPayment.getCurrencyId(), CurrencyType.EGYPTIAN_POUND.getTypeId()))
				errors.rejectValue("currency", "invalidValue");
		if(businessPayment.getAction()==null)
			errors.rejectValue("action", "invalidValue");
		if(businessPayment.getBankId()==null)
			errors.rejectValue("bankId", "invalidValue");
		if(businessPayment.getAccountId()==null)
			errors.rejectValue("accountId", "invalidValue");
	
		if(BooleanUtility.isFalse(businessPayment.getIsMobile()))
		{
			if(!StringUtility.isStringPopulated(businessPayment.getReturnUrl()))
				errors.rejectValue("returnUrl", "invalidValue");
		}
		
	}

}
