package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.LookUpInput;
import innovitics.azimut.rest.entities.teacomputers.LookUpOutput;
import innovitics.azimut.rest.entities.teacomputers.LookUpOutputs;
import innovitics.azimut.rest.models.teacomputers.LookupRequest;
import innovitics.azimut.rest.models.teacomputers.LookupResponse;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class LookupApiConsumer  extends RestTeaComputerApiConsumer<LookupRequest, LookupResponse[],LookUpInput , LookUpOutput>{

	@Override
	public HttpEntity<String> generateRequestFromInput(LookUpInput input) {
		LookupRequest request=new LookupRequest();	
		this.populateCredentials(request);
		request.setTypeId(input.getTypeId().longValue());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public LookUpOutput generateOutPutFromResponse(ResponseEntity<LookupResponse[]> responseEntity) {
		// TODO Auto-generated method stub
		LookUpOutput output=new LookUpOutput();
		List<LookUpOutputs> lookUpOutputs=new ArrayList<LookUpOutputs>();
		
		if(true//this.validateResponseStatus(responseEntity)&&responseEntity.hasBody()
		   )
		{		
				for(int i=0;i<responseEntity.getBody().length;i++)
				{					
					LookUpOutputs outputs=new LookUpOutputs();
					outputs.setEnglishCountryName(responseEntity.getBody()[i].getEnglishCountryName());
					outputs.setArabicCountryName(responseEntity.getBody()[i].getArabicCountryName());
					outputs.setCountryId(responseEntity.getBody()[i].getCountryId());
					outputs.setSystemCountryCode(responseEntity.getBody()[i].getSytemCountryCode());
	
					outputs.setEnglishCityName(responseEntity.getBody()[i].getEnglishCityName());
					outputs.setArabicCityName(responseEntity.getBody()[i].getArabicCityName());
					outputs.setCityId(responseEntity.getBody()[i].getCityId());
					outputs.setSystemCityCode(responseEntity.getBody()[i].getSytemCityCode());
					
					outputs.setEnglishNationalityName(responseEntity.getBody()[i].getEnglishNationalityName());
					outputs.setArabicNationalityName(responseEntity.getBody()[i].getArabicNationalityName());
					outputs.setSystemNationalityCode(responseEntity.getBody()[i].getSytemNationalityCode());
					outputs.setNationalityId(responseEntity.getBody()[i].getNationalityId());
					
					
					outputs.setEnglishCurrencyName(responseEntity.getBody()[i].getEnglishCurrencyName());
					outputs.setArabicCurrencyName(responseEntity.getBody()[i].getArabicCurrencyName());
					outputs.setCurrencyId(responseEntity.getBody()[i].getCurrencyId());
					outputs.setSystemCurrencyCode(responseEntity.getBody()[i].getSystemCurrencyCode());
					
					outputs.setEnglishBankName(responseEntity.getBody()[i].getEnglishBankName());
					outputs.setArabicBankName(responseEntity.getBody()[i].getArabicBankName());
					outputs.setBankId(responseEntity.getBody()[i].getBankId());
					outputs.setSystemBankCode(responseEntity.getBody()[i].getSystemBankCode());
					
					outputs.setEnglishBranchName(responseEntity.getBody()[i].getEnglishBranchName());
					outputs.setArabicBranchName(responseEntity.getBody()[i].getArabicBranchName());
					outputs.setSystemBranchCode(responseEntity.getBody()[i].getSystemBranchCode());
					outputs.setBranchId(responseEntity.getBody()[i].getBranchId());
					

					lookUpOutputs.add(outputs);
				}
				
				output.setOutputs(lookUpOutputs);
		}
	
		return output;
	}
	

	@Override
	public void validateResponse(ResponseEntity<LookupResponse[]> responseEntity) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Check Account API:::");
		if(exception instanceof IntegrationException)
		{
			this.logger.info("The exception was found to be an integration exception:::");
			IntegrationException integrationException=(IntegrationException)exception;
			
			this.logger.info("Check Account API Integration Exception error Code:::"+integrationException.getErrorCode());
			this.logger.info("Check Account API Integration Exception error message:::"+integrationException.getErrorMessage());
			
			return integrationException;
		}
		else
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);

	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.GET ;
	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+"/lookups/"+params;
	}

	@Override
	protected String generateSignature(LookupRequest request) {
		if(request!=null)
		{
			if(request.getTypeId().longValue()==AzimutEntityType.COUNTRY.getTypeId())
			{
				return	this.teaComputersSignatureGenerator.generateSignature("Country");
			}
			else if(request.getTypeId().longValue()==AzimutEntityType.CITY.getTypeId())
			{
				return this.teaComputersSignatureGenerator.generateSignature("City");
			}
			else if(request.getTypeId().longValue()==AzimutEntityType.NATIONALITY.getTypeId())
			{
				return this.teaComputersSignatureGenerator.generateSignature("Nationality");
			}
			else if(request.getTypeId().longValue()==AzimutEntityType.BRANCH.getTypeId())
			{
				return	this.teaComputersSignatureGenerator.generateSignature("Branches");
			}
			else if(request.getTypeId().longValue()==AzimutEntityType.BANK.getTypeId())
			{
				return this.teaComputersSignatureGenerator.generateSignature("Banks");
			}
			else if(request.getTypeId().longValue()==AzimutEntityType.CURRENCY.getTypeId())
			{
				return this.teaComputersSignatureGenerator.generateSignature("Currencies");
			}
		}
		return null;
	}

	@Override
	protected void generateResponseSignature(LookupResponse[] teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateResponseListSignature(LookupResponse[] teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<LookupResponse[]> getResponseClassType() {
		// TODO Auto-generated method stub
		return LookupResponse[].class;
	}

	

}
