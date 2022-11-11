package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.funds.BusinessFundTransaction;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetFundTransactionsApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.FundTransactionOutput;
import innovitics.azimut.rest.entities.teacomputers.GetFundTransactionsInput;
import innovitics.azimut.rest.entities.teacomputers.GetFundTransactionsOutput;
import innovitics.azimut.rest.models.teacomputers.FundTransactionResponse;
import innovitics.azimut.rest.models.teacomputers.GetFundTransactionsRequest;
import innovitics.azimut.rest.models.teacomputers.GetFundTransactionsResponse;
import innovitics.azimut.utilities.crosslayerenums.OrderStatus;
import innovitics.azimut.utilities.crosslayerenums.OrderType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;

@Component
public class GetFundTransactionsMapper extends RestMapper<GetFundTransactionsInput, GetFundTransactionsOutput,GetFundTransactionsRequest ,FundTransactionResponse[], BusinessFundTransaction> {

	public static final String EXECUTED_ORDERS="GetExecutedOrders";
	public static final String PENDING_ORDERS="GetPendingOrders";
	
	@Autowired GetFundTransactionsApiConsumer getFundTransactionsApiConsumer;
	@Autowired ListUtility<FundTransactionOutput> listUtility;
	@Override
	BusinessFundTransaction consumeRestService(BusinessFundTransaction businessFundTransaction, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessFundTransaction> consumeListRestService(BusinessFundTransaction businessFundTransaction, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{

		List<BusinessFundTransaction> allTransactions=new ArrayList<BusinessFundTransaction>();
		List<BusinessFundTransaction> executedTransactions=new ArrayList<BusinessFundTransaction>();
		List<BusinessFundTransaction> pendingTransactions=new ArrayList<BusinessFundTransaction>();
		
		executedTransactions=this.createListBusinessEntityFromOutput(this.getFundTransactionsApiConsumer.invoke(this.createInput(businessFundTransaction), FundTransactionResponse[].class,EXECUTED_ORDERS),OrderStatus.EXECUTED);
		pendingTransactions=this.createListBusinessEntityFromOutput(this.getFundTransactionsApiConsumer.invoke(this.createInput(businessFundTransaction), FundTransactionResponse[].class, PENDING_ORDERS),OrderStatus.PENDING);
		
		allTransactions.addAll(executedTransactions);
		allTransactions.addAll(pendingTransactions);
		
		
		return allTransactions;
	}

	@Override
	GetFundTransactionsInput createInput(BusinessFundTransaction businessFundTransaction) {
		GetFundTransactionsInput  input=new GetFundTransactionsInput();
		input.setIdTypeId(businessFundTransaction.getAzIdType());
		input.setIdNumber(businessFundTransaction.getAzId());
		input.setFundId(businessFundTransaction.getFundId());
		return input;
	}

	@Override
	BusinessFundTransaction createBusinessEntityFromOutput(GetFundTransactionsOutput getFundTransactionsOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<BusinessFundTransaction> createListBusinessEntityFromOutput(GetFundTransactionsOutput getFundTransactionsOutput) {	
		return null;
	}
	protected List<BusinessFundTransaction> createListBusinessEntityFromOutput(GetFundTransactionsOutput getFundTransactionsOutput,OrderStatus orderStatus) 
	{
		List<BusinessFundTransaction> businessFundTransactions=new ArrayList<BusinessFundTransaction>();		
		if(getFundTransactionsOutput!=null&&this.listUtility.isListPopulated(getFundTransactionsOutput.getFundTransactionOutputs()))
		{
			for(FundTransactionOutput fundTransactionOutput:getFundTransactionsOutput.getFundTransactionOutputs())
			{
				BusinessFundTransaction businessFundTransaction=new BusinessFundTransaction();
				businessFundTransaction.setTransactionId(fundTransactionOutput.getTransactionId());
				businessFundTransaction.setFundId(fundTransactionOutput.getFundId());
				businessFundTransaction.setOrderDate(fundTransactionOutput.getOrderDate());
				businessFundTransaction.setOrderValue(fundTransactionOutput.getOrderValue());
				businessFundTransaction.setQuantity(fundTransactionOutput.getQuantity());
				businessFundTransaction.setOrderTypeId(fundTransactionOutput.getOrderTypeId());
				if(fundTransactionOutput!=null&&NumberUtility.areIntegerValuesMatching(fundTransactionOutput.getOrderTypeId(), OrderType.SELL.getTypeId()))
				{
					businessFundTransaction.setOrderStatus(OrderType.SELL.getType());
				}
				else if(fundTransactionOutput!=null&&NumberUtility.areIntegerValuesMatching(fundTransactionOutput.getOrderTypeId(), OrderType.BUY.getTypeId()))
				{
					businessFundTransaction.setOrderStatus(OrderType.BUY.getType());
				}
				else
				{
					businessFundTransaction.setOrderStatus(OrderType.OTHER.getType());
				}				
				businessFundTransaction.setOrderStatusId(orderStatus.getTypeId());
				businessFundTransaction.setOrderStatus(orderStatus.getType());
				businessFundTransactions.add(businessFundTransaction);
			}
			
		}
		return businessFundTransactions;
	}

	@Override
	protected void setConsumer(BusinessFundTransaction businessFundTransaction) {
		this.consumer=getFundTransactionsApiConsumer;
		
	}

}
