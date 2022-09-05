package innovitics.azimut.validations.validators.azimutclient;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class SaveUserLocation extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UserLocation.class.equals(clazz);	
	}

	@Override
	public void validate(Object target, Errors errors) {

		
		UserLocation userLocation=(UserLocation) target;
		
		if(!StringUtility.isStringPopulated(userLocation.getFullName()))
				errors.rejectValue("fullName", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getStreet()))
				errors.rejectValue("street", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getBuildingNo()))
				errors.rejectValue("buildingNo", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getFloorNo()))
			errors.rejectValue("floorNo", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getApartment()))
			errors.rejectValue("apartment", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getCountryCode()))
			errors.rejectValue("countryCode", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getCountryPhoneCode()))
			errors.rejectValue("countryPhoneCode", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getPhoneNumber()))
			errors.rejectValue("phoneNumber", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getLongt()))
			errors.rejectValue("longt", "invalidValue");
		if(!StringUtility.isStringPopulated(userLocation.getLat()))
			errors.rejectValue("lat", "invalidValue");
				
	}

}
