package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetClientTransactionListApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.GetTransactionsInput;
import innovitics.azimut.rest.entities.teacomputers.GetTransactionsOutput;
import innovitics.azimut.rest.entities.teacomputers.TransactionOutput;
import innovitics.azimut.rest.models.teacomputers.GetTransactionsResponse;
import innovitics.azimut.rest.models.teacomputers.TransactionResponse;
import innovitics.azimut.utilities.crosslayerenums.OrderType;
import innovitics.azimut.utilities.crosslayerenums.TransactionOrderType;
import innovitics.azimut.utilities.crosslayerenums.TransactionStatus;
import innovitics.azimut.utilities.crosslayerenums.TransactionType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Service
public class GetTransactionsMapper extends RestMapper<GetTransactionsInput,GetTransactionsOutput,GetTransactionsResponse,BusinessTransaction>{

	@Autowired GetClientTransactionListApiConsumer getClientTransactionListApiConsumer;
	@Autowired ListUtility<TransactionOutput> listUtility;

	@Override
	BusinessTransaction consumeRestService(BusinessTransaction businessTransaction, String params) throws IntegrationException {
		// TODO Auto-generated method stub
	return null;
	}

	@Override
	List<BusinessTransaction> consumeListRestService(BusinessTransaction businessTransaction, String params) throws HttpClientErrorException, Exception {
	
		return  this.createListBusinessEntityFromOutput(this.getClientTransactionListApiConsumer.invoke(this.createInput(businessTransaction),TransactionResponse[].class, params));
	}

	@Override
	GetTransactionsInput createInput(BusinessTransaction businessTransaction) {
		GetTransactionsInput getTransactionsInput=new GetTransactionsInput();
		getTransactionsInput.setFromDate(businessTransaction.getSearchFromDate());
		getTransactionsInput.setToDate(businessTransaction.getSearchToDate());
		getTransactionsInput.setIdNumber(businessTransaction.getAzId());
		getTransactionsInput.setIdTypeId(businessTransaction.getAzIdType());
		return getTransactionsInput;
	}

	@Override
	BusinessTransaction createBusinessEntityFromOutput(GetTransactionsOutput getTransactionsOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<BusinessTransaction> createListBusinessEntityFromOutput(GetTransactionsOutput getTransactionsOutput) {
		List<BusinessTransaction> businessTransactions=new ArrayList<BusinessTransaction>();
		if( getTransactionsOutput!=null&&this.listUtility.isListPopulated(getTransactionsOutput.getTransactionOutputs()))	
		{
			for(TransactionOutput transactionOutput: getTransactionsOutput.getTransactionOutputs())
			{
				businessTransactions.add(this.getConversion(transactionOutput));
			}
			
		}
		return businessTransactions;
	}
	
	BusinessTransaction getConversion(TransactionOutput transactionOutput)
	{
		BusinessTransaction businessTransaction=new BusinessTransaction();
		if(transactionOutput!=null)
		{
			this.logger.info("TransactionOutput:::::"+ transactionOutput);
			businessTransaction.setAmount(transactionOutput.getTransValue()==null? null:Double.valueOf(transactionOutput.getTransValue()));
			businessTransaction.setTrxDate(transactionOutput.getTransDate());
			businessTransaction.setCurrency(transactionOutput.getCurrencyName());
			if(StringUtility.isStringPopulated(transactionOutput.getTransStatusName()))
			{
				if(transactionOutput.getTransStatusName().equals(TransactionStatus.PENDING.getStatus()))
				{
					businessTransaction.setTransactionStatus(TransactionStatus.PENDING);
				}
				else if(transactionOutput.getTransStatusName().equals(TransactionStatus.TIKCET.getStatus()))
				{
					businessTransaction.setTransactionStatus(TransactionStatus.TIKCET);
				}
				else if((transactionOutput.getTransStatusName().equals(TransactionStatus.CANCELED.getStatus())))
				{
					businessTransaction.setTransactionStatus(TransactionStatus.CANCELED);
				}
				else 
				{
					businessTransaction.setTransactionStatus(TransactionStatus.OTHER);
				}
			}
			if(StringUtility.isStringPopulated(transactionOutput.getTransTypeName()))
			{
				if(transactionOutput.getTransTypeName().equals(TransactionType.DEPOSIT.getType()))
				{
					businessTransaction.setTransactionType(TransactionType.DEPOSIT);
				}
				else if(transactionOutput.getTransTypeName().equals(TransactionType.SWIFT.getType()))
				{
					businessTransaction.setTransactionType(TransactionType.SWIFT);
				}
				else 
				{
					businessTransaction.setTransactionType(TransactionType.OTHER);
				}
			}
			if(StringUtility.isStringPopulated(transactionOutput.getOrderTypeName()))
			{
				if(transactionOutput.getOrderTypeName().equals(TransactionOrderType.WITHDRAW.getType()))
				{
					businessTransaction.setTransactionOrderType(TransactionOrderType.WITHDRAW);
				}
				else if(transactionOutput.getOrderTypeName().equals(TransactionOrderType.INJECT.getType()))
				{
					businessTransaction.setTransactionOrderType(TransactionOrderType.INJECT);
				}
				else  
				{
					businessTransaction.setOrderType(OrderType.OTHER);
				}
				
			}
			
		}
		return businessTransaction; 
		
	}

}
