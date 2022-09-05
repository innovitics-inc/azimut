package innovitics.azimut.businessmodels.funds;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessClientFund extends BaseBusinessEntity {
	

	private Double  quantity;
	private Double avgcost;
	private String tradePrice;
	private Double availableToBuy;
	private Double availableToSell;
	private Long fundId;
	private String fundName;
	private String fundType;
	private Long currencyId;
	private String currencyName;
	private Double currencyRate;
	private Double totalAmount;
	private String lastPriceUpdateDate;
	private String logo;
	
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
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getLastPriceUpdateDate() {
		return lastPriceUpdateDate;
	}
	public void setLastPriceUpdateDate(String lastPriceUpdateDate) {
		this.lastPriceUpdateDate = lastPriceUpdateDate;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
	

}
