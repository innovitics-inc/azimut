package innovitics.azimut.rest.models.teacomputers;

public class ClientFundResponse extends TeaComputerResponse{
	private String clientName;
	private String mobile;
	private Double  quantity;
	private Double avgcost;
	private String tradePrice;
	private Double availableToBuy;
	private Double availableToSell;
	private String  statusName;
	private String clientStatus;
	private Long fundID;
	private String certificateName;
	private String assetClass;
	private Long currencyID;
	private String currencyName;
	private Double currencyRate;
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getAvgcost() {
		return avgcost;
	}
	public void setAvgcost(Double avgcost) {
		this.avgcost = avgcost;
	}
	public String getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}
	public Double getAvailableToBuy() {
		return availableToBuy;
	}
	public void setAvailableToBuy(Double availableToBuy) {
		this.availableToBuy = availableToBuy;
	}
	public Double getAvailableToSell() {
		return availableToSell;
	}
	public void setAvailableToSell(Double availableToSell) {
		this.availableToSell = availableToSell;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getClientStatus() {
		return clientStatus;
	}
	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	public Long getFundID() {
		return fundID;
	}
	public void setFundID(Long fundID) {
		this.fundID = fundID;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public Long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(Long currencyID) {
		this.currencyID = currencyID;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Double getCurrencyRate() {
		return currencyRate;
	}
	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}
	@Override
	public String toString() {
		return "GetClientFundsResponse [clientName=" + clientName + ", mobile=" + mobile + ", quantity=" + quantity
				+ ", avgcost=" + avgcost + ", tradePrice=" + tradePrice + ", availableToBuy=" + availableToBuy
				+ ", availableToSell=" + availableToSell + ", statusName=" + statusName + ", clientStatus="
				+ clientStatus + ", fundID=" + fundID + ", certificateName=" + certificateName + ", assetClass="
				+ assetClass + ", currencyID=" + currencyID + ", currencyName=" + currencyName + ", currencyRate="
				+ currencyRate + "]";
	}
	
}
