package innovitics.azimut.rest.entities.teacomputers;

public class FundPriceOutput {

	private Long fundId;
    private Double tradePrice;
    private String priceDate;
	private String signature;
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public Double getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(Double tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(String priceDate) {
		this.priceDate = priceDate;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "FundPriceOutput [fundId=" + fundId + ", tradePrice=" + tradePrice + ", priceDate=" + priceDate
				+ ", signature=" + signature + "]";
	}
	
	
	
}
