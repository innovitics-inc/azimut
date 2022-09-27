package innovitics.azimut.businessmodels.trading;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BaseAzimutTrading extends BaseBusinessEntity {

	private Long orderTypeId;
	private Long orderValue;
	private Integer Quantity;
	
	public Long getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public Long getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}
	
	
}
