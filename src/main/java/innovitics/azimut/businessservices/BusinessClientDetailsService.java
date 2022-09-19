package innovitics.azimut.businessservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.businessmodels.funds.BusinessClientFund;
import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.businessmodels.funds.BusinessFundTransaction;
import innovitics.azimut.businessmodels.user.AzimutAccount;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessAzimutDataLookup;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.businessmodels.user.BusinessClientCashBalance;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.mappers.AddAccountMapper;
import innovitics.azimut.rest.mappers.AddClientBankAccountMapper;
import innovitics.azimut.rest.mappers.CheckAccountMapper;
import innovitics.azimut.rest.mappers.GetClientBalanceMapper;
import innovitics.azimut.rest.mappers.GetClientBankAccountsMapper;
import innovitics.azimut.rest.mappers.GetClientFundsMapper;
import innovitics.azimut.rest.mappers.GetFundPricesMapper;
import innovitics.azimut.rest.mappers.GetFundTransactionsMapper;
import innovitics.azimut.rest.mappers.GetTransactionsMapper;
import innovitics.azimut.services.FundService;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.services.user.AzimutDataLookUpService;
import innovitics.azimut.utilities.businessutilities.SortCompare;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.crosslayerenums.TransactionStatus;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.AzimutDataLookupUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.FundPriceMapper;
import innovitics.azimut.validations.validators.azimutclient.GetAzimutEntityLookup;
import innovitics.azimut.validations.validators.azimutclient.GetBalanceAndTransactions;
import innovitics.azimut.validations.validators.azimutclient.RemoveClientBankAccount;
import innovitics.azimut.validations.validators.azimutclient.SaveClientBankAccountTemporarily;
import innovitics.azimut.validations.validators.azimutclient.SaveClientBankAccountsTemporarily;
@Service
public class BusinessClientDetailsService extends AbstractBusinessService<BusinessAzimutClient> {

@Autowired GetClientBalanceMapper getClientBalanceMapper;
@Autowired GetTransactionsMapper getTransactionsMapper;
@Autowired GetClientBankAccountsMapper getClientBankAccountsMapper;
@Autowired AddClientBankAccountMapper addClientBankAccountMapper;
@Autowired CheckAccountMapper checkAccountMapper;
@Autowired AddAccountMapper addAccountMapper;
@Autowired  GetClientFundsMapper getClientFundsMapper;
@Autowired  GetFundPricesMapper getFundPricesMapper;
@Autowired ListUtility<BusinessTransaction>listUtility;
@Autowired ListUtility<BusinessFundPrice> fundPricesListUtility;
@Autowired ListUtility<BusinessClientFund> clientFundListUtility ;
@Autowired SortCompare sortCompare;
@Autowired GetBalanceAndTransactions getBalanceAndTransactions;
@Autowired AzimutDataLookUpService azimutDataLookUpService;
@Autowired AzimutDataLookupUtility azimutDataLookupUtility;
@Autowired TeaComputerService teaComputerService;
@Autowired BusinessUserService businessUserService;
@Autowired SaveClientBankAccountsTemporarily saveClientBankAccountsTemporarily;
@Autowired SaveClientBankAccountTemporarily saveClientBankAccountTemporarily;
@Autowired RemoveClientBankAccount removeClientBankAccount;
@Autowired GetAzimutEntityLookup getAzimutEntityLookup;
@Autowired FundService fundService;
@Autowired FundPriceMapper fundPriceMapper;
@Autowired GetFundTransactionsMapper getFundTransactionsMapper;

	public BusinessAzimutClient getBalanceAndTransactions(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		this.validation.validate(businessAzimutClient, getBalanceAndTransactions, BusinessAzimutClient.class.getName());

		try 
		{
				responseBusinessAzimutClient.setBusinessTransactions(getTransactionsMapper.wrapBaseBusinessEntity(true,this.prepareTransactionSearchInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList());
				responseBusinessAzimutClient.setBusinessClientCashBalance(getClientBalanceMapper.wrapBaseBusinessEntity(false,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null).getData());
				
		}
		catch(Exception exception)
		{
	
			if(exception instanceof IntegrationException)
			throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else		
			throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}

		return this.beautifyBalanceAndTransactionsBusinessAzimutClient(responseBusinessAzimutClient);
	}
	
	
	

	public BusinessAzimutClient getBankAccountsWithDetails(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,boolean isList) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		try 
		{			
			if(isList)
			responseBusinessAzimutClient.setBankList(getClientBankAccountsMapper.wrapBaseBusinessEntity(isList, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null).getDataList());
			else if(!isList)
			responseBusinessAzimutClient.setBankAccountDetails(getClientBankAccountsMapper.wrapBaseBusinessEntity(isList, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null).getData());	
		}
		catch(Exception exception)
		{
	

			if(exception instanceof IntegrationException)
			{
				this.logger.info("Detecting the exception type in the checkAccountAtTeaComputers method:::");
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			}
			else
			{
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
			}
		}

		return responseBusinessAzimutClient;
	}
	
