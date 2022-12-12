package innovitics.azimut.utilities.mapping;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessAzimutInformation;
import innovitics.azimut.businessmodels.BusinessAzimutInformationType;
import innovitics.azimut.models.azimutdetails.AzimutInformation;
import innovitics.azimut.utilities.datautilities.StringUtility;

@Component
public class AzimutInformationMapper extends Mapper<AzimutInformation,BusinessAzimutInformation>{

	@Override
	public AzimutInformation convertBusinessUnitToBasicUnit(BusinessAzimutInformation businessAzimutInformation, boolean save) {
		
		return null;
	}

	@Override
	public BusinessAzimutInformation convertBasicUnitToBusinessUnit(AzimutInformation azimutInformation) {
		BusinessAzimutInformation businessAzimutInformation=new BusinessAzimutInformation();
		businessAzimutInformation.setId(azimutInformation.getId());
		businessAzimutInformation.setQuestion(azimutInformation.getQuestion());
		businessAzimutInformation.setAnswer(azimutInformation.getAnswer());
		businessAzimutInformation.setType(azimutInformation.getInformationType());
		return businessAzimutInformation;
	}

	@Override
	public BusinessAzimutInformation convertBasicUnitToBusinessUnit(AzimutInformation azimutInformation, String language) {
		BusinessAzimutInformation businessAzimutInformation=new BusinessAzimutInformation();
		businessAzimutInformation.setId(azimutInformation.getId());
		
		if(StringUtility.isStringPopulated(language))
		{
			if(StringUtility.stringsMatch(language, StringUtility.ENGLISH))
			{
				businessAzimutInformation.setQuestion(azimutInformation.getQuestion());
				businessAzimutInformation.setAnswer(azimutInformation.getAnswer());
			}
			else if(StringUtility.stringsMatch(language, StringUtility.ARABIC))
			{
				businessAzimutInformation.setQuestion(azimutInformation.getQuestionAr());
				businessAzimutInformation.setAnswer(azimutInformation.getAnswerAr());
			}
		}
		else
		{
			businessAzimutInformation.setQuestion(azimutInformation.getQuestion());
			businessAzimutInformation.setAnswer(azimutInformation.getAnswer());
		}
		businessAzimutInformation.setType(azimutInformation.getInformationType());
		return businessAzimutInformation;
	}

	public BusinessAzimutInformationType convertBasicUnitToBusinessAzimutInformationTypeUnit(AzimutInformation azimutInformation, String language) {
		BusinessAzimutInformationType businessAzimutInformationType=new BusinessAzimutInformationType();
		businessAzimutInformationType.setId(azimutInformation.getId());
		
		if(StringUtility.isStringPopulated(language))
		{
			if(StringUtility.stringsMatch(language, StringUtility.ENGLISH))
			{
				businessAzimutInformationType.setQuestion(azimutInformation.getQuestion());
				businessAzimutInformationType.setAnswer(azimutInformation.getAnswer());
			}
			else if(StringUtility.stringsMatch(language, StringUtility.ARABIC))
			{
				businessAzimutInformationType.setQuestion(azimutInformation.getQuestionAr());
				businessAzimutInformationType.setAnswer(azimutInformation.getAnswerAr());
			}
		}
		else
		{
			businessAzimutInformationType.setQuestion(azimutInformation.getQuestion());
			businessAzimutInformationType.setAnswer(azimutInformation.getAnswer());
		}
		businessAzimutInformationType.setType(azimutInformation.getInformationType());
		return businessAzimutInformationType;
	}
}
