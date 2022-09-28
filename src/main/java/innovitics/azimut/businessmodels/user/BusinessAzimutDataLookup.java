package innovitics.azimut.businessmodels.user;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.teacomputers.Bank;
import innovitics.azimut.models.teacomputers.Branch;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Currency;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.models.user.UserType;

public class BusinessAzimutDataLookup extends BaseBusinessEntity{


	private String entityType;
	private Long entityTypeId;
	
	private List<Country> countries;
	private List<City> cities;
	private List<Nationality> nationalities;
	private List<Currency> currencies;
	private List<Bank> banks;
	private List<Branch> branches;
	private List<UserType> userTypes;
	private List<BusinessCompanyBankAccount> companyBankAccounts;
	
	
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Long getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public List<Country> getCountries() {
		return countries;
	}
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public List<Nationality> getNationalities() {
		return nationalities;
	}
	public void setNationalities(List<Nationality> nationalities) {
		this.nationalities = nationalities;
	}
	public List<Currency> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	public List<Bank> getBanks() {
		return banks;
	}
	public void setBanks(List<Bank> banks) {
		this.banks = banks;
	}
	public List<Branch> getBranches() {
		return branches;
	}
	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}
	public List<UserType> getUserTypes() {
		return userTypes;
	}
	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}
	public List<BusinessCompanyBankAccount> getCompanyBankAccounts() {
		return companyBankAccounts;
	}
	public void setCompanyBankAccounts(List<BusinessCompanyBankAccount> companyBankAccounts) {
		this.companyBankAccounts = companyBankAccounts;
	}
	
	
	
	
	
	
}
