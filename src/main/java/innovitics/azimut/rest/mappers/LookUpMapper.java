package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.result.Outputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.models.user.AzimutDataLookup;
import innovitics.azimut.rest.apis.teacomputers.LookupApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.LookUpInput;
import innovitics.azimut.rest.entities.teacomputers.LookUpOutput;
import innovitics.azimut.rest.entities.teacomputers.LookUpOutputs;
import innovitics.azimut.rest.models.teacomputers.LookupResponse;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.services.user.AzimutDataLookUpService;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class LookUpMapper extends RestMapper<LookUpInput,LookUpOutput,LookupResponse,BusinessAzimutClient>{
@Autowired LookupApiConsumer lookupApiConsumer;
@Autowired AzimutDataLookUpService azimutDataLookUpService;
@Autowired TeaComputerService  teaComputerService;
	@Override
	BusinessAzimutClient consumeRestService(BusinessAzimutClient baseBusinessEntity, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessAzimutClient> consumeListRestService(BusinessAzimutClient businessAzimutDataLookup, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return  this.createListBusinessEntityFromOutput(this.lookupApiConsumer.invoke(this.createInput(businessAzimutDataLookup),LookupResponse[].class, params));
	}

	@Override
	LookUpInput createInput(BusinessAzimutClient baseBusinessEntity) {
		LookUpInput input =new LookUpInput();
		input.setTypeId(baseBusinessEntity.getEntityTypeId());
		return input;
	}

	@Override
	BusinessAzimutClient createBusinessEntityFromOutput(LookUpOutput lookUpOutput) {
		return null;
	}

	@Override
	protected List<BusinessAzimutClient> createListBusinessEntityFromOutput(LookUpOutput lookUpOutput) {
		
		List<AzimutDataLookup> azimutDataLookups=new ArrayList<AzimutDataLookup>();	
	
		//this.transferData(lookUpOutput);
		
		return null;
	}
	
	void transferData(LookUpOutput lookUpOutput)
	{
		List<Country> countries=new ArrayList<Country>();
		List<City> cities=new ArrayList<City>();
		List<Nationality> nationalities=new ArrayList<Nationality> ();
		if(lookUpOutput!=null&&lookUpOutput.getOutputs()!=null)
		{
			for(LookUpOutputs lookUpOutputs:lookUpOutput.getOutputs())
			{
				Country country=new Country();
				country.setArabicCountryName(lookUpOutputs.getArabicCountryName());
				country.setEnglishCountryName(lookUpOutputs.getEnglishCountryName());
				country.setCountryId(lookUpOutputs.getCountryId());
				country.setSystemCountryCode(lookUpOutputs.getSystemCountryCode());
				countries.add(country);
			}
			this.teaComputerService.saveAllCountries(countries);
			for(LookUpOutputs lookUpOutputs:lookUpOutput.getOutputs())
			{
				City city=new City();
				city.setArabicCityName(lookUpOutputs.getArabicCityName());
				city.setEnglishCityName(lookUpOutputs.getEnglishCityName());
				city.setCountryId(lookUpOutputs.getCountryId());
				city.setSystemCountryCode(lookUpOutputs.getSystemCountryCode());
				city.setCityId(lookUpOutputs.getCityId());
				city.setSystemCityCode(lookUpOutputs.getSystemCityCode());
				cities.add(city);
			}
			this.teaComputerService.saveAllCities(cities);			
			for(LookUpOutputs lookUpOutputs:lookUpOutput.getOutputs())
			{
				Nationality nationality=new Nationality();
				nationality.setArabicNationalityName(lookUpOutputs.getArabicNationalityName());
				nationality.setEnglishNationalityName(lookUpOutputs.getEnglishNationalityName());
				nationality.setNationalityId(lookUpOutputs.getNationalityId());
				nationality.setSystemNationalityCode(lookUpOutputs.getSystemNationalityCode());
				
				nationalities.add(nationality);
			}
			this.teaComputerService.saveAllNationalities(nationalities);

		}
		
		
		

	}

}
