package innovitics.azimut.businessmodels.trading;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BaseAzimutTrading extends BaseBusinessEntity {

	private Long orderTypeId;
	private Double orderValue;
	private Integer Quantity;
    private Long currencyId;
    private String accountNo;
    private Long orderId;
    private MultipartFile injectionDocument;
	
	public Long getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public Double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Double orderValue) {
		this.orderValue = orderValue;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public MultipartFile getInjectionDocument() {
		return injectionDocument;
	}
	public void setInjectionDocument(MultipartFile injectionDocument) {
		this.injectionDocument = injectionDocument;
	}
	
	
}