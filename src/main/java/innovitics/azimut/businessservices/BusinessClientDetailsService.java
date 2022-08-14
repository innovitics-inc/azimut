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
import innovitics.azimut.models.user.AzimutDataLookup;
import innovitics.azimut.models.user.AzimutEntity;
import innovitics.azimut.rest.mappers.AddAccountMapper;
import innovitics.azimut.rest.mappers.CheckAccountMapper;
import innovitics.azimut.rest.mappers.GetClientBalanceMapper;
import innovitics.azimut.rest.mappers.GetClientBankAccountsMapper;
import innovitics.azimut.rest.mappers.GetTransactionsMapper;
import innovitics.azimut.rest.mappers.LookUpMapper;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.services.user.AzimutDataLookUpService;
import innovitics.azimut.utilities.businessutilities.SortCompare;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.crosslayerenums.TransactionStatus;
import innovitics.azimut.utilities.datautilities.AzimutDataLookupUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.validations.validators.azimutclient.GetBalanceAndTransactions;
@Service
public class BusinessClientDetailsService extends AbstractBusinessService<BusinessAzimutClient> {
@Autowired GetClientBalanceMapper getClientBalanceMapper;
@Autowired GetTransactionsMapper getTransactionsMapper;
@Autowired GetClientBankAccountsMapper getClientBankAccountsMapper;
@Autowired CheckAccountMapper checkAccountMapper;
@Autowired ListUtility<BusinessTransaction>listUtility;
@Autowired SortCompare sortCompare;
@Autowired GetBalanceAndTransactions getBalanceAndTransactions;
@Autowired AddAccountMapper addAccountMapper;
@Autowired AzimutDataLookUpService azimutDataLookUpService;
@Autowired AzimutDataLookupUtility azimutDataLookupUtility;
@Autowired TeaComputerService teaComputerService;
@Autowired LookUpMapper lookUpMapper;
@Autowired BusinessUserService businessUserService;


	public BusinessAzimutClient getBalanceAndTransactions(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		this.validation.validate(businessAzimutClient, getBalanceAndTransactions, BusinessAzimutClient.class.getName());

		try 
		{
				responseBusinessAzimutClient.setBusinessTransactions(getTransactionsMapper.wrapBaseBusinessEntity(true,this.prepareTransactionSearchInputs(businessAzimutClient), null).getDataList());
				responseBusinessAzimutClient.setBusinessClientCashBalance(getClientBalanceMapper.wrapBaseBusinessEntity(false,this.preparClientCashBalanceInputs(businessAzimutClient), null).getData());
				
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
			responseBusinessAzimutClient.setBankList(getClientBankAccountsMapper.wrapBaseBusinessEntity(isList, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,isList), null).getDataList());
			else if(!isList)
			responseBusinessAzimutClient.setBankAccountDetails(getClientBankAccountsMapper.wrapBaseBusinessEntity(isList, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,isList), null).getData());	
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
	
	public BusinessAzimutClient checkAccountAtTeaComputers(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		try 
		{			
			responseBusinessAzimutClient.setAzimutAccounts(this.checkAccountMapper.wrapBaseBusinessEntity(true, this.prepareAccountRetrievalInputs(businessAzimutClient), null).getDataList());	
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
	public BusinessAzimutClient getAzimutLookupData(BusinessAzimutDataLookup businessAzimutDataLookup ,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		businessAzimutClient.setLookupData(this.azimutDataLookupUtility.getLookups(businessAzimutDataLookup));
		return businessAzimutClient;
	}
	
	public BusinessAzimutClient getTeaComputersLookupData(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		try 
		{		

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
	
	
	private AzimutAccount prepareAccountAdditionInputs(AzimutAccount azimutAccount,BusinessUser businessUser) throws BusinessException 
	{
		azimutAccount.setCustomerNameEn(businessUser.getFirstName()+businessUser.getLastName());
		azimutAccount.setCustomerNameAr(businessUser.getFirstName()+businessUser.getLastName());
		azimutAccount.setIdType(businessUser.getIdType());
		azimutAccount.setUserId(businessUser.getUserId());
		azimutAccount.setIdMaturityDate(businessUser.getDateOfIdExpiry());
		azimutAccount.setBirthDate(businessUser.getDateOfBirth());
		azimutAccount.setEmail(businessUser.getEmailAddress());
		azimutAccount.setPhoneNumber("0"+businessUser.getPhoneNumber());
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

	BusinessTransaction prepareTransactionSearchInputs(BusinessAzimutClient businessAzimutClient)
	{
		BusinessTransaction searchBusinessTransaction=new BusinessTransaction();
		searchBusinessTransaction.setSearchFromDate(businessAzimutClient.getSearchFromDate());
		searchBusinessTransaction.setSearchToDate(businessAzimutClient.getSearchToDate());
		searchBusinessTransaction.setAzId(businessAzimutClient.getAzId());
		searchBusinessTransaction.setAzIdType(businessAzimutClient.getAzIdType());
		this.logger.info("SearchBusinessTransaction:"+ searchBusinessTransaction.toString());
		return searchBusinessTransaction;
	}
	
	BusinessClientCashBalance preparClientCashBalanceInputs(BusinessAzimutClient businessAzimutClient)
	{
		BusinessClientCashBalance searchBusinessClientCashBalance=new BusinessClientCashBalance();
		searchBusinessClientCashBalance.setAzId(/*businessAzimutClient.getSearchIdNumber()*/"28206092102184");
		searchBusinessClientCashBalance.setAzIdType(businessAzimutClient.getAzIdType());
		this.logger.info("SearchBusinessClientCashBalance:"+ searchBusinessClientCashBalance.toString());
		return searchBusinessClientCashBalance;
		
	}
	BusinessClientBankAccountDetails prepareClientBankAccountDetailsInputs(BusinessAzimutClient businessAzimutClient,boolean isList)
	{
		BusinessClientBankAccountDetails searchBusinessClientBankAccountDetails=new BusinessClientBankAccountDetails();
		searchBusinessClientBankAccountDetails.setAzId(businessAzimutClient.getAzId());
		searchBusinessClientBankAccountDetails.setAzIdType(businessAzimutClient.getAzIdType());	
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
	
  AzimutAccount	prepareAccountRetrievalInputs(BusinessAzimutClient businessAzimutClient)
  {
	  AzimutAccount azimutAccount=new AzimutAccount();
	  azimutAccount.setPhoneNumber(businessAzimutClient.getUserPhone());
	  azimutAccount.setUserId(businessAzimutClient.getUserId());
	  azimutAccount.setIdType(businessAzimutClient.getIdType());
	  return azimutAccount;
  }	
  
  
  
  
}
