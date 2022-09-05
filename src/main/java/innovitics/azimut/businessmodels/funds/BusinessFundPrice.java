package innovitics.azimut.businessmodels.funds;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;

@CustomJsonRootName(plural = "fundPrices", singular = "fundPrice")
public class BusinessFundPrice extends BaseBusinessEntity {

	private Long fundId;
	private Long id;
	private Double nav;
	private Double tradePrice;
	private String priceDate;
	private Long teacomputerId;
	private String logo;
	
	
	
	
	
	public BusinessFundPrice(Long fundId, Long id, Double nav, Double tradePrice, String priceDate) {
		super();
		this.fundId = fundId;
		this.id = id;
		this.nav = nav;
		this.tradePrice = tradePrice;
		this.priceDate = priceDate;
	}
	

	public BusinessFundPrice() {
			}
	
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getNav() {
		return nav;
	}
	public void setNav(Double nav) {
		this.nav = nav;
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
	
	public Long getTeacomputerId() {
		return teacomputerId;
	}


	public void setTeacomputerId(Long teacomputerId) {
		this.teacomputerId = teacomputerId;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	@Override
	public String toString() {
		return "BusinessFundPrice [fundId=" + fundId + ", id=" + id + ", nav=" + nav + ", tradePrice=" + tradePrice
				+ ", priceDate=" + priceDate + ", searchFromDate=" + searchFromDate + ", searchToDate=" + searchToDate
				+ ", azIdType=" + azIdType + ", azId=" + azId + ", sorting=" + sorting + ", bankId=" + bankId + "]";
	}



	
}
