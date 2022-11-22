package innovitics.azimut.models.payment;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.user.User;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="payment_transactions")
public class PaymentTransaction extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	@ManyToOne
	@JoinColumn(name="app_user_id",foreignKey = @javax.persistence.ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
	private User user;
	
	private Double transactionAmount;
	
	private String status;
	
	private String referenceTransactionId;
	
	private Long paymentGateway;
	
	private String paymentMethod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReferenceTransactionId() {
		return referenceTransactionId;
	}

	public void setReferenceTransactionId(String referenceTransactionId) {
		this.referenceTransactionId = referenceTransactionId;
	}

	public Long getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(Long paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
 
	
}
