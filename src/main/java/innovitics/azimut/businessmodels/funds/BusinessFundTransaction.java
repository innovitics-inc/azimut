package innovitics.azimut.businessmodels.funds;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.crosslayerenums.OrderStatus;

public class BusinessFundTransaction extends BaseBusinessEntity{

	private String orderDate;
	private Double orderValue;
	private Integer quantity;
	private String orderType;
	private Integer orderTypeId;
	private String orderStatus;
	private Integer orderStatusId;
	private Long transactionId;
	private Long fundId;
	private OrderStatus status;
	
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BusinessFundTransaction [orderDate=" + orderDate + ", orderValue=" + orderValue + ", quantity="
				+ quantity + ", orderType=" + orderType + ", orderTypeId=" + orderTypeId + ", orderStatus="
				+ orderStatus + ", orderStatusId=" + orderStatusId + ", transactionId=" + transactionId + ", fundId="
				+ fundId + "]";
	}
	
	
	
}
