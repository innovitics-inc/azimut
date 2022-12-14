package innovitics.azimut.utilities.businessutilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessAzimutDataLookup;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.teacomputers.Bank;
import innovitics.azimut.models.teacomputers.Branch;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.ClientBankAccount;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Currency;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.rest.mappers.LookUpMapper;
import innovitics.azimut.services.kyc.UserTypeService;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
import innovitics.azimut.utilities.crosslayerenums.BankAccountStatus;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
@Component
public class AzimutDataLookupUtility extends ParentUtility {

@Autowired TeaComputerService teaComputerService;
@Autowired ExceptionHandler exceptionHandler;
@Autowired LookUpMapper lookUpMapper;
@Autowired ListUtility<ClientBankAccount> clientBankAccountListUtility;
@Autowired ListUtility<BusinessClientBankAccountDetails> businessClientBankAccountListUtility ;
@Autowired ArrayUtility arrayUtility;
@Autowired UserTypeService userTypeService;




	public	List<Long> getAzimutEntityTypeIds()
	{
		List<Long> ids=new ArrayList<Long>();
		for(AzimutEntityType azimutEntityType:AzimutEntityType.values())
		{
			ids.add(azimutEntityType.getTypeId());
		}
		return ids;
	}




public void syncTeaComputersData() throws IntegrationException
{
	this.lookUpMapper.wrapBaseBusinessEntity(true, new BusinessAzimutClient(AzimutEntityType.BANK), null);
	this.lookUpMapper.wrapBaseBusinessEntity(true, new BusinessAzimutClient(AzimutEntityType.BRANCH), null);
	this.lookUpMapper.wrapBaseBusinessEntity(true, new BusinessAzimutClient(AzimutEntityType.CURRENCY), null);
	
	this.lookUpMapper.wrapBaseBusinessEntity(true, new BusinessAzimutClient(AzimutEntityType.COUNTRY), null);
	this.lookUpMapper.wrapBaseBusinessEntity(true, new BusinessAzimutClient(AzimutEntityType.CITY), null);
	this.lookUpMapper.wrapBaseBusinessEntity(true, new BusinessAzimutClient(AzimutEntityType.NATIONALITY), null);
}

	public void saveAzimutClientBankAccountData(BusinessUser businessUser,BusinessAzimutClient businessAzimutClient)
	{
		List<ClientBankAccount> clientBankAccounts=new ArrayList<ClientBankAccount>();
		
		if(this.arrayUtility.isArrayPopulated(businessAzimutClient.getClientBankAccounts()))
		{
			for(BusinessClientBankAccountDetails businessClientBankAccountDetailsIterator: businessAzimutClient.getClientBankAccounts())
			{
				clientBankAccounts.add(this.convertBusinessClientBankAccountDetailsToClientBankAccount(businessAzimutClient.getPersist(),businessClientBankAccountDetailsIterator, businessUser));
			}
		}
		this.teaComputerService.saveClientBankAccountsTemporarily(clientBankAccounts);	
	}

