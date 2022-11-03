package innovitics.azimut.businessutilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.businessmodels.funds.BusinessClientFund;
import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.businessmodels.funds.BusinessFundTransaction;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessClientCashBalance;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.mappers.RestManager;
import innovitics.azimut.services.FundService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.businessutilities.CashTransactionSortCompare;
import innovitics.azimut.utilities.businessutilities.FundTransactionSortCompare;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.FundMapper;
import innovitics.azimut.utilities.mapping.FundPriceMapper;
import innovitics.azimut.validations.Validation;
import innovitics.azimut.validations.validators.azimutclient.GetBalanceAndTransactions;
@Component
public class AzimutClientDetailsUtility extends ParentUtility 
{
	@Autowired Validation<?> validation;
	@Autowired ListUtility<BusinessTransaction> businessTransactionListUtility;
	@Autowired CashTransactionSortCompare cashTransactionSortCompare;
	@Autowired GetBalanceAndTransactions getBalanceAndTransactions;	
	@Autowired ListUtility<BusinessClientFund> clientFundListUtility ;
	@Autowired ListUtility<BusinessClientCashBalance> clientCashBalanceListUtility ;
	@Autowired FundService fundService;
	@Autowired FundPriceMapper fundPriceMapper;
	@Autowired FundMapper fundMapper;
	@Autowired ListUtility<BusinessFundPrice> fundPricesListUtility;
	@Autowired FundTransactionSortCompare fundTransactionSortCompare;


	public BusinessAzimutClient getBalanceAndTransactions(RestManager restManager,BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		this.logger.info("");
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		this.validation.validate(businessAzimutClient, getBalanceAndTransactions, BusinessAzimutClient.class.getName());
		responseBusinessAzimutClient.setSorting(businessAzimutClient.getSorting());
		this.getBalance(restManager,businessAzimutClient, tokenizedBusinessUser, responseBusinessAzimutClient);
		this.getTransactions(restManager,businessAzimutClient, tokenizedBusinessUser, responseBusinessAzimutClient);	
		return this.beautifyBalanceAndTransactionsBusinessAzimutClient(responseBusinessAzimutClient);
	}
	
	public BusinessAzimutClient getBalance(RestManager restManager,BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,BusinessAzimutClient responseBusinessAzimutClient) throws BusinessException,IntegrationException
	{
		try 
		{				
			responseBusinessAzimutClient.setBusinessClientCashBalances(restManager.getClientBalanceMapper.wrapBaseBusinessEntity(true,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList());			}
		catch(Exception exception)
		{
			responseBusinessAzimutClient.setLastTransactionDate("No transactions yet.");
			responseBusinessAzimutClient.setBusinessClientCashBalances(clientCashBalanceListUtility.handleExceptionAndReturnEmptyList(exception,ErrorCode.INVALID_CLIENT));						
		}
		
		if(clientCashBalanceListUtility.isListEmptyOrNull(responseBusinessAzimutClient.getBusinessClientCashBalances()))
		{
			List<BusinessClientCashBalance> emptyBusinessClientCashBalances=new ArrayList<BusinessClientCashBalance>();
			emptyBusinessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.EGYPTIAN_POUND));
			emptyBusinessClientCashBalances.add(new BusinessClientCashBalance(CurrencyType.US_DOLLAR));
			responseBusinessAzimutClient.setBusinessClientCashBalances(emptyBusinessClientCashBalances);
		}
		
