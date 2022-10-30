package innovitics.azimut.services.teacomputer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.teacomputers.Bank;
import innovitics.azimut.models.teacomputers.Branch;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.ClientBankAccount;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Currency;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.repositories.teacomputers.BankDynamicRepository;
import innovitics.azimut.repositories.teacomputers.BranchDynamicRepository;
import innovitics.azimut.repositories.teacomputers.CityDynamicRepository;
import innovitics.azimut.repositories.teacomputers.ClientBankAccountDynamicRepository;
import innovitics.azimut.repositories.teacomputers.CountryDynamicRepository;
import innovitics.azimut.repositories.teacomputers.CurrencyDynamicRepository;
import innovitics.azimut.repositories.teacomputers.NationalityDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.BranchChildSpecification;
import innovitics.azimut.utilities.dbutilities.specifications.child.CityChildSpecification;
import innovitics.azimut.utilities.dbutilities.specifications.child.ClientBankAccountEntitySpecification;

@Service
public class TeaComputerService extends AbstractService<Country, String> {

	@Autowired CountryDynamicRepository countryDynamicRepository;
	@Autowired CityDynamicRepository cityDynamicRepository;
	@Autowired NationalityDynamicRepository nationalityDynamicRepository;
	@Autowired CityChildSpecification cityChildSpecification;
	@Autowired BranchChildSpecification branchChildSpecification;
	@Autowired BankDynamicRepository bankDynamicRepository;
	@Autowired BranchDynamicRepository branchDynamicRepository;
	@Autowired CurrencyDynamicRepository currencyDynamicRepository;
	@Autowired ClientBankAccountDynamicRepository clientBankAccountDynamicRepository;
	@Autowired ClientBankAccountEntitySpecification clientBankAccountEntitySpecification;
	
	public List<Country> saveAllCountries (List<Country> countries)
	{
		return this.countryDynamicRepository.saveAll(countries);
	}

	public List<City> saveAllCities (List<City> cities)
	{
		return this.cityDynamicRepository.saveAll(cities);
	}
	
	public List<Nationality> saveAllNationalities (List<Nationality> nationalities)
	{
		return this.nationalityDynamicRepository.saveAll(nationalities);
	}
	public List<Bank> saveAllBanks (List<Bank> banks)
	{
		return this.bankDynamicRepository.saveAll(banks);
	}
	public List<Branch> saveAllBranches (List<Branch> branches)
	{
		return this.branchDynamicRepository.saveAll(branches);
	}
	public List<Currency> saveAllCurrencies (List<Currency> currencies)
	{
		return this.currencyDynamicRepository.saveAll(currencies);
	}
	public List<Country> getAllCountries()
	{
		return this.countryDynamicRepository.findAll();
	}
	public List<City> getAllCities()
	{
		return  this.cityDynamicRepository.findAll();
	}
	public List<City> getAllCitiesByCountryId(Long countryId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("countryId", countryId,SearchOperation.EQUAL, null));
		return this.cityDynamicRepository.findAll(cityChildSpecification.findByCriteria(searchCriteriaList));
	}
	public List<Nationality> getAllNationalities()
	{
		return this.nationalityDynamicRepository.findAll();
	}
	public List<Bank> getAllBanks()
	{
		return this.bankDynamicRepository.findAll();
	}
	public List<Branch> getAllBranches()
	{
		return  this.branchDynamicRepository.findAll();
	}	
	public List<Branch> getAllBranchesByBankId(Long bankId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("bankId", bankId,SearchOperation.EQUAL, null));
		return this.branchDynamicRepository.findAll(branchChildSpecification.findByCriteria(searchCriteriaList));
	}
	public List<Currency> getAllCurrencies()
	{
		return this.currencyDynamicRepository.findAll();
	}
	
	
	public void deleteAllCountries()
	{
		this.countryDynamicRepository.deleteAllInBatch();
	}
	public void deleteAllCities()
	{
		this.cityDynamicRepository.deleteAllInBatch();
	}
	public void deleteAllNationalities()
	{
		this.nationalityDynamicRepository.deleteAllInBatch();
	}
	public void deleteAllBanks()
	{
		this.bankDynamicRepository.deleteAllInBatch();
	}
	public void deleteAllBranches()
	{
		this.branchDynamicRepository.deleteAllInBatch();
	}
	public void deleteAllCurrencies()
	{
		this.currencyDynamicRepository.deleteAllInBatch();
	}
	
	public List<ClientBankAccount> getUserClientBankAccounts(Long userId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userId",userId,SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt","",SearchOperation.IS_NULL,null));
		return this.clientBankAccountDynamicRepository.findAll(this.clientBankAccountEntitySpecification.findByCriteria(searchCriteriaList));
	}
	public List<ClientBankAccount> getUserClientBankAccounts(Long userId,Boolean kycOnly)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userId",userId,SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt","",SearchOperation.IS_NULL,null));
		
		if(BooleanUtility.isTrue(kycOnly))
		searchCriteriaList.add(new SearchCriteria("kycOnly",true,SearchOperation.EQUAL,null));
		
		return this.clientBankAccountDynamicRepository.findAll(this.clientBankAccountEntitySpecification.findByCriteria(searchCriteriaList));
	}
	
	public void saveClientBankAccountsTemporarily(List<ClientBankAccount> clientBankAccounts)
	{
		this.clientBankAccountDynamicRepository.saveAll(clientBankAccounts);
	}

	public ClientBankAccount removeClientBankAccount(Long id)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id",id,SearchOperation.EQUAL,null));
		searchCriteriaList.add(new SearchCriteria("deletedAt","",SearchOperation.IS_NULL,null));
		
		ClientBankAccount clientBankAccount=new ClientBankAccount();
		clientBankAccount=this.clientBankAccountDynamicRepository.findOne(this.clientBankAccountEntitySpecification.findByCriteria(searchCriteriaList)).get();
		
		clientBankAccount.setDeletedAt(new Date());
		
		this.clientBankAccountDynamicRepository.save(clientBankAccount);
		return clientBankAccount;
		
	}
	
	public void deleteClientBankAccounts(Long userId)
	{
		this.clientBankAccountDynamicRepository.softDeleteClientBankAccounts(userId);
	}
}