	public BusinessClientBankAccountDetails getKYCClientBankAccountData(BusinessUser businessUser,Long accountId)
	{
		List<ClientBankAccount> clientBankAccounts=new  ArrayList<ClientBankAccount>();
		try {
			clientBankAccounts=this.teaComputerService.getUserClientBankAccounts(businessUser.getId(),accountId);
		}
		catch (Exception exception) {
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		if(clientBankAccountListUtility.isListPopulated(clientBankAccounts))
		{
			return this.convertClientBankAccountToBusinAccountDetails(clientBankAccounts.get(0));
		}
		else
		{
			return new BusinessClientBankAccountDetails();
		}
	}
	
	public BusinessClientBankAccountDetails[] getKYCClientBankAccountData(BusinessUser businessUser)
	{
		return this.getData(businessUser, true);
	}
	
	public BusinessClientBankAccountDetails[] getClientBankAccountData(BusinessUser businessUser)
	{
		return this.getData(businessUser, false);
	}

	public BusinessClientBankAccountDetails[] getData(BusinessUser businessUser,boolean kyc)
	{
		List<ClientBankAccount> clientBankAccounts=new  ArrayList<ClientBankAccount>();
		try 
		{
			clientBankAccounts=this.teaComputerService.getUserClientBankAccounts(businessUser.getId(),kyc);

		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				BusinessClientBankAccountDetails[] empty= {};
				return empty;
			}
		}
		
		if(clientBankAccountListUtility.isListPopulated(clientBankAccounts))
		{
			BusinessClientBankAccountDetails[] businessClientBankAccountDetailsArray=new BusinessClientBankAccountDetails[clientBankAccounts.size()];
			List<BusinessClientBankAccountDetails> businessClientBankAccountDetailsList=new ArrayList<BusinessClientBankAccountDetails>();
			
			
			for(ClientBankAccount clientBankAccount:clientBankAccounts)
			{
				if(clientBankAccount!=null)
				{
					BusinessClientBankAccountDetails businessClientBankAccountDetails=new BusinessClientBankAccountDetails();
					businessClientBankAccountDetails.setAccountId(clientBankAccount.getId());
					businessClientBankAccountDetails.setBankId(clientBankAccount.getBankId());
					businessClientBankAccountDetails.setBranchId(clientBankAccount.getBranchId());
					businessClientBankAccountDetails.setCurrencyId(clientBankAccount.getCurrencyId());
					businessClientBankAccountDetails.setIban(clientBankAccount.getIban());
					businessClientBankAccountDetails.setSwiftCode(clientBankAccount.getSwiftCode());
					businessClientBankAccountDetails.setAccountNumber(clientBankAccount.getAccountNo());
					businessClientBankAccountDetails.setEnglishBankName(clientBankAccount.getEnglishBankName());
					businessClientBankAccountDetails.setArabicBankName(clientBankAccount.getArabicBankName());
					businessClientBankAccountDetails.setEnglishBranchName(clientBankAccount.getEnglishBranchName());
					businessClientBankAccountDetails.setArabicBranchName(clientBankAccount.getArabicBranchName());
					businessClientBankAccountDetails.setEnglishCurrencyName(clientBankAccount.getEnglishCurrencyName());
					businessClientBankAccountDetails.setArabicCurrencyName(clientBankAccount.getArabicCurrencyName());
					businessClientBankAccountDetails.setStatus(BankAccountStatus.PENDING.getStatusId());
					businessClientBankAccountDetails.setAccountStatus(BankAccountStatus.PENDING.getStatusId());
					businessClientBankAccountDetails.setStatusName(BankAccountStatus.PENDING.getStatus());
					businessClientBankAccountDetails.setIsLocal(true);
					businessClientBankAccountDetailsList.add(businessClientBankAccountDetails);
				}
			}
			
			businessClientBankAccountDetailsList.toArray(businessClientBankAccountDetailsArray);
			
			return businessClientBankAccountDetailsArray;			
		}
		else
		{
			BusinessClientBankAccountDetails[] empty= {};
			return empty;
		}
	}

