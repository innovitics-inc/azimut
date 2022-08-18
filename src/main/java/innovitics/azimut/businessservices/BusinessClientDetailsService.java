package innovitics.azimut.businessservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BusinessTransaction;
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
import innovitics.azimut.rest.mappers.GetTransactionsMapper;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.services.user.AzimutDataLookUpService;
import innovitics.azimut.utilities.businessutilities.SortCompare;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.crosslayerenums.TransactionStatus;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.AzimutDataLookupUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
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

@Autowired ListUtility<BusinessTransaction>listUtility;
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
		
		for(BusinessClientBankAccountDetails businessClientBankAccountDetails:businessAzimutClient.getClientBankAccounts())
		{			
			this.validation.validate(businessClientBankAccountDetails, saveClientBankAccountTemporarily, BusinessClientBankAccountDetails.class.getName());
		}
		
		try 
		{	
			this.teaComputerService.deleteClientBankAccounts(tokenizedBusinessUser.getId());
			this.azimutDataLookupUtility.saveAzimutClientBankAccountData(tokenizedBusinessUser,businessAzimutClient.getClientBankAccounts());
			tokenizedBusinessUser.setUserStep(UserStep.BANK_REFERENCES_SHOW.getStepId());
			this.businessUserService.editUser(tokenizedBusinessUser);
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
		searchBusinessClientBankAccountDetails.setBankId(businessAzimutClient.getBankId());
		return searchBusinessClientBankAccountDetails;
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
  
  
  
}