		return responseBusinessAzimutClient;

	}
	public BusinessAzimutClient getTransactions(RestManager restManager,BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser,BusinessAzimutClient responseBusinessAzimutClient) throws BusinessException,IntegrationException
	{
		try 
		{
			responseBusinessAzimutClient.setBusinessTransactions(restManager.getTransactionsMapper.wrapBaseBusinessEntity(true,this.prepareTransactionSearchInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList());
		}
		catch(Exception exception)
		{
			responseBusinessAzimutClient.setBusinessTransactions(businessTransactionListUtility.handleExceptionAndReturnEmptyList(exception,ErrorCode.INVALID_CLIENT));
		}
		return responseBusinessAzimutClient;
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
				businessAzimutClient.setLastTransactionDate(DateUtility.changeStringDateFormat(oldLastTransactionDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd MMM,yyyy")));
				
				for(BusinessTransaction  businessTransaction:unsortedTransactions)
				{
					if(businessTransaction!=null)
					{
						this.logger.info("business Transaction:::"+ businessTransaction);
							
						 String oldDate=businessTransaction.getTrxDate();
						 businessTransaction.setTrxDate(DateUtility.changeStringDateFormat(oldDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd MMM,yyyy")));
											
					}
				}
				
			}
		
		return businessAzimutClient;
	}
	
	BusinessTransaction prepareTransactionSearchInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser)
	{
		BusinessTransaction searchBusinessTransaction=new BusinessTransaction();
		searchBusinessTransaction.setSearchFromDate(StringUtility.SEARCH_FROM_TRANSACTION_DATE);
		searchBusinessTransaction.setSearchToDate(StringUtility.SEARCH_TO_TRANSACTION_DATE);		
		searchBusinessTransaction.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessTransaction.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		this.logger.info("SearchBusinessTransaction:"+ searchBusinessTransaction.toString());
		return searchBusinessTransaction;
	}
	
	BusinessClientCashBalance preparClientCashBalanceInputs(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser)
	{
		BusinessClientCashBalance searchBusinessClientCashBalance=new BusinessClientCashBalance();
		searchBusinessClientCashBalance.setAzId(tokenizedBusinessUser.getUserId());
		searchBusinessClientCashBalance.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		this.logger.info("SearchBusinessClientCashBalance:"+ searchBusinessClientCashBalance.toString());
		return searchBusinessClientCashBalance;
		
	}
	 protected Long getAzimutUserTypeId(BusinessUser businessUser)
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
	 


		public BusinessAzimutClient getPaginatedClientFunds(RestManager restManager,BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient,ArrayUtility arrayUtility) throws IntegrationException, BusinessException
		{
			BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
			responseBusinessAzimutClient=this.getClientFundsOrFund(restManager,tokenizedBusinessUser, businessAzimutClient,arrayUtility);
			if(responseBusinessAzimutClient!=null&&businessAzimutClient!=null&&businessAzimutClient.getPageNumber()!=null)
			{
				List<BusinessClientFund> clientFunds=responseBusinessAzimutClient.getBusinessClientFunds();
				PaginatedEntity<BusinessClientFund> paginatedBusinessClientFunds=this.clientFundListUtility.getPaginatedList(clientFunds,businessAzimutClient.getPageNumber(),Integer.valueOf(this.configProperties.getPageSize()));
				responseBusinessAzimutClient.setPaginatedBusinessClientFunds(paginatedBusinessClientFunds);
				responseBusinessAzimutClient.setBusinessClientFunds(null);
			}
			return responseBusinessAzimutClient;
		}
		
		private BusinessAzimutClient getClientFundsOrFund(RestManager restManager,BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient,ArrayUtility arrayUtility) throws IntegrationException, BusinessException
		{
			
			BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
			
			try 
			{
				Map <String,Object> clientFundPriceMap= this.getClientFunds(restManager,tokenizedBusinessUser, businessAzimutClient,arrayUtility);
				this.beautifyBusinessClientFunds(responseBusinessAzimutClient,
						(List<BusinessClientFund>)clientFundPriceMap.get(BusinessClientFund.class.getName()),
						(List<BusinessFundPrice>)clientFundPriceMap.get(BusinessFundPrice.class.getName()),
						(List<BusinessClientCashBalance>)clientFundPriceMap.get(BusinessClientCashBalance.class.getName()));
			}
			
			catch(Exception exception)
			{
				responseBusinessAzimutClient.setBusinessClientFunds(clientFundListUtility.handleExceptionAndReturnEmptyList(exception, ErrorCode.INVALID_CLIENT));
			}
			
			return responseBusinessAzimutClient;
		}
		
		private Map<String,Object> getClientFunds(RestManager restManager,BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient,ArrayUtility arrayUtility) throws IntegrationException
		{
			
			Map <String,Object> clientFundPriceMap= new HashMap<String,Object>();
			List<BusinessFundPrice> businessFundPrices=new ArrayList<BusinessFundPrice>();
			List<BusinessClientFund> businessClientFunds=new ArrayList<BusinessClientFund>();
			businessClientFunds=restManager.getClientFundsMapper.wrapBaseBusinessEntity(true, this.prepareClientFundInputs(tokenizedBusinessUser,businessAzimutClient), null).getDataList();
			
				
			if(businessAzimutClient!=null&&businessAzimutClient.getTeacomputerId()!=null)
			{
				if(clientFundListUtility.isListPopulated(businessClientFunds))
				{
					this.getClientFundDetails(restManager,businessClientFunds.get(0),tokenizedBusinessUser, businessAzimutClient);
				}
			}
			else
			{
				List<BusinessClientCashBalance> businessClientCashBalances=restManager.getClientBalanceMapper.wrapBaseBusinessEntity(true,this.preparClientCashBalanceInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList();
				clientFundPriceMap.put(BusinessClientCashBalance.class.getName(), businessClientCashBalances);
			}
			
			
			
			Long [] teacomputerFundIds=this.populateTheArrayOfFundIds(businessClientFunds);		
			if(arrayUtility.isArrayPopulated(teacomputerFundIds))
			{
				this.logger.info("Funds Populated::::");
				businessFundPrices= this.fundPriceMapper.convertBasicListToBusinessList(this.fundService.getAllRelevantFundPrices(teacomputerFundIds));
			}
			
			clientFundPriceMap.put(BusinessClientFund.class.getName(), businessClientFunds);
			clientFundPriceMap.put(BusinessFundPrice.class.getName(), businessFundPrices);
			
			return clientFundPriceMap;
		}
		
		private void getClientFundDetails(RestManager restManager,BusinessClientFund businessClientFund,BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient) throws IntegrationException
		{	
			List<BusinessFundTransaction> businessFundTransactions=restManager.getFundTransactionsMapper.wrapBaseBusinessEntity(true, this.prepareBusinessBusinessFundTransactionRetrievalInputs(tokenizedBusinessUser,businessAzimutClient), null).getDataList();	
			this.beautifyBusinessClientFundTransactions(businessClientFund,businessFundTransactions,businessAzimutClient);
		
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
					 businessFundTransaction.setOrderDate(DateUtility.changeStringDateFormat(oldDate, new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd MMM,yyyy")));
				 }
				 
				 businessClientFund.setFundTransactions(businessFundTransactions);
				 
			 }
			
			 
		  }
		 private  Long [] populateTheArrayOfFundIds(List<BusinessClientFund> businessClientFunds)
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
							  businessClientFund.setLastPriceUpdateDate(DateUtility.changeStringDateFormat(businessFundPrice.getPriceDate(), new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("dd MMM,yyyy")));
							 
							  
							  businessClientFund.setLogo(businessFundPrice.getLogo());
							  
							  if(businessClientFund.getTotalAmount()!=null)
							  {
								  totalFundPosition=totalFundPosition+(businessClientFund.getTotalAmount().doubleValue());
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
			  
			 
			  responseBusinessAzimutClient.setTotalPosition(totalPosition);
			  responseBusinessAzimutClient.setBusinessClientFunds(businessClientFunds);
		  }

		  private   BusinessClientFund prepareClientFundInputs(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient)
			{
				BusinessClientFund searchBusinessClientFund=new BusinessClientFund();
				searchBusinessClientFund.setAzId(tokenizedBusinessUser.getUserId());
				searchBusinessClientFund.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
				searchBusinessClientFund.setFundId(businessAzimutClient.getTeacomputerId());
				return searchBusinessClientFund;
			}
		  private  BusinessFundTransaction prepareBusinessBusinessFundTransactionRetrievalInputs(BusinessUser tokenizedBusinessUser,BusinessAzimutClient businessAzimutClient)
		  {
			  BusinessFundTransaction searchBusinessFundTransaction=new BusinessFundTransaction();
			  searchBusinessFundTransaction.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
			  searchBusinessFundTransaction.setAzId(tokenizedBusinessUser.getUserId());
			  searchBusinessFundTransaction.setFundId(businessAzimutClient.getTeacomputerId());
			  return searchBusinessFundTransaction;
		  }
		  
}