	public BusinessAzimutClient checkAccountAtTeaComputers(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		try 
		{			
			responseBusinessAzimutClient.setAzimutAccounts(this.checkAccountMapper.wrapBaseBusinessEntity(true, this.prepareAccountRetrievalInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList());	
		}
		catch(Exception exception)
		{
	
			if(exception instanceof IntegrationException)
			{
				this.logger.info("Detecting the exception type in the checkAccountAtTeaComputers method:::");
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			}
			else
			{
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
			}
		}

		return responseBusinessAzimutClient;
	}
	public BusinessAzimutClient saveTeaComputersAccountData(AzimutAccount azimutAccount,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(azimutAccount.getId(), tokenizedBusinessUser);
		try 
		{
			tokenizedBusinessUser.setAzimutAccount(azimutAccount);
			this.businessUserService.editUser(tokenizedBusinessUser);
		}
		catch(Exception exception)
		{
	
			if(exception instanceof IntegrationException)
			throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else		
			throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}

		return responseBusinessAzimutClient;
	}
	public BusinessAzimutClient addAccountAtTeaComputers(AzimutAccount azimutAccount,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(azimutAccount.getId(), tokenizedBusinessUser);
		try 
		{			
			this.addAccountMapper.wrapBaseBusinessEntity(false, this.prepareAccountAdditionInputs(azimutAccount,tokenizedBusinessUser), null).getData();
			tokenizedBusinessUser.setIsVerified(true);
			this.businessUserService.editUser(tokenizedBusinessUser);
			
			this.addClientBankAccountMapper.consumeRestServiceInALoop(new BusinessAzimutClient(this.azimutDataLookupUtility.getClientBankAccountData(tokenizedBusinessUser)),
					tokenizedBusinessUser.getUserId(),this.getAzimutUserTypeId(tokenizedBusinessUser));
			this.teaComputerService.deleteClientBankAccounts(tokenizedBusinessUser.getId());
		}
		catch(Exception exception)
		{
	
			if(exception instanceof IntegrationException)
			throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else		
			throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}

		return responseBusinessAzimutClient;
	}
	public BusinessAzimutClient getAzimutLookupData(BusinessAzimutDataLookup businessAzimutDataLookup ,BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		this.validation.validate(businessAzimutDataLookup, getAzimutEntityLookup, BusinessAzimutDataLookup.class.getName());
		try 
		{
			BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
			businessAzimutClient.setLookupData(this.azimutDataLookupUtility.getLookups(businessAzimutDataLookup));
			return businessAzimutClient;
		}
		catch(Exception exception)
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
		}
		return null;
	}
	
