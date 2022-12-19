package innovitics.azimut.businessservices;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import innovitics.azimut.businessmodels.user.BusinessCompanyBankAccount;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.EportfolioDetail;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.services.FundService;
import innovitics.azimut.services.azimut.AzimutDataLookUpService;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.utilities.businessutilities.AzimutDataLookupUtility;
import innovitics.azimut.utilities.businessutilities.CashTransactionSortCompare;
import innovitics.azimut.utilities.businessutilities.FundTransactionSortCompare;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.crosslayerenums.BankAccountStatus;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.crosslayerenums.OrderStatus;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.FundMapper;
import innovitics.azimut.utilities.mapping.FundPriceMapper;
import innovitics.azimut.validations.validators.azimutclient.GetAzimutEntityLookup;
import innovitics.azimut.validations.validators.azimutclient.GetBalanceAndTransactions;
import innovitics.azimut.validations.validators.azimutclient.RemoveClientBankAccount;
import innovitics.azimut.validations.validators.azimutclient.SaveClientBankAccountTemporarily;
import innovitics.azimut.validations.validators.azimutclient.SaveClientBankAccountsTemporarily;
@SuppressWarnings("unchecked")
@Service
public class BusinessClientDetailsService extends AbstractBusinessService<BusinessAzimutClient> {


@Autowired ListUtility<BusinessTransaction> businessTransactionListUtility;
@Autowired ListUtility<BusinessFundPrice> fundPricesListUtility;
@Autowired ListUtility<BusinessClientFund> clientFundListUtility;
@Autowired ListUtility<BusinessClientCashBalance> clientCashBalanceListUtility;
@Autowired ListUtility<BusinessClientBankAccountDetails> businessClientBankAccountDetailsListUtility;
@Autowired CashTransactionSortCompare cashTransactionSortCompare;
@Autowired FundTransactionSortCompare fundTransactionSortCompare;
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
@Autowired FundMapper fundMapper;
@Autowired ListUtility<EportfolioDetail> eportfolioDetailListUtility;
@Autowired ListUtility<BusinessCompanyBankAccount> companyBankAccountListUtility;


	public BusinessAzimutClient getBalanceAndFundOwnership(BusinessUser tokenizedBusinessUser,String language,BusinessAzimutClient businessAzimutClient) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validate(businessAzimutClient, getBalanceAndTransactions, BusinessAzimutClient.class.getName());
		
		responseBusinessAzimutClient=this.getBalanceAndTransactions(businessAzimutClient, tokenizedBusinessUser);
		this.getFundOwnership(businessAzimutClient, tokenizedBusinessUser, responseBusinessAzimutClient);		
		//this.getBalance(businessAzimutClient, tokenizedBusinessUser, responseBusinessAzimutClient);
		
