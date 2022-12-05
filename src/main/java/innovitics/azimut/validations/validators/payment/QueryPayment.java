package innovitics.azimut.validations.validators.payment;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.validations.validators.BaseValidator;

@Component
public class QueryPayment extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessPayment.class.equals(clazz);	
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		BusinessPayment businessPayment=(BusinessPayment) target;
		
		if(businessPayment.getReferenceTransactionId()==null)
				errors.rejectValue("referenceTransactionId", "invalidValue");	
	}

}
