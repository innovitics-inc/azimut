package innovitics.azimut.validations.validators.valify;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class ValifyIdImages extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) 
	
	{
		return BusinessValify.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) 
		{
			BusinessUser businessUser = (BusinessUser) target;			
			
		}
}
