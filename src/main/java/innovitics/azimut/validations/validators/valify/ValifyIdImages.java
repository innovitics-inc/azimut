package innovitics.azimut.validations.validators.valify;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

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
			BusinessValify businessValify = (BusinessValify) target;			
			if(businessValify!=null)
			{
				if(StringUtility.stringsMatch(businessValify.getDocumentType(), StringUtility.NATIONAL_ID_DOCUMENT_TYPE))
				  {
					if (!StringUtility.isStringPopulated(businessValify.getFrontImage()))
						errors.rejectValue("frontImage", "invalidValue");
					if (!StringUtility.isStringPopulated(businessValify.getBackImage()))
						errors.rejectValue("backImage", "invalidValue");
				   }
				else if(StringUtility.stringsMatch(businessValify.getDocumentType(), StringUtility.PASSPORT_DOCUMENT_TYPE))
				{
					if (!StringUtility.isStringPopulated(businessValify.getPassportImage()))
						errors.rejectValue("passportImage", "invalidValue");
				}
			}
			
			
	    }
}
