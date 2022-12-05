package innovitics.azimut.utilities.businessutilities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.models.user.User;
import innovitics.azimut.services.payment.PaymentService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.Action;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.TransactionOrderType;
import innovitics.azimut.utilities.crosslayerenums.TransactionStatus;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Component
public class PaymentTransactionUtility extends ParentUtility{

	
	@Autowired PaymentService paymentService;
	@Autowired ListUtility<PaymentTransaction> paymentTransactionListUtility;
	
	public PaymentTransaction addPaymentTransaction(User user,Double amount,PaymentGateway paymentGateway,Long currencyId,Integer action,String parameterNames,String parameterValues) throws BusinessException
	{
		try 
		{
			return this.paymentService.addPaymentTransaction(user, amount, paymentGateway, currencyId, action, parameterNames, parameterValues);
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleBusinessException(exception, ErrorCode.PAYMENT_TRANSACTION_NOT_SAVED);
		}
		
	}
	
	
	public PaymentTransaction updatePaymentTransaction(PaymentTransaction  paymentTransaction)
	{
		return this.paymentService.updatePaymentTransaction(paymentTransaction);
	}
	
	
	public	PaymentTransaction getTransactionById(Long id) throws BusinessException
	{
		try 
		{
			return this.paymentService.getTransactionById(id);		
		}
		catch(Exception exception)
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
			return null;
		}
	}
	
	public PaymentTransaction getTransactionByReferenceId(String referenceTransactionId,PaymentGateway paymentGateway) throws BusinessException
	{
		try 
		{
		return this.paymentService.getTransactionByReferenceId(referenceTransactionId, paymentGateway);
		}
		catch(Exception exception)
		{
			 this.exceptionHandler.getNullIfNonExistent(exception);
			 return null;
		}

	}
	public PaymentTransaction getTransactionByReferenceId(String referenceTransactionId,PaymentGateway paymentGateway,Long transactionId) throws BusinessException
	{
		try 
		{
	
		return this.paymentService.getTransactionByReferenceId(referenceTransactionId, paymentGateway, transactionId);
		}
		catch(Exception exception)
		{
			 this.exceptionHandler.getNullIfNonExistent(exception);
			 return null;
		}
	}
	
	
	public List<BusinessTransaction> getUserPaymentTransactionsAndConvertThemToBusinessTransactions(BusinessUser tokenizedBusinessUser,PaymentGateway paymentGateway) throws BusinessException
	{
		List<BusinessTransaction> businessPaymentTransactions=new ArrayList<BusinessTransaction>();
		
		List<PaymentTransaction> paymentTransactions=this.getTransactionByUser(tokenizedBusinessUser.getId(), StringUtility.INCLUDED_STATUSES, paymentGateway, StringUtility.RELEVANT_ACTIONS);
		
		if(this.paymentTransactionListUtility.isListPopulated(paymentTransactions))
		{
			for(PaymentTransaction paymentTransaction:paymentTransactions)
			{
				businessPaymentTransactions.add(this.convertPaymentTransactionToBusinessTransaction(paymentTransaction));
			}
		}
		
		
		return businessPaymentTransactions;
	}
	public PaymentTransaction getTransactionByUser(Long userId,String referenceTransactionId,PaymentGateway paymentGateway) throws BusinessException
	{
		try 
		{
	
		return this.paymentService.getTransactionByUser(userId,referenceTransactionId,paymentGateway);
		}
		catch(Exception exception)
		{
			 this.exceptionHandler.getNullIfNonExistent(exception);
			 return null;
		}
	}
	
	private	List<PaymentTransaction> getTransactionByUser(Long userId,String[] includedStatuses,PaymentGateway paymentGateway,Integer[] actions) throws BusinessException
	{
		try 
		{
	
		return this.paymentService.getTransactionsByUser(userId, includedStatuses, paymentGateway, actions);
		}
		catch(Exception exception)
		{
			 this.exceptionHandler.getNullIfNonExistent(exception);
			 return null;
		}
	}
	
	
	
	private	BusinessTransaction convertPaymentTransactionToBusinessTransaction(PaymentTransaction paymentTransaction)
	{
		BusinessTransaction businessTransaction=new BusinessTransaction();
	
		this.logger.info("PaymentTransaction:::"+paymentTransaction.toString());
		
		businessTransaction.setAmount(paymentTransaction.getTransactionAmount());		
		businessTransaction.setTrxDate(DateUtility.changeDatetoStringDate(paymentTransaction.getUpdatedAt(), "dd-MM-yyyy"));
		businessTransaction.setCurrency(CurrencyType.getById(paymentTransaction.getCurrencyId()).getType());
		
		this.logger.info("TransactionStatus::::"+TransactionStatus.getByPaymentStatusId(paymentTransaction.getStatus()));
		
		this.logger.info("Action::::"+Action.getById(paymentTransaction.getAction()));
		
		businessTransaction.setStatus(TransactionStatus.getByPaymentStatusId(paymentTransaction.getStatus()).getStatusId());				
		businessTransaction.setType(TransactionOrderType.getByOwnId(Action.getById(paymentTransaction.getAction()).getActionId()).getTypeId());
		
		return businessTransaction;
	}
		
	
}
