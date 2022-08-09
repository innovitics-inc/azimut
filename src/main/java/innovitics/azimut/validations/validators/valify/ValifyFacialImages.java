package innovitics.azimut.validations.validators.valify;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class ValifyFacialImages extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) 
	
	{
		return BusinessValify.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) 
		{
			BusinessValify businessValify = (BusinessValify) target;
			if (businessValify != null) {
				if (businessValify.getUserStep() != null) 
				{
					if (this.numberUtility.areIntegerValuesMatching(businessValify.getUserStep(),UserStep.STRAIGHT_AND_SMILE.getStepId())) 
					{
						if (!StringUtility.isStringPopulated(businessValify.getStraightFace()))
							errors.rejectValue("straightFace", "invalidValue");
						if (!StringUtility.isStringPopulated(businessValify.getSmilingFace()))
							errors.rejectValue("smilingFace", "invalidValue");
					} else if (this.numberUtility.areIntegerValuesMatching(businessValify.getUserStep(),UserStep.LEFT_AND_RIGHT.getStepId())) 
					{
						if (!StringUtility.isStringPopulated(businessValify.getLeftSide()))
							errors.rejectValue("leftSide", "invalidValue");
						if (!StringUtility.isStringPopulated(businessValify.getRightSide()))
							errors.rejectValue("rightSide", "invalidValue");
					}

				}
			} 
			else 
			{
				errors.rejectValue("userStep", "invalidValue");
			}
		}
}