		return responseBusinessAzimutClient;
		
	}
	public BusinessAzimutClient getFundOwnership(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,BusinessAzimutClient responseBusinessAzimutClient) throws BusinessException,IntegrationException
	{
		
		List<BusinessClientFund> businessClientFunds=new ArrayList<BusinessClientFund>();
		businessClientFunds=this.getClientFundsOrFund(tokenizedBusinessUser, businessAzimutClient,false).getBusinessClientFunds();
		
		if(clientFundListUtility.sizeIsOne(businessClientFunds))
		{
			responseBusinessAzimutClient.setOwned(true);
			responseBusinessAzimutClient.setBusinessClientFunds(businessClientFunds);
		}
		else
		{
			responseBusinessAzimutClient.setOwned(false);
		}
		
		
		return responseBusinessAzimutClient;
	}
	

	public BusinessAzimutClient getBalanceAndTransactions(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		this.logger.info("");
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		this.validation.validate(businessAzimutClient, getBalanceAndTransactions, BusinessAzimutClient.class.getName());
		responseBusinessAzimutClient.setSorting(businessAzimutClient.getSorting());
		this.getBalance(businessAzimutClient, tokenizedBusinessUser, responseBusinessAzimutClient);
		this.getTransactions(businessAzimutClient, tokenizedBusinessUser, responseBusinessAzimutClient);	
		return this.beautifyBalanceAndTransactionsBusinessAzimutClient(responseBusinessAzimutClient);
	}
	
	public BusinessAzimutClient getBalance(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,BusinessAzimutClient responseBusinessAzimutClient) throws BusinessException,IntegrationException
	{
		
		List<BusinessClientCashBalance> businessClientCashBalances=new ArrayList<BusinessClientCashBalance>();
		List<BusinessClientCashBalance> businessClientCashBalancesWithCurrencies=new ArrayList<BusinessClientCashBalance>();
		if(tokenizedBusinessUser!=null&&StringUtility.isStringPopulated(tokenizedBusinessUser.getUserId()))
		{
		
			try 
			{				
				businessClientCashBalances=restContract.getDataList(restContract.getClientBalanceMapper,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null);
				if(clientCashBalanceListUtility.isListEmptyOrNull(businessClientCashBalances))
				{
					this.logger.info("Empty List");
					businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.EGYPTIAN_POUND));
					businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.US_DOLLAR));
				}
				else if(clientCashBalanceListUtility.sizeIsOne(businessClientCashBalances))
				{
					this.logger.info("One Element List");
					BusinessClientCashBalance existingBusinessClientCashBalance=businessClientCashBalances.get(0);
					if(existingBusinessClientCashBalance!=null&&NumberUtility.areLongValuesMatching(existingBusinessClientCashBalance.getCurrencyID().longValue(), CurrencyType.EGYPTIAN_POUND.getTypeId()))
					{
						businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.US_DOLLAR));
					}
					else if(existingBusinessClientCashBalance!=null&&NumberUtility.areLongValuesMatching(existingBusinessClientCashBalance.getCurrencyID().longValue(), CurrencyType.US_DOLLAR.getTypeId()))
					{
						businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.EGYPTIAN_POUND));
					}
				}

			}
			catch(Exception exception)
			{
				businessClientCashBalances=clientCashBalanceListUtility.handleExceptionAndReturnEmptyList(exception);
				businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.EGYPTIAN_POUND));
				businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.US_DOLLAR));
			}
		
		
			if(clientCashBalanceListUtility.isListPopulated(businessClientCashBalances)&&businessAzimutClient.getCurrencyId()!=null)
			{
				for(BusinessClientCashBalance businessClientCashBalance: businessClientCashBalances)
				{
					if(businessClientCashBalance!=null&&NumberUtility.areLongValuesMatching(businessClientCashBalance.getCurrencyID(),businessAzimutClient.getCurrencyId()))
					{
						businessClientCashBalancesWithCurrencies.add(businessClientCashBalance);
					}
				}
				responseBusinessAzimutClient.setBusinessClientCashBalances(businessClientCashBalancesWithCurrencies);
			}
			else
			{
				responseBusinessAzimutClient.setBusinessClientCashBalances(businessClientCashBalances);	
			}
		
		}
		else
		{
			businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.EGYPTIAN_POUND));
			businessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.US_DOLLAR));
			if(clientCashBalanceListUtility.isListPopulated(businessClientCashBalances)&&businessAzimutClient.getCurrencyId()!=null)
			{
				for(BusinessClientCashBalance businessClientCashBalance: businessClientCashBalances)
				{
					if(businessClientCashBalance!=null&&NumberUtility.areLongValuesMatching(businessClientCashBalance.getCurrencyID(),businessAzimutClient.getCurrencyId()))
					{
						businessClientCashBalancesWithCurrencies.add(businessClientCashBalance);
					}
				}
				responseBusinessAzimutClient.setBusinessClientCashBalances(businessClientCashBalancesWithCurrencies);
			}
			else
			{
				responseBusinessAzimutClient.setBusinessClientCashBalances(businessClientCashBalances);	
			}
				
		}
		
		return responseBusinessAzimutClient;

	}
	public BusinessAzimutClient getTransactions(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,BusinessAzimutClient responseBusinessAzimutClient) throws BusinessException,IntegrationException
	{
		if(tokenizedBusinessUser!=null&&StringUtility.isStringPopulated(tokenizedBusinessUser.getUserId()))
		{
			try 
			{	
				List<BusinessTransaction> businessTransactions= (List<BusinessTransaction>) this.restContract.getDataList(this.restContract.getTransactionsMapper, this.prepareTransactionSearchInputs(businessAzimutClient,tokenizedBusinessUser), null);
				
				List<BusinessTransaction> businessPaymentTransactions=this.getPaymentTransactions(tokenizedBusinessUser);
				
				if(businessTransactionListUtility.isListPopulated(businessTransactions)&&businessTransactionListUtility.isListPopulated(businessPaymentTransactions))
				{
					businessTransactions.addAll(businessPaymentTransactions);
					responseBusinessAzimutClient.setBusinessTransactions(businessTransactions);
				}
				else if(businessTransactionListUtility.isListPopulated(businessTransactions)&&businessTransactionListUtility.isListEmptyOrNull(businessPaymentTransactions))
				{
					responseBusinessAzimutClient.setBusinessTransactions(businessTransactions);
				}
				else if(businessTransactionListUtility.isListEmptyOrNull(businessTransactions)&&businessTransactionListUtility.isListPopulated(businessPaymentTransactions))
				{
					responseBusinessAzimutClient.setBusinessTransactions(businessPaymentTransactions);
				}
				else if(businessTransactionListUtility.isListEmptyOrNull(businessTransactions)&&businessTransactionListUtility.isListEmptyOrNull(businessPaymentTransactions))
				{
					responseBusinessAzimutClient.setLastTransactionDate("No transactions yet.");
					responseBusinessAzimutClient.setBusinessTransactions(new ArrayList<BusinessTransaction>());
				}						
			}
			catch(Exception exception)
			{
				responseBusinessAzimutClient.setLastTransactionDate("No transactions yet.");
				responseBusinessAzimutClient.setBusinessTransactions(businessTransactionListUtility.handleExceptionAndReturnEmptyList(exception));
			}
		}
		else
		{
			responseBusinessAzimutClient.setLastTransactionDate("No transactions yet.");
			responseBusinessAzimutClient.setBusinessTransactions(new ArrayList<BusinessTransaction>());
			
		}
		return responseBusinessAzimutClient;
	}
	
	public List<BusinessTransaction> getPaymentTransactions(BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		List<BusinessTransaction> businessTransactions=new ArrayList<BusinessTransaction>();
		if(tokenizedBusinessUser!=null&&StringUtility.isStringPopulated(tokenizedBusinessUser.getUserId()))
		{
			businessTransactions=this.paymentTransactionUtility.getUserPaymentTransactionsAndConvertThemToBusinessTransactions(tokenizedBusinessUser, PaymentGateway.PAYTABS);	
		}
		
		return businessTransactions;
	}
	
	public BusinessAzimutClient getBankAccountsWithDetails(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,boolean isList,Long accountId) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		if(tokenizedBusinessUser!=null&&StringUtility.isStringPopulated(tokenizedBusinessUser.getUserId()))
		{	
			List<BusinessClientBankAccountDetails> totalBankAccountsWithCurrencies=new ArrayList<BusinessClientBankAccountDetails>();
			List<BusinessClientBankAccountDetails> totalBankAccounts=new ArrayList<BusinessClientBankAccountDetails>();
			List<BusinessClientBankAccountDetails> teacomputersBankAccounts=new ArrayList<BusinessClientBankAccountDetails>();
			try 
			{			
				if(isList)
				{
			
					if(BooleanUtility.isFalse(businessAzimutClient.getIsActive()))
					{
						BusinessClientBankAccountDetails [] localClientTeacomputersBankAccounts=this.azimutDataLookupUtility.getClientBankAccountData(tokenizedBusinessUser);
				
						if(arrayUtility.isArrayPopulated(localClientTeacomputersBankAccounts))
						{	
							totalBankAccounts.addAll(Arrays.asList(localClientTeacomputersBankAccounts));
						}
						teacomputersBankAccounts=this.restContract.getDataList(this.restContract.getClientBankAccountsMapper, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null) ;
						totalBankAccounts.addAll(teacomputersBankAccounts);
					}
					else
					{
						teacomputersBankAccounts=this.restContract.getDataList(this.restContract.getClientBankAccountsMapper, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null) ;		
						for(BusinessClientBankAccountDetails businessClientBankAccountDetails:teacomputersBankAccounts)
						{
							if(businessClientBankAccountDetails!=null&&NumberUtility.areLongValuesMatching((Long.valueOf(businessClientBankAccountDetails.getAccountStatus().intValue())),BankAccountStatus.ACTIVE.getStatusId()))
								totalBankAccounts.add(businessClientBankAccountDetails);	
						}
						
					}
					//teacomputersBankAccounts=this.restManager.getClientBankAccountsMapper.wrapBaseBusinessEntity(isList, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null).getDataList();		
				}
				else if(!isList)
				{
					if(BooleanUtility.isTrue(businessAzimutClient.getIsLocal()))
					{
						if(accountId!=null)
						{
							responseBusinessAzimutClient.setBankAccountDetails(this.azimutDataLookupUtility.getKYCClientBankAccountData(tokenizedBusinessUser, accountId));
						}
						else
						{
							responseBusinessAzimutClient.setBankAccountDetails(this.azimutDataLookupUtility.getClientBankAccountData(tokenizedBusinessUser)[0]);
						}
					}	
					else
					{
					//responseBusinessAzimutClient.setBankAccountDetails(this.restManager.getClientBankAccountsMapper.wrapBaseBusinessEntity(isList, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null).getData());
						responseBusinessAzimutClient.setBankAccountDetails((BusinessClientBankAccountDetails) this.restContract.getDataList(this.restContract.getClientBankAccountsMapper, this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,isList), null).get(0));
					}
				}	
			}
			catch(Exception exception)
			{
				if(!this.exceptionHandler.checkIfIntegrationExceptinWithSpecificErrorCode(exception,ErrorCode.INVALID_CLIENT)&&BooleanUtility.isFalse(tokenizedBusinessUser.getIsVerified()))
				{
					throw this.exceptionHandler.handleException(exception);
				}
			}
		
			if(businessClientBankAccountDetailsListUtility.isListPopulated(totalBankAccounts)&&businessAzimutClient.getCurrencyId()!=null)
			{
				for(BusinessClientBankAccountDetails businessClientBankAccountDetails:totalBankAccounts)
				{
					if(businessClientBankAccountDetails!=null&&NumberUtility.areLongValuesMatching(businessClientBankAccountDetails.getCurrencyId(), businessAzimutClient.getCurrencyId()))
					{
						totalBankAccountsWithCurrencies.add(businessClientBankAccountDetails);
					}
				}
				responseBusinessAzimutClient.setBankList(totalBankAccountsWithCurrencies);
			}
			else
			{
				responseBusinessAzimutClient.setBankList(totalBankAccounts);	
			}
		}
		else
		{
			responseBusinessAzimutClient.setBankList(new ArrayList<BusinessClientBankAccountDetails>());
		}
		return responseBusinessAzimutClient;
	}
	
	public BusinessAzimutClient holdClientBankAccount(BusinessUser tokenizedBusinessUser, BusinessAzimutClient businessAzimutClient) throws BusinessException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		try 
		{			
			if(BooleanUtility.isFalse(businessAzimutClient.getIsLocal()))
			{
				//responseBusinessAzimutClient=this.restManager.holdClientBankAccountMapper.wrapBaseBusinessEntity(false, this.prepareAccountHoldingInputs(businessAzimutClient,tokenizedBusinessUser), null).getData();
				responseBusinessAzimutClient=(BusinessAzimutClient) this.restContract.getData(this.restContract.holdClientBankAccountMapper, this.prepareAccountHoldingInputs(businessAzimutClient,tokenizedBusinessUser), null);
			}
			else
			{
				this.azimutDataLookupUtility.removeClientBankAccount(businessAzimutClient.getAccountId());
			}
		}
		catch(Exception exception)
		{
	
			throw this.exceptionHandler.handleException(exception);
		}

		return responseBusinessAzimutClient;
	}
	private BusinessAzimutClient prepareAccountHoldingInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException 
	{
		BusinessAzimutClient searchAzimutClient=new BusinessAzimutClient();
		searchAzimutClient.setAccountId(businessAzimutClient.getAccountId());
		searchAzimutClient.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		searchAzimutClient.setAzId(tokenizedBusinessUser.getUserId());
		
		return searchAzimutClient;
	
	}
	
	public BusinessAzimutClient checkAccountAtTeaComputers(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		try 
		{			
			//responseBusinessAzimutClient.setAzimutAccounts(this.restManager.checkAccountMapper.wrapBaseBusinessEntity(true, this.prepareAccountRetrievalInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList());	
			responseBusinessAzimutClient.setAzimutAccounts((List<AzimutAccount>) this.restContract.getData(this.restContract.checkAccountMapper,this.prepareAccountRetrievalInputs(businessAzimutClient,tokenizedBusinessUser), null));
		}
		catch(Exception exception)
		{
	
			throw this.exceptionHandler.handleException(exception);
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
			throw this.exceptionHandler.handleException(exception);	
		}

		return responseBusinessAzimutClient;
	}
	public BusinessAzimutClient addAccountAtTeaComputers(AzimutAccount azimutAccount,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(azimutAccount.getId(), tokenizedBusinessUser);
		try 
		{			
			//this.restManager.addAccountMapper.wrapBaseBusinessEntity(false, this.prepareAccountAdditionInputs(azimutAccount,tokenizedBusinessUser), null).getData();
			
			this.restContract.getData(this.restContract.addAccountMapper, this.prepareAccountAdditionInputs(azimutAccount,tokenizedBusinessUser), null);
			
			tokenizedBusinessUser.setIsVerified(true);
			
			this.businessUserService.editUser(tokenizedBusinessUser);
			
			BusinessClientBankAccountDetails[] businessClientBankAccountDetailsArray= this.azimutDataLookupUtility.getClientBankAccountData(tokenizedBusinessUser);
					
			if(arrayUtility.isArrayPopulated(businessClientBankAccountDetailsArray))
			{
				for(BusinessClientBankAccountDetails businessClientBankAccountDetails:businessClientBankAccountDetailsArray)
				{
					businessClientBankAccountDetails.setAzId(tokenizedBusinessUser.getUserId());
					businessClientBankAccountDetails.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
				}
						this.restContract.loopConsumption(this.restContract.addClientBankAccountMapper, this.arrayUtility.generateObjectListFromObjectArray(businessClientBankAccountDetailsArray));
			 }
			
			this.teaComputerService.deleteClientBankAccounts(tokenizedBusinessUser.getId());
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);		
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
		
		if(BooleanUtility.isFalse(businessAzimutClient.getPersist()))
		{
			this.teaComputerService.deleteClientBankAccounts(tokenizedBusinessUser.getId());
			this.validation.validateUserKYCCompletion(tokenizedBusinessUser);
		}
	
		
		this.validation.validate(businessAzimutClient, saveClientBankAccountsTemporarily, BusinessAzimutClient.class.getName());
		BusinessAzimutClient responseBusinessAzimutClient= new BusinessAzimutClient();
		for(BusinessClientBankAccountDetails businessClientBankAccountDetails:businessAzimutClient.getClientBankAccounts())
		{			
			this.validation.validate(businessClientBankAccountDetails, saveClientBankAccountTemporarily, BusinessClientBankAccountDetails.class.getName());
		}
		
		try 
		{					
			this.azimutDataLookupUtility.saveAzimutClientBankAccountData(tokenizedBusinessUser,businessAzimutClient);
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
			//this.restManager.addClientBankAccountMapper.wrapBaseBusinessEntity(false,request, null);
			this.restContract.getData(this.restContract.addClientBankAccountMapper, request, null);
		}
		catch(Exception exception)
		{
			this.exceptionHandler.handleException(exception);		
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
		this.validation.validateUserKYCCompletion(businessUser);
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		try {
				businessAzimutClient.setClientBankAccounts(this.azimutDataLookupUtility.getKYCClientBankAccountData(businessUser));
				businessAzimutClient.setFirstPageId(businessUser.getFirstPageId());
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
	public BusinessAzimutClient getTotalClientBankAccounts(BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		try 
			{	
				//List<BusinessClientBankAccountDetails> clientTeacomputersBankAccounts=this.restManager.getClientBankAccountsMapper.wrapBaseBusinessEntity(true,this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,true), null).getDataList();
				List<BusinessClientBankAccountDetails> clientTeacomputersBankAccounts=this.restContract.getDataList(this.restContract.getClientBankAccountsMapper,this.prepareClientBankAccountDetailsInputs(businessAzimutClient,tokenizedBusinessUser,true), null);
				BusinessClientBankAccountDetails [] tempClientTeacomputersBankAccounts=this.azimutDataLookupUtility.getClientBankAccountData(tokenizedBusinessUser);
				if(arrayUtility.isArrayPopulated(tempClientTeacomputersBankAccounts))
				{	
					clientTeacomputersBankAccounts.addAll(Arrays.asList(tempClientTeacomputersBankAccounts));
				}
				businessAzimutClient.setBankList(clientTeacomputersBankAccounts);
			}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);

		}
		return businessAzimutClient;
	}
		
	
	public BusinessAzimutClient getPaginatedClientFunds(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws IntegrationException, BusinessException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		responseBusinessAzimutClient=this.getClientFundsOrFund(tokenizedBusinessUser, businessAzimutClient,true);
		if(responseBusinessAzimutClient!=null&&businessAzimutClient!=null&&businessAzimutClient.getPageNumber()!=null)
		{
			List<BusinessClientFund> clientFunds=responseBusinessAzimutClient.getBusinessClientFunds();
			PaginatedEntity<BusinessClientFund> paginatedBusinessClientFunds=this.clientFundListUtility.getPaginatedList(clientFunds,businessAzimutClient.getPageNumber(),Integer.valueOf(this.configProperties.getPageSize()));
			responseBusinessAzimutClient.setPaginatedBusinessClientFunds(paginatedBusinessClientFunds);
			responseBusinessAzimutClient.setBusinessClientFunds(null);
		}
		return responseBusinessAzimutClient;
	}
	
	
	public BusinessAzimutClient getClientFundsOrFund(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient,boolean getDetails) throws IntegrationException, BusinessException
	{
		
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		if(tokenizedBusinessUser!=null&&StringUtility.isStringPopulated(tokenizedBusinessUser.getUserId()))
		{
			try 
			{
				Map <String,Object> clientFundPriceMap= this.getClientFunds(tokenizedBusinessUser, businessAzimutClient,getDetails);
			
				this.beautifyBusinessClientFunds(responseBusinessAzimutClient,
					(List<BusinessClientFund>)clientFundPriceMap.get(BusinessClientFund.class.getName()),
					(List<BusinessFundPrice>)clientFundPriceMap.get(BusinessFundPrice.class.getName()),
					(List<BusinessClientCashBalance>)clientFundPriceMap.get(BusinessClientCashBalance.class.getName()));
			}
		
			catch(Exception exception)
			{
				responseBusinessAzimutClient.setTotalPosition(new BigDecimal(0));
				responseBusinessAzimutClient.setBalance(0D);
				responseBusinessAzimutClient.setBusinessClientFunds(clientFundListUtility.handleExceptionAndReturnEmptyList(exception));
			}
		}
		else
		{
			responseBusinessAzimutClient.setTotalPosition(new BigDecimal(0));
			responseBusinessAzimutClient.setBalance(0D);
			responseBusinessAzimutClient.setBusinessClientFunds(new ArrayList<BusinessClientFund>());
		
		}
		return responseBusinessAzimutClient;
	}
			
	Map<String,Object> getClientFunds(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient,boolean getDetails) throws IntegrationException, BusinessException
	{
		
		Map <String,Object> clientFundPriceMap= new HashMap<String,Object>();
		List<BusinessFundPrice> businessFundPrices=new ArrayList<BusinessFundPrice>();
		List<BusinessClientFund> businessClientFunds=new ArrayList<BusinessClientFund>();
		//businessClientFunds=this.restManager.getClientFundsMapper.wrapBaseBusinessEntity(true, this.prepareClientFundInputs(tokenizedBusinessUser,businessAzimutClient), null).getDataList();
		businessClientFunds=this.restContract.getDataList(this.restContract.getClientFundsMapper, this.prepareClientFundInputs(tokenizedBusinessUser,businessAzimutClient), null);
			
		if(businessAzimutClient!=null&&businessAzimutClient.getTeacomputerId()!=null)
		{
			if(clientFundListUtility.isListPopulated(businessClientFunds)&&getDetails)
			{
				this.getClientFundDetails(businessClientFunds.get(0),tokenizedBusinessUser, businessAzimutClient);
			}
		}
		else
		{
			//List<BusinessClientCashBalance> businessClientCashBalances=this.restManager.getClientBalanceMapper.wrapBaseBusinessEntity(true,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList();
			List<BusinessClientCashBalance> businessClientCashBalances=this.restContract.getDataList(this.restContract.getClientBalanceMapper,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null);
			clientFundPriceMap.put(BusinessClientCashBalance.class.getName(), businessClientCashBalances);
		}
		
		
		
		Long [] teacomputerFundIds=this.populateTheArrayOfFundIds(businessClientFunds);		
		if(this.arrayUtility.isArrayPopulated(teacomputerFundIds))
		{
			this.logger.info("Funds Populated::::");
			businessFundPrices= this.fundPriceMapper.convertBasicListToBusinessList(this.fundService.getAllRelevantFundPrices(teacomputerFundIds));
		}
		
		clientFundPriceMap.put(BusinessClientFund.class.getName(), businessClientFunds);
		clientFundPriceMap.put(BusinessFundPrice.class.getName(), businessFundPrices);
		
		return clientFundPriceMap;
	}
	
	public void getClientFundDetails(BusinessClientFund businessClientFund,BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws IntegrationException, BusinessException
	{	
		//List<BusinessFundTransaction> businessFundTransactions=this.restManager.getFundTransactionsMapper.wrapBaseBusinessEntity(true, this.prepareBusinessBusinessFundTransactionRetrievalInputs(tokenizedBusinessUser,businessAzimutClient), null).getDataList();
		List<BusinessFundTransaction> allTransactions=new ArrayList<BusinessFundTransaction>();
		
		allTransactions.addAll(this.restContract.getDataList(this.restContract.getFundTransactionsMapper,this.prepareBusinessFundTransactionRetrievalInputs(tokenizedBusinessUser,businessAzimutClient,OrderStatus.EXECUTED),StringUtility.EXECUTED_ORDERS));
		allTransactions.addAll(this.restContract.getDataList(this.restContract.getFundTransactionsMapper,this.prepareBusinessFundTransactionRetrievalInputs(tokenizedBusinessUser,businessAzimutClient,OrderStatus.PENDING),StringUtility.PENDING_ORDERS));
		allTransactions.addAll(this.restContract.getDataList(this.restContract.getFundTransactionsMapper,this.prepareBusinessFundTransactionRetrievalInputs(tokenizedBusinessUser,businessAzimutClient,OrderStatus.CANCELED),StringUtility.CANCELED_ORDERS));
		
		this.beautifyBusinessClientFundTransactions(businessClientFund,allTransactions,businessAzimutClient);
	
	}
	
	
	
	public BusinessAzimutClient getEportfolio(BusinessUser tokenizedBusinessUser,String language) throws BusinessException, IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		
		if(tokenizedBusinessUser!=null&&StringUtility.isStringPopulated(tokenizedBusinessUser.getUserId()))
		{
			try 
			{
				responseBusinessAzimutClient.setEportfolioDetails(this.restContract.getDataList(this.restContract.getEportfolioMapper,this.prepareGetEportfolioInputs(tokenizedBusinessUser,language), null));
			}
			catch(Exception exception)
			{
				responseBusinessAzimutClient.setEportfolioDetails(eportfolioDetailListUtility.handleExceptionAndReturnEmptyList(exception));		
			}
		}
		else
		{
			responseBusinessAzimutClient.setEportfolioDetails(new ArrayList<EportfolioDetail>());
		}
		
		return responseBusinessAzimutClient;
	}
	
	public BusinessAzimutClient getValuationReport(BusinessUser tokenizedBusinessUser,String language) throws BusinessException, IntegrationException
	{
		return this.getReport(tokenizedBusinessUser, language, StringUtility.VALUATION_REPORT,null);
	}
	public BusinessAzimutClient getRequestStatement(BusinessUser tokenizedBusinessUser,String language,BusinessAzimutClient businessAzimutClient) throws BusinessException, IntegrationException
	{
		return this.getReport(tokenizedBusinessUser, language, StringUtility.REQUEST_STATEMENT, businessAzimutClient);
	}
	
	public BusinessAzimutClient getReport(BusinessUser tokenizedBusinessUser,String language,String reportType,BusinessAzimutClient businessAzimutClient) throws BusinessException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		try {
				//responseBusinessAzimutClient= this.restManager.getReportMapper.wrapBaseBusinessEntity(false,this.prepareGetReportInputs(tokenizedBusinessUser,language,reportType,businessAzimutClient), reportType).getData();
			responseBusinessAzimutClient=(BusinessAzimutClient) this.restContract.getData(this.restContract.getReportMapper, this.prepareGetReportInputs(tokenizedBusinessUser,language,reportType,businessAzimutClient), reportType);
			}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);		
		}
		return responseBusinessAzimutClient;
	}
	
	
	
	public BusinessAzimutClient  getCompanyBankAccounts(Long currencyId) throws BusinessException,IntegrationException
	{
		try 
		{
			BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
			BusinessAzimutDataLookup businessAzimutDataLookup=new BusinessAzimutDataLookup();
			
			List<BusinessCompanyBankAccount> businessCompanyBankAccountsWithCurrency=new  ArrayList<BusinessCompanyBankAccount>();
			
			//List<BusinessCompanyBankAccount> businessCompanyBankAccounts= this.restManager.getCompanyBankAccountMapper.wrapBaseBusinessEntity(true, null, null).getDataList();
			List<BusinessCompanyBankAccount> businessCompanyBankAccounts=this.restContract.getDataList(this.restContract.getCompanyBankAccountMapper, null, null);
			if(companyBankAccountListUtility.isListPopulated(businessCompanyBankAccounts)&&currencyId!=null)
			{
				for(BusinessCompanyBankAccount businessCompanyBankAccount:businessCompanyBankAccounts)
				{
					if(businessCompanyBankAccount!=null&&businessCompanyBankAccount.getCurrencyId()!=null)
					{
						if(NumberUtility.areLongValuesMatching(currencyId,Long.valueOf( businessCompanyBankAccount.getCurrencyId())))
						{
							businessCompanyBankAccountsWithCurrency.add(businessCompanyBankAccount);
						}
					}
				}
				businessAzimutDataLookup.setCompanyBankAccounts(businessCompanyBankAccountsWithCurrency);
			}
			else if(companyBankAccountListUtility.isListPopulated(businessCompanyBankAccounts)&&currencyId==null)
			{
				businessAzimutDataLookup.setCompanyBankAccounts(businessCompanyBankAccounts);
			}
		
			businessAzimutClient.setLookupData(businessAzimutDataLookup);
			
			return businessAzimutClient;
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);	
		}
	}
	
	private EportfolioDetail prepareGetEportfolioInputs(BusinessUser tokenizedBusinessUser,String language) throws BusinessException {
		EportfolioDetail eportBusinessEntity=new EportfolioDetail();		
		eportBusinessEntity.setAzId(tokenizedBusinessUser.getUserId());
		eportBusinessEntity.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		eportBusinessEntity.setLanguage(language);
		return eportBusinessEntity;
	}
	private BusinessAzimutClient prepareGetReportInputs(BusinessUser tokenizedBusinessUser,String language,String reportType,BusinessAzimutClient businessAzimutClient) throws BusinessException {
		BusinessAzimutClient searchBusinessAzimutClient=new BusinessAzimutClient();
		searchBusinessAzimutClient.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessAzimutClient.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		searchBusinessAzimutClient.setReportType(reportType);
		searchBusinessAzimutClient.setLanguage(language);
		
		if(businessAzimutClient!=null)
		{
			searchBusinessAzimutClient.setSearchFromDate(businessAzimutClient.getSearchFromDate());
			searchBusinessAzimutClient.setSearchToDate(businessAzimutClient.getSearchToDate());
			searchBusinessAzimutClient.setCurrencyId(businessAzimutClient.getCurrencyId());
		}
		
		
		return searchBusinessAzimutClient;
	}
		
	private AzimutAccount prepareAccountAdditionInputs(AzimutAccount azimutAccount,BusinessUser businessUser) throws BusinessException,Exception 
	{
		azimutAccount.setCustomerNameEn(businessUser.getFirstName()+StringUtility.SPACE+businessUser.getLastName());
		azimutAccount.setCustomerNameAr(businessUser.getFirstName()+StringUtility.SPACE+businessUser.getLastName());
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

	BusinessTransaction prepareTransactionSearchInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		BusinessTransaction searchBusinessTransaction=new BusinessTransaction();
		/*
		searchBusinessTransaction.setSearchFromDate(businessAzimutClient.getSearchFromDate());
		searchBusinessTransaction.setSearchToDate(businessAzimutClient.getSearchToDate());
		*/
		searchBusinessTransaction.setSearchFromDate(StringUtility.SEARCH_FROM_TRANSACTION_DATE);
		searchBusinessTransaction.setSearchToDate(StringUtility.SEARCH_TO_TRANSACTION_DATE);
		
		searchBusinessTransaction.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessTransaction.setAzIdType(/*businessAzimutClient.getAzIdType()*/this.getAzimutUserTypeId(tokenizedBusinessUser));
		this.logger.info("SearchBusinessTransaction:"+ searchBusinessTransaction.toString());
		return searchBusinessTransaction;
	}
	
	BusinessClientCashBalance preparClientCashBalanceInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		BusinessClientCashBalance searchBusinessClientCashBalance=new BusinessClientCashBalance();
		searchBusinessClientCashBalance.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientCashBalance.setAzIdType(/*businessAzimutClient.getAzIdType()*/this.getAzimutUserTypeId(tokenizedBusinessUser));
		this.logger.info("SearchBusinessClientCashBalance:"+ searchBusinessClientCashBalance.toString());
		return searchBusinessClientCashBalance;
		
	}
	BusinessClientBankAccountDetails prepareClientBankAccountDetailsInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,boolean isList) throws BusinessException
	{
		BusinessClientBankAccountDetails searchBusinessClientBankAccountDetails=new BusinessClientBankAccountDetails();
		searchBusinessClientBankAccountDetails.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientBankAccountDetails.setAzIdType(/*businessAzimutClient.getAzIdType()*/this.getAzimutUserTypeId(tokenizedBusinessUser));
		searchBusinessClientBankAccountDetails.setIsActive(businessAzimutClient.getIsActive());
		searchBusinessClientBankAccountDetails.setAccountId(businessAzimutClient.getAccountId());
		return searchBusinessClientBankAccountDetails;
	}
	
	BusinessClientFund prepareClientFundInputs(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws BusinessException
	{
		BusinessClientFund searchBusinessClientFund=new BusinessClientFund();
		searchBusinessClientFund.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientFund.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		searchBusinessClientFund.setFundId(businessAzimutClient.getTeacomputerId());
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
		if(businessAzimutClient!=null &&businessTransactionListUtility.isListPopulated(businessAzimutClient.getBusinessTransactions()))
			{
				List<BusinessTransaction> unsortedTransactions=businessAzimutClient.getBusinessTransactions();
				
				businessAzimutClient.setTotalPendingAmount(pendingAmount);
				
				boolean sorting = true;
				if(StringUtility.isStringPopulated(businessAzimutClient.getSorting())&&businessAzimutClient.getSorting().equals(Sorting.ASC.getOrder()))
				sorting=true;
				
				else if(!StringUtility.isStringPopulated(businessAzimutClient.getSorting())||StringUtility.isStringPopulated(businessAzimutClient.getSorting())&&businessAzimutClient.getSorting().equals(Sorting.DESC.getOrder()))
				sorting=false;	
					
				this.logger.info("Sorting:::"+sorting);
				
				Collections.sort(unsortedTransactions,sorting ? this.cashTransactionSortCompare: cashTransactionSortCompare.reversed());
				
				this.logger.info("Sorted Transactions:::"+unsortedTransactions.toString());
				
				
				int lastIndex=unsortedTransactions.size();
				int index = sorting ?  lastIndex: 0;
				
				this.logger.info("Index:::"+index);
				businessAzimutClient.setBusinessTransactions(unsortedTransactions);
				
				businessAzimutClient.setLastTransactionDate(sorting? unsortedTransactions.get(lastIndex-1).getTrxDate():unsortedTransactions.get(0).getTrxDate());
				String oldLastTransactionDate=businessAzimutClient.getLastTransactionDate();
				businessAzimutClient.setLastTransactionDate(DateUtility.changeStringDateFormat(oldLastTransactionDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd MMM, yyyy")));
				
				for(BusinessTransaction  businessTransaction:unsortedTransactions)
				{
					if(businessTransaction!=null)
					{
						this.logger.info("business Transaction:::"+ businessTransaction);
							
						 String oldDate=businessTransaction.getTrxDate();
						 businessTransaction.setTrxDate(DateUtility.changeStringDateFormat(oldDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd MMM, yyyy")));
											
					}
				}
				
			}
		
		return businessAzimutClient;
	}
	
  AzimutAccount	prepareAccountRetrievalInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException
  {
	  AzimutAccount azimutAccount=new AzimutAccount();
	  azimutAccount.setPhoneNumber(businessAzimutClient.getUserPhone());
	  azimutAccount.setUserId(businessAzimutClient.getUserId());
	  azimutAccount.setIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
	  return azimutAccount;
  }	
  

  
  
  BusinessFundTransaction prepareBusinessFundTransactionRetrievalInputs(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient,OrderStatus orderStatus) throws BusinessException
  {
	  BusinessFundTransaction searchBusinessFundTransaction=new BusinessFundTransaction();
	  searchBusinessFundTransaction.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
	  searchBusinessFundTransaction.setAzId(tokenizedBusinessUser.getUserId());
	  searchBusinessFundTransaction.setFundId(businessAzimutClient.getTeacomputerId());
	  searchBusinessFundTransaction.setStatus(orderStatus);
	  return searchBusinessFundTransaction;
  }
  
  private void beautifyBusinessClientFunds(BusinessAzimutClient responseBusinessAzimutClient,List<BusinessClientFund> businessClientFunds,List<BusinessFundPrice> businessFundPrices,List<BusinessClientCashBalance> businessClientCashBalances)
  {
	  
	  double totalPosition=0d;

	  if(this.clientFundListUtility.isListPopulated(businessClientFunds)&&this.fundPricesListUtility.isListPopulated(businessFundPrices))
	  {
		this.logger.info("The two lists are populated::");  
		  double totalFundPosition=0d;
		  for(BusinessClientFund businessClientFund:businessClientFunds)
		  {
			  for(BusinessFundPrice businessFundPrice:businessFundPrices)
			  {
				  if(NumberUtility.areLongValuesMatching(businessClientFund.getTeacomputerId(), businessFundPrice.getTeacomputerId()))
				  {
					  
					  if(StringUtility.isStringPopulated(businessFundPrice.getPriceDate()))
					  businessClientFund.setLastPriceUpdateDate(DateUtility.changeStringDateFormat(businessFundPrice.getPriceDate(), new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("dd MMM, yyyy")));
					 
					  
					  businessClientFund.setLogo(businessFundPrice.getLogo());
					  
					  if(businessClientFund.getTotalAmount()!=null&&businessClientFund.getCurrencyRate()!=null)
					  {
						  totalFundPosition=totalFundPosition+(businessClientFund.getTotalAmount().doubleValue()* businessClientFund.getCurrencyRate().doubleValue());
					  }
					  
				  }
			  }
			  
		  }
		  totalPosition=totalFundPosition;
	  }
	  
	  if(this.clientCashBalanceListUtility.isListPopulated(businessClientCashBalances))
		{
		 this.logger.info("Cash Balances:::::::"+businessClientCashBalances.toString()); 
		 double clientCashBalanceInAllCurrencies=0.0D;
		  
		  	for(BusinessClientCashBalance businessClientCashBalance:businessClientCashBalances)
			{
		  		if(businessClientCashBalance!=null&&businessClientCashBalance.getBalance()!=null&&businessClientCashBalance.getCurrencyRate()!=null)
		  		{
		  			clientCashBalanceInAllCurrencies=clientCashBalanceInAllCurrencies+(businessClientCashBalance.getBalance().doubleValue()*businessClientCashBalance.getCurrencyRate().doubleValue());
		  		}
			}
		  	
		  	responseBusinessAzimutClient.setBalance(clientCashBalanceInAllCurrencies);
		  	totalPosition=totalPosition+clientCashBalanceInAllCurrencies;
		}
	  else
	  {
		  responseBusinessAzimutClient.setBalance(0.0D);
	  }
	 
	  //responseBusinessAzimutClient.setTotalPosition(totalPosition);
	  responseBusinessAzimutClient.setTotalPosition(NumberUtility.changeFormat(new BigDecimal(totalPosition)));
	  responseBusinessAzimutClient.setBusinessClientFunds(businessClientFunds);
  }
  
  private void beautifyBusinessClientFundTransactions(BusinessClientFund businessClientFund,List<BusinessFundTransaction> businessFundTransactions,BusinessAzimutClient searchBusinessAzimutClient)
  {
	 this.logger.info("Business Fund Transactions:::"+businessFundTransactions.toString());
	 if(businessClientFund!=null)
	 {
		boolean sorting = true;
				if(StringUtility.isStringPopulated(searchBusinessAzimutClient.getSorting())&&searchBusinessAzimutClient.getSorting().equals(Sorting.ASC.getOrder()))
				sorting=true;
				
				else if(!StringUtility.isStringPopulated(searchBusinessAzimutClient.getSorting())||StringUtility.stringsMatch(searchBusinessAzimutClient.getSorting(), Sorting.DESC.getOrder()))
				sorting=false;	
				{	
				this.logger.info("Sorting:::"+sorting);
				Collections.sort(businessFundTransactions,sorting ? this.fundTransactionSortCompare: fundTransactionSortCompare.reversed());
				int index = sorting ? businessFundTransactions.size() : 0;
				this.logger.info("Index:::"+index);
				}
				
		 
		 for(BusinessFundTransaction businessFundTransaction:businessFundTransactions)
		 {
			 String oldDate=businessFundTransaction.getOrderDate();
			 this.logger.info("Old date:::::"+oldDate);
			 businessFundTransaction.setOrderDate(DateUtility.changeStringDateFormat(oldDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd MMM, yyyy")));
		 }
		 
		 businessClientFund.setFundTransactions(businessFundTransactions);
		 
	 }
	
	 
  }
  Long [] populateTheArrayOfFundIds(List<BusinessClientFund> businessClientFunds)
  {
	  if(this.clientFundListUtility.isListPopulated(businessClientFunds))
		{
		  Long [] fundIds=new Long [businessClientFunds.size()];
		  
		  for(int i=0;i<businessClientFunds.size();i++)
		  {
			  if(businessClientFunds!=null&&businessClientFunds.get(i)!=null&&businessClientFunds.get(i).getTeacomputerId()!=null)
			  {
				  fundIds[i]=businessClientFunds.get(i).getTeacomputerId().longValue();
			  }
		  }
		  
		  return fundIds;
		}
	  return null;
  }
  
  
}
