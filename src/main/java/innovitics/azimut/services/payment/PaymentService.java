package innovitics.azimut.services.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.payment.PaymentTransactionRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.crosslayerenums.PaymentGateway;
import innovitics.azimut.utilities.crosslayerenums.PaymentTransactionStatus;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.PaymentTransactionParentSpecification;

@Service
public class PaymentService extends AbstractService<PaymentTransaction,String> {

	@Autowired PaymentTransactionRepository paymentTransactionRepository; 
	@Autowired PaymentTransactionParentSpecification paymentTransactionParentSpecification;
	
	public PaymentTransaction addPayment(User user,Double amount,PaymentGateway paymentGateway)
	{
		PaymentTransaction paymentTransaction=new PaymentTransaction();
		paymentTransaction.setUser(user);
		paymentTransaction.setTransactionAmount(amount);
		paymentTransaction.setPaymentGateway(paymentGateway.getId());
		paymentTransaction.setStatus(PaymentTransactionStatus.I.getStatusId());
		
		return this.saveTransaction(paymentTransaction);
	}
	
	
	
	public PaymentTransaction saveTransaction(PaymentTransaction  paymentTransaction)
	{
		return this.paymentTransactionRepository.save(paymentTransaction);
	}
	
	
	public PaymentTransaction updateTransaction(PaymentTransaction  paymentTransaction)
	{
		paymentTransaction.setUpdatedAt(new Date());
		return this.paymentTransactionRepository.save(paymentTransaction);
	}
	
	public	PaymentTransaction getTransactionById(Long id)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id,SearchOperation.EQUAL, null));
		return this.paymentTransactionRepository.findOne(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList)).get();
	}
	
	public PaymentTransaction getTransactionByReferneceId(String referenceTransactionId,PaymentGateway paymentGateway)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("paymentGateway", paymentGateway.getId(),SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("referenceTransactionId", referenceTransactionId,SearchOperation.EQUAL, null));
		return this.paymentTransactionRepository.findOne(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList)).get();
	}
	public PaymentTransaction getTransactionByReferneceId(String referenceTransactionId,PaymentGateway paymentGateway,Long transactionId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", transactionId,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("paymentGateway", paymentGateway.getId(),SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("referenceTransactionId", referenceTransactionId,SearchOperation.EQUAL, null));
		return this.paymentTransactionRepository.findOne(this.paymentTransactionParentSpecification.findByCriteria(searchCriteriaList)).get();
	}
	
}
