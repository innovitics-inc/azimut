package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.result.Outputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.teacomputers.Bank;
import innovitics.azimut.models.teacomputers.Branch;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Currency;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.models.user.AzimutDataLookup;
import innovitics.azimut.rest.apis.teacomputers.LookupApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.LookUpInput;
import innovitics.azimut.rest.entities.teacomputers.LookUpOutput;
import innovitics.azimut.rest.entities.teacomputers.LookUpOutputs;
import innovitics.azimut.rest.models.teacomputers.LookupResponse;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.services.user.AzimutDataLookUpService;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
import innovitics.azimut.utilities.datautilities.NumberUtility;

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
		
		
		LookUpOutput lookUpOutput=this.lookupApiConsumer.invoke(this.createInput(businessAzimutDataLookup),LookupResponse[].class, businessAzimutDataLookup.getParam());
		
		lookUpOutput.setTypeId(businessAzimutDataLookup.getEntityTypeId());
		
		return this.createListBusinessEntityFromOutput(lookUpOutput);
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
			
		this.transferData(lookUpOutput);
		
		return new ArrayList<BusinessAzimutClient>();
	}
	
	void transferData(LookUpOutput lookUpOutput)
	{
		List<Country> countries=new ArrayList<Country>();
		List<City> cities=new ArrayList<City>();
		List<Nationality> nationalities=new ArrayList<Nationality> ();
		List<Bank> banks=new ArrayList<Bank> ();
		List<Branch> branches=new ArrayList<Branch> ();
		List<Currency> currencies=new ArrayList<Currency> ();
		
		if(NumberUtility.areLongValuesMatching(lookUpOutput.getTypeId(), AzimutEntityType.COUNTRY.getTypeId()))
		this.synchronizeCountries(lookUpOutput, countries);
		
		if(NumberUtility.areLongValuesMatching(lookUpOutput.getTypeId(), AzimutEntityType.CITY.getTypeId()))
		this.synchronizeCities(lookUpOutput, cities);
		
		if(NumberUtility.areLongValuesMatching(lookUpOutput.getTypeId(), AzimutEntityType.NATIONALITY.getTypeId()))
		this.synchronizeNationalities(lookUpOutput, nationalities);
		
		if(NumberUtility.areLongValuesMatching(lookUpOutput.getTypeId(), AzimutEntityType.BANK.getTypeId()))
		this.synchronizeBanks(lookUpOutput, banks);
		
		if(NumberUtility.areLongValuesMatching(lookUpOutput.getTypeId(), AzimutEntityType.BRANCH.getTypeId()))
		this.synchronizeBranches(lookUpOutput, branches);
		
		if(NumberUtility.areLongValuesMatching(lookUpOutput.getTypeId(), AzimutEntityType.CURRENCY.getTypeId()))
		this.synchronizeCurrencies(lookUpOutput, currencies);
		
		

	}
	
	
	void synchronizeCountries(LookUpOutput lookUpOutput,List<Country> countries)
	{
		this.teaComputerService.deleteAllCountries();
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
	}
	
	void synchronizeCities(LookUpOutput lookUpOutput,List<City> cities)
	{
		this.teaComputerService.deleteAllCities();
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
	}
	
	void synchronizeNationalities(LookUpOutput lookUpOutput,List<Nationality> nationalities)
	{
		this.teaComputerService.deleteAllNationalities();
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
	
	void synchronizeBanks(LookUpOutput lookUpOutput,List<Bank> banks)
	{
		this.teaComputerService.deleteAllBanks();
		for(LookUpOutputs lookUpOutputs:lookUpOutput.getOutputs())
		{
			Bank bank=new Bank();
			bank.setArabicBankName(lookUpOutputs.getArabicBankName());
			bank.setEnglishBankName(lookUpOutputs.getEnglishBankName());
			bank.setBankId(lookUpOutputs.getBankId());
			bank.setSystemBankCode(lookUpOutputs.getSystemBankCode());
			
			banks.add(bank);
		}
		this.teaComputerService.saveAllBanks(banks);
	}
	
	void synchronizeBranches(LookUpOutput lookUpOutput,List<Branch> branches)
	{
		this.teaComputerService.deleteAllBranches();
		for(LookUpOutputs lookUpOutputs:lookUpOutput.getOutputs())
		{
			Branch branch=new Branch();
			branch.setArabicBranchName(lookUpOutputs.getArabicBranchName());
			branch.setEnglishBranchName(lookUpOutputs.getEnglishBranchName());
			branch.setBankId(lookUpOutputs.getSystemBankCode());
			branch.setSystemBankCode(lookUpOutputs.getSystemBankCode());
			branch.setBranchId(lookUpOutputs.getBranchId());
			branch.setSystemBranchCode(lookUpOutputs.getSystemBranchCode());
			
			branches.add(branch);
		}
		this.teaComputerService.saveAllBranches(branches);
	}
	
	void synchronizeCurrencies(LookUpOutput lookUpOutput,List<Currency> currencies)
	{
		this.teaComputerService.deleteAllCurrencies();
		for(LookUpOutputs lookUpOutputs:lookUpOutput.getOutputs())
		{
			Currency currency=new Currency();
			currency.setArabicCurrencyName(lookUpOutputs.getArabicCurrencyName());
			currency.setEnglishCurrencyName(lookUpOutputs.getEnglishCurrencyName());
			currency.setCurrencyId(lookUpOutputs.getCurrencyId());
			currency.setSystemCurrencyCode(lookUpOutputs.getSystemCurrencyCode());
			
			currencies.add(currency);
		}
		this.teaComputerService.saveAllCurrencies(currencies);
	}
	
	

}
