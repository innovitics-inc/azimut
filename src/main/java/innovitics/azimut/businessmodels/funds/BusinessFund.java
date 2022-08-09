package innovitics.azimut.businessmodels.funds;

import java.util.List;
import java.util.Map;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.Document;
import innovitics.azimut.businessmodels.PortfolioManager;
import innovitics.azimut.businessmodels.ReceivingEntity;

public class BusinessFund extends BaseBusinessEntity{

	private String id;
	private String image;
	private String name;
	private String type;
	private String amount;
	private String lastUpdateDate;
	private String ric;
	private String bbgTicker;
	private String redemption;
	private String subscription;
	private String investmentObjectives;
	private Map <String,String> fundDetails;
	private String performancePortfolio;
	private String investmentGuideLines;
	private List<ReceivingEntity> receivingEntities;
	private List<PortfolioManager> portfolioManagers;
	private List<Document> relatedDocuments;
	private String currency;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getRic() {
		return ric;
	}
	public void setRic(String ric) {
		this.ric = ric;
	}
	public String getBbgTicker() {
		return bbgTicker;
	}
	public void setBbgTicker(String bbgTicker) {
		this.bbgTicker = bbgTicker;
	}
	public String getRedemption() {
		return redemption;
	}
	public void setRedemption(String redemption) {
		this.redemption = redemption;
	}
	public String getSubscription() {
		return subscription;
	}
	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}
	public String getInvestmentObjectives() {
		return investmentObjectives;
	}
	public void setInvestmentObjectives(String investmentObjectives) {
		this.investmentObjectives = investmentObjectives;
	}
	public Map<String, String> getFundDetails() {
		return fundDetails;
	}
	public void setFundDetails(Map<String, String> fundDetails) {
		this.fundDetails = fundDetails;
	}
	public String getPerformancePortfolio() {
		return performancePortfolio;
	}
	public void setPerformancePortfolio(String performancePortfolio) {
		this.performancePortfolio = performancePortfolio;
	}
	public String getInvestmentGuideLines() {
		return investmentGuideLines;
	}
	public void setInvestmentGuideLines(String investmentGuideLines) {
		this.investmentGuideLines = investmentGuideLines;
	}
	public List<ReceivingEntity> getReceivingEntities() {
		return receivingEntities;
	}
	public void setReceivingEntities(List<ReceivingEntity> receivingEntities) {
		this.receivingEntities = receivingEntities;
	}
	public List<PortfolioManager> getPortfolioManagers() {
		return portfolioManagers;
	}
	public void setPortfolioManagers(List<PortfolioManager> portfolioManagers) {
		this.portfolioManagers = portfolioManagers;
	}
	public List<Document> getRelatedDocuments() {
		return relatedDocuments;
	}
	public void setRelatedDocuments(List<Document> relatedDocuments) {
		this.relatedDocuments = relatedDocuments;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Override
	public String toString() {
		return "BusinessFund [id=" + id + ", image=" + image + ", name=" + name + ", type=" + type + ", amount="
				+ amount + ", lastUpdateDate=" + lastUpdateDate + ", ric=" + ric + ", bbgTicker=" + bbgTicker
				+ ", redemption=" + redemption + ", subscription=" + subscription + ", investmentObjectives="
				+ investmentObjectives + ", fundDetails=" + fundDetails + ", performancePortfolio="
				+ performancePortfolio + ", investmentGuideLines=" + investmentGuideLines + ", receivingEntities="
				+ receivingEntities + ", portfolioManagers=" + portfolioManagers + ", relatedDocuments="
				+ relatedDocuments + ", currency=" + currency + "]";
	}
	
	
}
