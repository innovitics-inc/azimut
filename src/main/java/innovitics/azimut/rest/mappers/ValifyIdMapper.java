package innovitics.azimut.rest.mappers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.valify.ValifyOCRNationalIdApiConsumer;
import innovitics.azimut.rest.apis.valify.ValifyOCRPassportApiConsumer;
import innovitics.azimut.rest.entities.valify.ValifyOCRInput;
import innovitics.azimut.rest.entities.valify.ValifyOCROutput;
import innovitics.azimut.rest.models.valify.ValifyOCRIdRequest;
import innovitics.azimut.rest.models.valify.ValifyOCRIdResponse;
import innovitics.azimut.rest.models.valify.ValifyOCRNationalIdRequest;
import innovitics.azimut.rest.models.valify.ValifyOCRNationalIdResponse;
import innovitics.azimut.rest.models.valify.ValifyOCRPassportResponse;
import innovitics.azimut.utilities.crosslayerenums.UserIdType;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;

@Component
public class ValifyIdMapper extends RestMapper<ValifyOCRInput, ValifyOCROutput, ValifyOCRNationalIdRequest, ValifyOCRNationalIdResponse, BusinessValify>  {


	@Autowired ValifyOCRNationalIdApiConsumer valifyOCRNationalIdApiConsumer;
	
	@Override
	public BusinessValify consumeRestService(BusinessValify businessValify, String params) throws HttpClientErrorException, Exception {
		BusinessValify responseBusinessValify =new BusinessValify();
		
		if(this.isDocumentNationalId(businessValify))
		{
			responseBusinessValify=this.createBusinessEntityFromOutput(this.valifyOCRNationalIdApiConsumer.invoke(this.createInput(businessValify), ValifyOCRNationalIdResponse.class, params));
		}
		
		
		
		return responseBusinessValify;
	}

	@Override
	List<BusinessValify> consumeListRestService(BusinessValify baseBusinessEntity, String params)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	ValifyOCRInput createInput(BusinessValify businessValify) {
		ValifyOCRInput input=new ValifyOCRInput();
		if(this.isDocumentNationalId(businessValify))
		{
			input.setFrontImage(businessValify.getFrontImage());
			input.setBackImage(businessValify.getBackImage());
		}
		input.setLang(businessValify.getLanguage());
		input.setToken(businessValify.getToken());
		input.setDoucmentType(businessValify.getDocumentType());
		input.setLang(businessValify.getLanguage());
		return input;
	}

	@Override
	BusinessValify createBusinessEntityFromOutput(ValifyOCROutput valifyOCROutput) {
		BusinessValify businessValify=new BusinessValify();
		if(valifyOCROutput!=null&&valifyOCROutput.isEgyptian())
		{
			businessValify.setFirstName(valifyOCROutput.getFirstName());
			businessValify.setFullName(valifyOCROutput.getFullName());
			businessValify.setStreet(valifyOCROutput.getStreet());
			businessValify.setArea(valifyOCROutput.getArea());
			businessValify.setFrontNid(valifyOCROutput.getFrontNid());
			businessValify.setSerialNumber(valifyOCROutput.getSerialNumber());
			businessValify.setBackNid(valifyOCROutput.getBackNid());
			businessValify.setReleaseDate(valifyOCROutput.getReleaseDate());
			businessValify.setGender(valifyOCROutput.getGender());
			businessValify.setMaritalStatus(valifyOCROutput.getMaritalStatus());
			businessValify.setProfession(valifyOCROutput.getProfession());
			businessValify.setReligion(valifyOCROutput.getReligion());
			businessValify.setHusbandName(valifyOCROutput.getHusbandName());
			
			if(StringUtility.isStringPopulated(valifyOCROutput.getDateOfBirth()))
			businessValify.setDateOfBirth(DateUtility.changeStringDateFormat(valifyOCROutput.getDateOfBirth(), new SimpleDateFormat("yyyy/mm/dd"), new SimpleDateFormat("dd-mm-yyyy")));
			
			
			if(StringUtility.isStringPopulated(valifyOCROutput.getExpiryDate()))
			businessValify.setExpirationDate(DateUtility.changeStringDateFormat(valifyOCROutput.getExpiryDate()+"/01", new SimpleDateFormat("yyyy/mm/dd"), new SimpleDateFormat("dd-mm-yyyy")));
			
			
			
			businessValify.setCountry(StringUtility.EGYPT);
			
			if(StringUtility.isStringPopulated(valifyOCROutput.getArea()))
			{
				businessValify.setCity((valifyOCROutput.getArea().split("-")[1]).trim());
			}
			if(StringUtility.isStringPopulated(valifyOCROutput.getFullName()))
			{
				char space=' ';
				businessValify.setLastName(StringUtility.generateSubStringStartingFromCertainIndex(valifyOCROutput.getFullName(),valifyOCROutput.getFullName().lastIndexOf(space)).trim());
			}
			businessValify.setUserId(valifyOCROutput.getFrontNid());
			businessValify.setAzIdType(UserIdType.NATIONAL_ID.getTypeId());
	
		}
		
		businessValify.setValifyTransactionId(valifyOCROutput.getTransactionId());
		
		
		
	return businessValify;	
	}

	@Override
	protected List<BusinessValify> createListBusinessEntityFromOutput(ValifyOCROutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	boolean isDocumentNationalId(BusinessValify businessValify)
	{
		return	StringUtility.stringsMatch(businessValify.getDocumentType(), StringUtility.NATIONAL_ID_DOCUMENT_TYPE);
	}
	boolean isDocumentPassport(BusinessValify businessValify)
	{
		return StringUtility.stringsMatch(businessValify.getDocumentType(), StringUtility.PASSPORT_DOCUMENT_TYPE);
	}

	@Override
	protected void setConsumer(BusinessValify businessValify) {
		if(this.isDocumentNationalId(businessValify))
		{
			this.consumer=valifyOCRNationalIdApiConsumer;
		}			
	}
	
}
