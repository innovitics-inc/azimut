package innovitics.azimut.models.payment;

import java.util.Date;

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
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private Date deletedAt;
	
	private String message;

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
 
	
}
