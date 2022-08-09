package innovitics.azimut.businessmodels;

import java.util.Date;

import innovitics.azimut.utilities.CustomJsonRootName;
import innovitics.azimut.utilities.crosslayerenums.OrderType;
import innovitics.azimut.utilities.crosslayerenums.TransactionStatus;
import innovitics.azimut.utilities.crosslayerenums.TransactionType;
@CustomJsonRootName(plural = "transactions", singular = "transaction")
public class BusinessTransaction extends BaseBusinessEntity {

	private TransactionType transactionType;
	private Double amount;
	private String currency;
	private String trxDate;
	private TransactionStatus transactionStatus;
	private OrderType orderType;
	private Integer status;
	private Integer type;
	private Integer order;
	
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "BusinessTransaction [transactionType=" + transactionType + ", amount=" + amount + ", currency="
				+ currency + ", trxDate=" + trxDate + ", transactionStatus=" + transactionStatus + ", orderType="
				+ orderType + ", status=" + status + ", type=" + type + ", order=" + order + ", searchFromDate="
				+ searchFromDate + ", searchToDate=" + searchToDate + ", azIdType=" + azIdType + ", azId=" + azId
				+ ", sorting=" + sorting + "]";
	}
	

	
	
}
