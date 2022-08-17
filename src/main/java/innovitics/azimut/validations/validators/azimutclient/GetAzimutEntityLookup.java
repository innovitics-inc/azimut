package innovitics.azimut.validations.validators.azimutclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessAzimutDataLookup;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
import innovitics.azimut.utilities.datautilities.AzimutDataLookupUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.validations.validators.BaseValidator;

@Component
public class GetAzimutEntityLookup extends BaseValidator{

	@Autowired AzimutDataLookupUtility azimutDataLookupUtility;
	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessAzimutDataLookup.class.equals(clazz);	
	}

	@Override
	public void validate(Object target, Errors errors) {
		BusinessAzimutDataLookup businessAzimutDataLookup=(BusinessAzimutDataLookup) target;
		if((businessAzimutDataLookup.getEntityTypeId()==null)||(businessAzimutDataLookup.getEntityTypeId()!=null&&!this.azimutDataLookupUtility.getAzimutEntityTypeIds().contains(businessAzimutDataLookup.getEntityTypeId())))
		{
			errors.rejectValue("entityTypeId", "invalidValue");
		}
		if(businessAzimutDataLookup.getEntityTypeId()!=null&&NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.BRANCH.getTypeId())&&businessAzimutDataLookup.getBankId()==null)
		{
			errors.rejectValue("bankId", "invalidValue");
		}	
		if(businessAzimutDataLookup.getEntityTypeId()!=null&&NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.CITY.getTypeId())&&businessAzimutDataLookup.getCountryId()==null)
		{
			errors.rejectValue("countryId", "invalidValue");
		}
	}

}
