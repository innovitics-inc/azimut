package innovitics.azimut.rest.models.teacomputers;

public class FundPriceResponse extends TeaComputerResponse{

    private Long fundID;
    private Double tradePrice;
    private String priceDate;
	public Long getFundID() {
		return fundID;
	}
	public void setFundID(Long fundID) {
		this.fundID = fundID;
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
	@Override
	public String toString() {
		return "FundPriceResponse [fundID=" + fundID + ", tradePrice=" + tradePrice + ", priceDate=" + priceDate
				+ ", Signature=" + Signature + ", Message=" + Message + ", ErrorCode=" + ErrorCode + ", status="
				+ status + ", message=" + message + "]";
	}

    
     
}
