package innovitics.azimut.validations.validators.azimutclient;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.validations.RegexPattern;
import innovitics.azimut.validations.validators.BaseValidator;
@Component
public class GetBalanceAndTransactions extends BaseValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessAzimutClient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BusinessAzimutClient businessAzimutClient=(BusinessAzimutClient) target;
		
		if (!StringUtility.isStringPopulated(businessAzimutClient.getAzId()))
			{
			errors.rejectValue("azId", "invalidValue");
			}
		if (businessAzimutClient.getAzIdType()!=null&&!StringUtility.isStringPopulated(businessAzimutClient.getAzIdType().toString()))
			{
			errors.rejectValue("azIdType", "invalidValue");
			}
		if (!StringUtility.validateStringValueWithRegexPattern(businessAzimutClient.getSearchFromDate(),RegexPattern.YEAR.getPattern(),false))
			{
			errors.rejectValue("searchFromDate", "invalidValue");
			}
		if (!StringUtility.validateStringValueWithRegexPattern(businessAzimutClient.getSearchToDate(),RegexPattern.YEAR.getPattern(),false))
			{
			errors.rejectValue("searchToDate", "invalidValue");
			}
		
		if(StringUtility.isStringPopulated(businessAzimutClient.getSearchFromDate())
				&&StringUtility.isStringPopulated(businessAzimutClient.getSearchToDate())
				&&!DateUtility.isFromDateBeforeToDate(businessAzimutClient.getSearchFromDate(), businessAzimutClient.getSearchToDate()))
			{
				errors.rejectValue("searchDates", "invalidValue");
			}
		
		if(StringUtility.isStringPopulated(businessAzimutClient.getSorting()))
			{
				if(StringUtility.stringsDontMatch(businessAzimutClient.getSorting(), Sorting.ASC.getOrder())&&
						StringUtility.stringsDontMatch(businessAzimutClient.getSorting(), Sorting.DESC.getOrder()))
				{
					errors.rejectValue("sorting", "invalidValue");	
				}
			}
	}

}