	public BusinessAzimutClient synchronizeTeaComputersLookupData(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		try 
		{		
			this.azimutDataLookupUtility.syncTeaComputersData();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
			}
		}
		return new BusinessAzimutClient();
	}
	
	public BusinessAzimutClient saveClientBankAccounts(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		this.validation.validate(businessAzimutClient, saveClientBankAccountsTemporarily, BusinessAzimutClient.class.getName());
		BusinessAzimutClient responseBusinessAzimutClient= new BusinessAzimutClient();
		for(BusinessClientBankAccountDetails businessClientBankAccountDetails:businessAzimutClient.getClientBankAccounts())
		{			
			this.validation.validate(businessClientBankAccountDetails, saveClientBankAccountTemporarily, BusinessClientBankAccountDetails.class.getName());
		}
		
		try 
		{	
			this.teaComputerService.deleteClientBankAccounts(tokenizedBusinessUser.getId());
			this.azimutDataLookupUtility.saveAzimutClientBankAccountData(tokenizedBusinessUser,businessAzimutClient.getClientBankAccounts());
			BusinessUser editedUser=this.userUtility.isOldUserStepGreaterThanNewUserStep(tokenizedBusinessUser, UserStep.BANK_REFERENCES_SHOW.getStepId());
			this.businessUserService.editUser(editedUser);
			responseBusinessAzimutClient.setVerificationPercentage(editedUser.getVerificationPercentage());
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			throw new BusinessException(ErrorCode.OPERATION_NOT_PERFORMED);
		}
		return responseBusinessAzimutClient;
	}
	public BusinessAzimutClient saveClientBankAccountsAtTeacomputers(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		this.validation.validate(businessAzimutClient, saveClientBankAccountTemporarily, BusinessAzimutClient.class.getName());
				
		try 
		{	
			BusinessClientBankAccountDetails request=businessAzimutClient.getClientBankAccounts()[0];
			request.setAzId(tokenizedBusinessUser.getUserId());
			request.setAzIdType(tokenizedBusinessUser.getIdType());
			this.addClientBankAccountMapper.wrapBaseBusinessEntity(false,request, null);
		}
		catch(Exception exception)
		{
			if(exception instanceof IntegrationException)
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
				else		
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}
		return new BusinessAzimutClient();
	}
		
	public BusinessAzimutClient removeClientBankAccount(BusinessClientBankAccountDetails businessClientBankAccountDetails) throws BusinessException
	{
		this.validation.validate(businessClientBankAccountDetails, removeClientBankAccount, BusinessClientBankAccountDetails.class.getName());
		try {
				this.teaComputerService.removeClientBankAccount(businessClientBankAccountDetails.getId());
			}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}
		
		return new BusinessAzimutClient();
	}
	
	public BusinessAzimutClient getTemporaryClientBankAccountDetails(BusinessUser businessUser) throws BusinessException
	{
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		try {
				businessAzimutClient.setClientBankAccounts(this.azimutDataLookupUtility.getClientBankAccountData(businessUser));
			}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);

		}
		return businessAzimutClient;
	}
	
	public BusinessAzimutClient getAzimutDetails() throws BusinessException
	{
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		try {
				businessAzimutClient.setAzimutDetails(this.azimutDataLookUpService.getAzimutDetails());
			}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);

		}
		return businessAzimutClient;
	}
	
	
	public BusinessAzimutClient getClientFundsList(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws IntegrationException, BusinessException
	{
		
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		
		try {
		Map <String,Object> clientFundPriceMap= this.getClientFunds(tokenizedBusinessUser, responseBusinessAzimutClient);

		BusinessClientCashBalance businessClientCashBalance=this.getClientBalanceMapper.wrapBaseBusinessEntity(false,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null).getData();

		this.beautifyBusinessClientFunds(responseBusinessAzimutClient,(List<BusinessClientFund>)clientFundPriceMap.get(BusinessClientFund.class.getName()),(List<BusinessFundPrice>)clientFundPriceMap.get(BusinessFundPrice.class.getName()), businessClientCashBalance);
		}
		
		catch(Exception exception)
		{
			if(exception instanceof IntegrationException)
			{
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			}
			else
			{
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
			}
		}
		
		return responseBusinessAzimutClient;
	}
	
	public BusinessAzimutClient getClientFundDetails(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws IntegrationException
	{	
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();

		Map <String,Object> clientFundPriceMap= this.getClientFunds(tokenizedBusinessUser, responseBusinessAzimutClient);

		List<BusinessFundTransaction> businessFundTransactions=this.getFundTransactionsMapper.wrapBaseBusinessEntity(true, this.prepareBusinessBusinessFundTransactionRetrievalInputs(tokenizedBusinessUser), null).getDataList();
		
		this.beautifyBusinessClientFunds(responseBusinessAzimutClient,(List<BusinessClientFund>)clientFundPriceMap.get(BusinessClientFund.class.getName()),(List<BusinessFundPrice>)clientFundPriceMap.get(BusinessFundPrice.class.getName()),null, businessFundTransactions);
				
		return responseBusinessAzimutClient;
	}
		
	Map<String,Object> getClientFunds(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws IntegrationException
	{
		List<BusinessFundPrice> businessFundPrices=new ArrayList<BusinessFundPrice>();
		List<BusinessClientFund> businessClientFunds=new ArrayList<BusinessClientFund>();
		businessClientFunds=this.getClientFundsMapper.wrapBaseBusinessEntity(true, this.prepareClientFundInputs(tokenizedBusinessUser,businessAzimutClient), null).getDataList();
		
		if(businessAzimutClient!=null&&businessAzimutClient.getFundId()!=null)
		{
			BusinessClientFund businessClientFund=new  BusinessClientFund();
			businessClientFund=this.getClientFundsMapper.wrapBaseBusinessEntity(false, this.prepareClientFundInputs(tokenizedBusinessUser,businessAzimutClient), null).getData();
			businessClientFunds.add(businessClientFund);	
		}
		
		/*this.getFundPricesMapper.wrapBaseBusinessEntity(true, this.prepareFundPriceSearchInputs(businessAzimutClient), null).getDataList();*/
		
		Long [] teacomputerFundIds=this.populateTheArrayOfFundIds(businessClientFunds);
		
		if(this.arrayUtility.isArrayPopulated(teacomputerFundIds))
		{
			businessFundPrices= this.fundPriceMapper.convertBasicListToBusinessList(this.fundService.getAllRelevantFundPrices(teacomputerFundIds));
		}
		
		Map <String,Object> clientFundPriceMap= new HashMap<String,Object>();
		clientFundPriceMap.put(BusinessClientFund.class.getName(), businessClientFunds);
		clientFundPriceMap.put(BusinessFundPrice.class.getName(), businessFundPrices);
		return clientFundPriceMap;
	}
	
	private AzimutAccount prepareAccountAdditionInputs(AzimutAccount azimutAccount,BusinessUser businessUser) throws BusinessException 
	{
		azimutAccount.setCustomerNameEn(businessUser.getFirstName()+businessUser.getLastName());
		azimutAccount.setCustomerNameAr(businessUser.getFirstName()+businessUser.getLastName());
		azimutAccount.setIdType(this.getAzimutUserTypeId(businessUser));
		azimutAccount.setUserId(businessUser.getUserId());
		azimutAccount.setIdMaturityDate(businessUser.getDateOfIdExpiry());
		azimutAccount.setBirthDate(businessUser.getDateOfBirth());
		azimutAccount.setEmail(businessUser.getEmailAddress());
		azimutAccount.setPhoneNumber(StringUtility.ZERO+businessUser.getPhoneNumber());
		azimutAccount.setSexId(businessUser.getGenderId());
		
		
		AzimutAccount businessUserAzimutAccount=businessUser.getAzimutAccount();
		if(businessUserAzimutAccount!=null)
		{
				azimutAccount.setAddressAr(businessUserAzimutAccount.getAddressAr());
				
				azimutAccount.setAddressEn(businessUserAzimutAccount.getAddressEn());
			
				azimutAccount.setCityId(businessUserAzimutAccount.getCityId());
			
				azimutAccount.setCountryId(businessUserAzimutAccount.getCountryId());
			
				azimutAccount.setiDIssueCountryId(businessUserAzimutAccount.getiDIssueCountryId());
			
				azimutAccount.setiDIssueCityId(businessUserAzimutAccount.getiDIssueCityId());
			
				azimutAccount.setClientAML(businessUserAzimutAccount.getClientAML());
			
				azimutAccount.setOccupation(businessUserAzimutAccount.getOccupation());
				
				azimutAccount.setNationalityId(businessUserAzimutAccount.getNationalityId());
			
		}
		

		this.logger.info("Azimut Account:::"+azimutAccount.toString());
		
		return azimutAccount;
	}

	BusinessTransaction prepareTransactionSearchInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser)
	{
		BusinessTransaction searchBusinessTransaction=new BusinessTransaction();
		searchBusinessTransaction.setSearchFromDate(businessAzimutClient.getSearchFromDate());
		searchBusinessTransaction.setSearchToDate(businessAzimutClient.getSearchToDate());
		searchBusinessTransaction.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessTransaction.setAzIdType(/*businessAzimutClient.getAzIdType()*/this.getAzimutUserTypeId(tokenizedBusinessUser));
		this.logger.info("SearchBusinessTransaction:"+ searchBusinessTransaction.toString());
		return searchBusinessTransaction;
	}
	
	BusinessClientCashBalance preparClientCashBalanceInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser)
	{
		BusinessClientCashBalance searchBusinessClientCashBalance=new BusinessClientCashBalance();
		searchBusinessClientCashBalance.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientCashBalance.setAzIdType(/*businessAzimutClient.getAzIdType()*/this.getAzimutUserTypeId(tokenizedBusinessUser));
		this.logger.info("SearchBusinessClientCashBalance:"+ searchBusinessClientCashBalance.toString());
		return searchBusinessClientCashBalance;
		
	}
	BusinessClientBankAccountDetails prepareClientBankAccountDetailsInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,boolean isList)
	{
		BusinessClientBankAccountDetails searchBusinessClientBankAccountDetails=new BusinessClientBankAccountDetails();
		searchBusinessClientBankAccountDetails.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientBankAccountDetails.setAzIdType(/*businessAzimutClient.getAzIdType()*/this.getAzimutUserTypeId(tokenizedBusinessUser));	
		searchBusinessClientBankAccountDetails.setAccountId(businessAzimutClient.getAccountId());
		return searchBusinessClientBankAccountDetails;
	}
	
	BusinessClientFund prepareClientFundInputs(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient)
	{
		BusinessClientFund searchBusinessClientFund=new BusinessClientFund();
		searchBusinessClientFund.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientFund.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		searchBusinessClientFund.setFundId(businessAzimutClient.getFundId());
		return searchBusinessClientFund;
	}
	BusinessFundPrice prepareFundPriceSearchInputs(BusinessAzimutClient businessAzimutClient)
	{
		BusinessFundPrice searchBusinessFundPrice=new BusinessFundPrice();
		searchBusinessFundPrice.setSearchFromDate(businessAzimutClient.getSearchFromDate());
		searchBusinessFundPrice.setSearchToDate(businessAzimutClient.getSearchToDate());
		this.logger.info("SearchBusinessFundPrice:"+ searchBusinessFundPrice.toString());
		return searchBusinessFundPrice;
	}
	
	public BusinessAzimutClient beautifyBalanceAndTransactionsBusinessAzimutClient(BusinessAzimutClient businessAzimutClient)
	{
		double pendingAmount=0;
		if(businessAzimutClient!=null &&listUtility.isListPopulated(businessAzimutClient.getTransactions()))
			{
				for(BusinessTransaction  businessTransaction:businessAzimutClient.getTransactions())
				{
					if(businessTransaction!=null)
					{
						this.logger.info("business Transaction:::"+ businessTransaction);
						
						businessTransaction.setStatus(businessTransaction.getTransactionStatus()!=null?businessTransaction.getTransactionStatus().getStatusId():null);
						businessTransaction.setType(businessTransaction.getOrderType()!=null?businessTransaction.getOrderType().getTypeId():null);
					
						if(businessTransaction.getTransactionStatus()!=null&&businessTransaction.getTransactionStatus().getStatus().equals(TransactionStatus.PENDING.getStatus()))
							{
								pendingAmount=pendingAmount+businessTransaction.getAmount();
							}
					}
				}
				businessAzimutClient.setTotalPendingAmount(pendingAmount);
				
				boolean sorting = true;
				if(StringUtility.isStringPopulated(businessAzimutClient.getSorting())&&businessAzimutClient.getSorting().equals(Sorting.ASC.getOrder()))
				sorting=true;
				
				else if(!StringUtility.isStringPopulated(businessAzimutClient.getSorting())||StringUtility.isStringPopulated(businessAzimutClient.getSorting())&&businessAzimutClient.getSorting().equals(Sorting.DESC.getOrder()))
				sorting=false;	
					
				this.logger.info("Sorting:::"+sorting);
				Collections.sort(businessAzimutClient.getTransactions(),sorting ? this.sortCompare: Collections.reverseOrder());
				int index = sorting ? businessAzimutClient.getTransactions().size() : 0;
				this.logger.info("Index:::"+index);
				businessAzimutClient.setLastTransactionDate(businessAzimutClient.getTransactions().get(index).getTrxDate());
			}
		if(businessAzimutClient!=null&&businessAzimutClient.getBusinessClientCashBalance()!=null)
		{
			BusinessClientCashBalance businessClientCashBalance=businessAzimutClient.getBusinessClientCashBalance();
			businessAzimutClient.setBalance(businessClientCashBalance.getBalance());
			businessAzimutClient.setBalanceCurrency(businessClientCashBalance.getCurrencyName());
			businessAzimutClient.settPACurrency(businessClientCashBalance.getCurrencyName());
			businessAzimutClient.setBusinessClientCashBalance(null);
		}
		
		return businessAzimutClient;
	}
	
  AzimutAccount	prepareAccountRetrievalInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser)
  {
	  AzimutAccount azimutAccount=new AzimutAccount();
	  azimutAccount.setPhoneNumber(businessAzimutClient.getUserPhone());
	  azimutAccount.setUserId(businessAzimutClient.getUserId());
	  azimutAccount.setIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
	  return azimutAccount;
  }	
  
  Long getAzimutUserTypeId(BusinessUser businessUser)
  {
	  try 
	  {
		  return businessUser.getAzimutIdTypeId();
	  }
	  catch(Exception exception)
	  {
		  this.exceptionHandler.getNullIfNonExistent(exception);
		  return null;
	  }
  }
  
  
  BusinessFundTransaction prepareBusinessBusinessFundTransactionRetrievalInputs(BusinessUser tokenizedBusinessUser)
  {
	  BusinessFundTransaction searchBusinessFundTransaction=new BusinessFundTransaction();
	  searchBusinessFundTransaction.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
	  searchBusinessFundTransaction.setAzId(tokenizedBusinessUser.getUserId());
	  return searchBusinessFundTransaction;
  }
  
  private void beautifyBusinessClientFunds(BusinessAzimutClient responseBusinessAzimutClient,List<BusinessClientFund> businessClientFunds,List<BusinessFundPrice> businessFundPrices,BusinessClientCashBalance businessClientCashBalance)
  {
	  
	  double totalPosition=0d;

	  if(this.clientFundListUtility.isListPopulated(businessClientFunds)&&this.fundPricesListUtility.isListPopulated(businessFundPrices))
	  {
		  
		  double totalFundPosition=0d;
		  for(BusinessClientFund businessClientFund:businessClientFunds)
		  {
			  for(BusinessFundPrice businessFundPrice:businessFundPrices)
			  {
				  if(NumberUtility.areLongValuesMatching(businessClientFund.getFundId(), businessFundPrice.getTeacomputerId()))
				  {
					  businessClientFund.setLastPriceUpdateDate(businessFundPrice.getPriceDate());
					  businessClientFund.setLogo(businessFundPrice.getLogo());
					  
					  if(businessClientFund.getQuantity()!=null&&businessFundPrice.getTradePrice()!=null&&businessClientFund.getCurrencyRate()!=null)
					  {
						  totalFundPosition=totalFundPosition+(businessClientFund.getQuantity().doubleValue()*businessFundPrice.getTradePrice().doubleValue()*businessClientFund.getCurrencyRate());
					  }
					  
				  }
			  }
			  
		  }
		  totalPosition=totalFundPosition;
	  }
	  
	  if(businessClientCashBalance!=null&businessClientCashBalance.getBalance()!=null)
		{
			responseBusinessAzimutClient.setBalance(businessClientCashBalance.getBalance());
			totalPosition=totalPosition+businessClientCashBalance.getBalance();			
		}
	  
	 
	  responseBusinessAzimutClient.setTotalPosition(totalPosition);
	  responseBusinessAzimutClient.setBusinessClientFunds(businessClientFunds);
  }
  
  private void beautifyBusinessClientFunds(BusinessAzimutClient responseBusinessAzimutClient,List<BusinessClientFund> businessClientFunds,List<BusinessFundPrice> businessFundPrices,BusinessClientCashBalance businessClientCashBalance,List<BusinessFundTransaction> businessFundTransactions)
  {
	 
	 if(this.clientFundListUtility.isListPopulated(businessClientFunds)&&businessClientFunds.size()<2)
	 {
		 for(BusinessClientFund businessClientFund:businessClientFunds)
		 {
			 businessClientFund.setFundTransactions(businessFundTransactions);
		 }
	 }
	 this.beautifyBusinessClientFunds(responseBusinessAzimutClient, businessClientFunds, businessFundPrices, businessClientCashBalance);
	 
  }
  Long [] populateTheArrayOfFundIds(List<BusinessClientFund> businessClientFunds)
  {
	  if(this.clientFundListUtility.isListPopulated(businessClientFunds))
		{
		  Long [] fundIds=new Long [businessClientFunds.size()];
		  
		  for(int i=0;i<businessClientFunds.size();i++)
		  {
			  fundIds[i]=businessClientFunds.get(i).getFundId().longValue();
			  
		  }
		  
		  return fundIds;
		}
	  return null;
  }
  
  
}