 public ClientBankAccount removeClientBankAccount(Long id)
 {
	 try {
		 this.teaComputerService.removeClientBankAccount(id);
	 }
	 catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return null;
 }

public BusinessAzimutDataLookup getLookups(BusinessAzimutDataLookup businessAzimutDataLookup)
{

	if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.COUNTRY.getTypeId()))
	{
		List<Country> countries=this.getTeaComputerCountries();
		businessAzimutDataLookup.setCountries(countries);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.CITY.getTypeId()))
	{
		List<City> cities=this.getTeaComputerCitiesByCountryId(businessAzimutDataLookup.getCountryId());
		businessAzimutDataLookup.setCities(cities);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.NATIONALITY.getTypeId()))
	{
		List<Nationality> nationalities=this.getTeaComputerNationalities();
		businessAzimutDataLookup.setNationalities(nationalities);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.CURRENCY.getTypeId()))
	{
		List<Currency> currencies=this.getTeaComputerCurrencies();
		businessAzimutDataLookup.setCurrencies(currencies);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.BANK.getTypeId()))
	{
		List<Bank> banks=this.getTeaComputerBanks();
		businessAzimutDataLookup.setBanks(banks);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.BRANCH.getTypeId()))
	{
		List<Branch> branchs=this.getTeaComputerBranchesByBankId(businessAzimutDataLookup.getBankId());
		businessAzimutDataLookup.setBranches(branchs);
	}
		
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.USER_TYPE.getTypeId()))
	{
		List<UserType> userTypes=this.userTypeService.findAll();
		businessAzimutDataLookup.setUserTypes(userTypes);
	}	
	
	return businessAzimutDataLookup;
}



	List<Country> getTeaComputerCountries()
	{	
		try 
		{			
			return this.teaComputerService.getAllCountries();			
		}
		catch(Exception exception)
		{
			this.getNullIfNonExistent(exception);
		}
		return null;
	}

	List<City> getTeaComputerCitiesByCountryId(Long countryId)
	{	
		try 
		{			
			return this.teaComputerService.getAllCitiesByCountryId(countryId);			
		}
		catch(Exception exception)
		{
			this.getNullIfNonExistent(exception);
		}
		return null;
	}
	List<Nationality> getTeaComputerNationalities()
	{	
		try 
		{			
			return this.teaComputerService.getAllNationalities();			
		}
		catch(Exception exception)
		{
			this.getNullIfNonExistent(exception);
		}
		return null;
	}

	List<Currency> getTeaComputerCurrencies()
	{	
		try 
		{			
			return this.teaComputerService.getAllCurrencies();			
		}
		catch(Exception exception)
		{
			this.getNullIfNonExistent(exception);
		}
		return null;
	}
	List<Bank> getTeaComputerBanks()
	{	
		try 
		{			
			return this.teaComputerService.getAllBanks();			
		}
		catch(Exception exception)
		{
			this.getNullIfNonExistent(exception);
		}
		return null;
	}
	List<Branch> getTeaComputerBranchesByBankId(Long bankId)
	{	
		try 
		{			
			return this.teaComputerService.getAllBranchesByBankId(bankId);			
		}
		catch(Exception exception)
		{
			this.getNullIfNonExistent(exception);
		}
		return null;
	}
	
	 ClientBankAccount	convertBusinessClientBankAccountDetailsToClientBankAccount(Boolean persist,BusinessClientBankAccountDetails businessClientBankAccountDetails,BusinessUser businessUser)
		{
		 ClientBankAccount clientBankAccount=new ClientBankAccount();
		 
			clientBankAccount.setUserId(businessUser.getId());
			clientBankAccount.setIdTypeId(businessUser.getIdType());
			clientBankAccount.setIdNumber(businessUser.getUserId());			
			clientBankAccount.setBankId(businessClientBankAccountDetails.getBankId());
			clientBankAccount.setBranchId(businessClientBankAccountDetails.getBranchId());
			clientBankAccount.setCurrencyId(businessClientBankAccountDetails.getCurrencyId());			
			clientBankAccount.setEnglishBankName(businessClientBankAccountDetails.getEnglishBankName());
			clientBankAccount.setArabicBankName(businessClientBankAccountDetails.getArabicBankName());
			clientBankAccount.setEnglishBranchName(businessClientBankAccountDetails.getEnglishBranchName());
			clientBankAccount.setArabicBranchName(businessClientBankAccountDetails.getArabicBranchName());
			clientBankAccount.setEnglishCurrencyName(businessClientBankAccountDetails.getEnglishCurrencyName());
			clientBankAccount.setArabicCurrencyName(businessClientBankAccountDetails.getArabicCurrencyName());
			clientBankAccount.setIban(businessClientBankAccountDetails.getIban());
			clientBankAccount.setAccountNo(businessClientBankAccountDetails.getAccountNumber());
			clientBankAccount.setSwiftCode(businessClientBankAccountDetails.getSwiftCode());
			clientBankAccount.setKycOnly(BooleanUtility.isFalse(persist)?true:false);
			
			return clientBankAccount;
		}	
		
	Object getNullIfNonExistent(Exception exception)
	 {
		 exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
			return null;
	 }

	
	
	BusinessClientBankAccountDetails convertClientBankAccountToBusinAccountDetails(ClientBankAccount clientBankAccount)
	{
		BusinessClientBankAccountDetails businessClientBankAccountDetails=new BusinessClientBankAccountDetails();
		businessClientBankAccountDetails.setAccountId(clientBankAccount.getId());
		businessClientBankAccountDetails.setBankId(clientBankAccount.getBankId());
		businessClientBankAccountDetails.setBranchId(clientBankAccount.getBranchId());
		businessClientBankAccountDetails.setCurrencyId(clientBankAccount.getCurrencyId());
		businessClientBankAccountDetails.setIban(clientBankAccount.getIban());
		businessClientBankAccountDetails.setSwiftCode(clientBankAccount.getSwiftCode());
		businessClientBankAccountDetails.setAccountNumber(clientBankAccount.getAccountNo());
		businessClientBankAccountDetails.setEnglishBankName(clientBankAccount.getEnglishBankName());
		businessClientBankAccountDetails.setArabicBankName(clientBankAccount.getArabicBankName());
		businessClientBankAccountDetails.setEnglishBranchName(clientBankAccount.getEnglishBranchName());
		businessClientBankAccountDetails.setArabicBranchName(clientBankAccount.getArabicBranchName());
		businessClientBankAccountDetails.setEnglishCurrencyName(clientBankAccount.getEnglishCurrencyName());
		businessClientBankAccountDetails.setArabicCurrencyName(clientBankAccount.getArabicCurrencyName());
		businessClientBankAccountDetails.setStatus(BankAccountStatus.PENDING.getStatusId());
		businessClientBankAccountDetails.setAccountStatus(BankAccountStatus.PENDING.getStatusId());
		businessClientBankAccountDetails.setStatusName(BankAccountStatus.PENDING.getStatus());
		businessClientBankAccountDetails.setIsLocal(true);
		
		return businessClientBankAccountDetails;
	}
	
}
