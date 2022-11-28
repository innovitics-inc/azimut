package innovitics.azimut.businessmodels.trading;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "trading", singular = "trading")
public class BaseAzimutTrading extends BaseBusinessEntity {

	private Long orderTypeId;
	private Double orderValue;
	private Integer quantity;
    private Long currencyId;
    private Long accountId;
    private Long bankId;
    private Long orderId;
    private MultipartFile injectionDocument;
    private String userId;
    private UserBlockage userBlockage;
    private Long moduleTypeId;
    private String fileBytes;
    private Integer threshold;
    private Long transactionId;
    
	
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
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}	
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserBlockage getUserBlockage() {
		return userBlockage;
	}
	public void setUserBlockage(UserBlockage userBlockage) {
		this.userBlockage = userBlockage;
	}
	public Long getModuleTypeId() {
		return moduleTypeId;
	}
	public void setModuleTypeId(Long moduleTypeId) {
		this.moduleTypeId = moduleTypeId;
	}
	public String getFileBytes() {
		return fileBytes;
	}
	public void setFileBytes(String fileBytes) {
		this.fileBytes = fileBytes;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
