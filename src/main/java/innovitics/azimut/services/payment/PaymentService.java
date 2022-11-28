package innovitics.azimut.services.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.payment.PaymentTransactionRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.crosslayerenums.Action;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.PaymentTransactionStatus;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.PaymentTransactionParentSpecification;

@Service
public class PaymentService extends AbstractService<PaymentTransaction,String> {

	@Autowired PaymentTransactionRepository paymentTransactionRepository; 
	@Autowired PaymentTransactionParentSpecification paymentTransactionParentSpecification;
	@Autowired ListUtility<PaymentTransaction> paymentTransactionListUtility;
	
	private void populateMap(PaymentTransaction  paymentTransaction)
	{
		if(paymentTransaction!=null&&StringUtility.isStringPopulated(paymentTransaction.getParameterNames())&&StringUtility.isStringPopulated(paymentTransaction.getParameterValues()))
		{
			Map<String,String> transitional=new HashMap<String,String>();
			List<String> parameterNames=StringUtility.splitStringUsingCharacter(paymentTransaction.getParameterNames(),StringUtility.COMMA);
			List<String> parameterValues=StringUtility.splitStringUsingCharacter(paymentTransaction.getParameterValues(),StringUtility.COMMA);
			
			if(parameterNames.size()==parameterValues.size())
			{
				for(int i=0;i<parameterNames.size();i++)
				{
					transitional.put(parameterNames.get(i),parameterValues.get(i));
				}
				paymentTransaction.setKeyValueMap(transitional);
			}
		}
		
	}
	
	public PaymentTransaction addPaymentTransaction(User user,Double amount,PaymentGateway paymentGateway,Long currencyId,Integer action,String parameterNames,String parameterValues)
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		paymentTransaction.setUser(user);
		paymentTransaction.setTransactionAmount(amount);
		paymentTransaction.setPaymentGateway(paymentGateway.getId());
		paymentTransaction.setStatus(PaymentTransactionStatus.I.getStatusId());
		paymentTransaction.setCurrencyId(currencyId);
		paymentTransaction.setAction(action);
		paymentTransaction.setCreatedAt(new Date());
		paymentTransaction.setUpdatedAt(new Date());
		paymentTransaction.setParameterNames(parameterNames);
		paymentTransaction.setParameterValues(parameterValues);		
		return this.saveTransaction(paymentTransaction);
	}
	
	
	
	public PaymentTransaction saveTransaction(PaymentTransaction  paymentTransaction)
	{
		return this.paymentTransactionRepository.save(paymentTransaction);
	}
	
	
	public PaymentTransaction updatePaymentTransaction(PaymentTransaction  paymentTransaction)
	{
		paymentTransaction.setUpdatedAt(new Date());
		return this.paymentTransactionRepository.save(paymentTransaction);
	}
	
	public	PaymentTransaction getTransactionById(Long id)
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id,SearchOperation.EQUAL, null));
		paymentTransaction= this.paymentTransactionRepository.findOne(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList)).get();

		this.populateMap(paymentTransaction);
		
		return paymentTransaction;
	}
	
	public PaymentTransaction getTransactionByReferenceId(String referenceTransactionId,PaymentGateway paymentGateway)
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("paymentGateway", paymentGateway.getId(),SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("referenceTransactionId", referenceTransactionId,SearchOperation.EQUAL, null));
		paymentTransaction= this.paymentTransactionRepository.findOne(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList)).get();
		this.populateMap(paymentTransaction);
		return paymentTransaction;

	}
	public PaymentTransaction getTransactionByReferenceId(String referenceTransactionId,PaymentGateway paymentGateway,Long transactionId)
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", transactionId,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("paymentGateway", paymentGateway.getId(),SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("referenceTransactionId", referenceTransactionId,SearchOperation.EQUAL, null));
		paymentTransaction= this.paymentTransactionRepository.findOne(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList)).get();
		this.populateMap(paymentTransaction);
		return paymentTransaction;

	}
	public	List<PaymentTransaction> getTransactionByUser(Long userId,String[] paymentTransactionStatuses,PaymentGateway paymentGateway)
	{
		List<PaymentTransaction> paymentTransactions=new ArrayList<PaymentTransaction>();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", userId,SearchOperation.EQUAL, "user"));
		searchCriteriaList.add(new SearchCriteria("paymentGateway", paymentGateway.getId(),SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("status", this.arrayUtility.generateObjectListFromObjectArray(paymentTransactionStatuses),SearchOperation.NOT_IN, null));

		paymentTransactions= this.paymentTransactionRepository.findAll(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList));
		
		if(this.paymentTransactionListUtility.isListPopulated(paymentTransactions))
		{
			for(PaymentTransaction paymentTransaction:paymentTransactions)
			{
				this.populateMap(paymentTransaction);
			}
		}
		return paymentTransactions;
	}
	
}
