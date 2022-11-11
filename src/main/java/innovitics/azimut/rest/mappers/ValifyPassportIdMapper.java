package innovitics.azimut.rest.mappers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.valify.ValifyOCRPassportApiConsumer;
import innovitics.azimut.rest.entities.valify.ValifyOCRInput;
import innovitics.azimut.rest.entities.valify.ValifyOCROutput;
import innovitics.azimut.rest.models.valify.ValifyOCRPassportRequest;
import innovitics.azimut.rest.models.valify.ValifyOCRPassportResponse;
import innovitics.azimut.utilities.crosslayerenums.UserIdType;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;

@Component
public class ValifyPassportIdMapper extends RestMapper<ValifyOCRInput, ValifyOCROutput, ValifyOCRPassportRequest, ValifyOCRPassportResponse, BusinessValify>{

	@Autowired ValifyOCRPassportApiConsumer valifyOCRPassportApiConsumer;

	@Override
	public
	BusinessValify consumeRestService(BusinessValify businessValify, String params)
			throws IntegrationException, HttpClientErrorException, Exception {
		
			BusinessValify responseBusinessValify =new BusinessValify();
			responseBusinessValify=this.createBusinessEntityFromOutput(this.valifyOCRPassportApiConsumer.invoke(this.createInput(businessValify), ValifyOCRPassportResponse.class, params));
		return responseBusinessValify;
	}

	@Override
	List<BusinessValify> consumeListRestService(BusinessValify baseBusinessEntity, String params)
			throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	ValifyOCRInput createInput(BusinessValify businessValify) {
		ValifyOCRInput input=new ValifyOCRInput();
		if(this.isDocumentPassport(businessValify))
		{
			input.setImage(businessValify.getPassportImage());
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
		if(valifyOCROutput!=null)
		{	
			if(StringUtility.isStringPopulated(valifyOCROutput.getName()))
			{
				String value=valifyOCROutput.getName();
				businessValify.setName(value.substring(0, value.indexOf(" ")));
			}
			
			businessValify.setLastName(valifyOCROutput.getSurname());
			businessValify.setPassportNumber(valifyOCROutput.getPassportNumber());
			
			
			if(StringUtility.isStringPopulated(valifyOCROutput.getDateOfBirth()))
				businessValify.setDateOfBirth(DateUtility.changeStringDateFormat(valifyOCROutput.getDateOfBirth().replace("'",""), new SimpleDateFormat("dd/mm/yy"), new SimpleDateFormat("dd-mm-yyyy")));
				
				
			if(StringUtility.isStringPopulated(valifyOCROutput.getExpirationDate()))
				businessValify.setExpirationDate(DateUtility.changeStringDateFormat(valifyOCROutput.getExpirationDate().replace("'", ""), new SimpleDateFormat("dd/mm/yy"), new SimpleDateFormat("dd-mm-yyyy")));
				
				
			
			
			businessValify.setSex(valifyOCROutput.getSex());
			businessValify.setNationality(valifyOCROutput.getNationality());
			businessValify.setValidity(valifyOCROutput.getValidity());
			businessValify.setUserId(valifyOCROutput.getPassportNumber());
			businessValify.setAzIdType(UserIdType.PASSPORT.getTypeId());
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
		if(this.isDocumentPassport(businessValify))
		{
			this.consumer=valifyOCRPassportApiConsumer;
		}	
		
	}
	
}
