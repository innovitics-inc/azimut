package innovitics.azimut.businessmodels.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.businessmodels.funds.BusinessClientFund;
import innovitics.azimut.models.azimutdetails.AzimutDetails;
import innovitics.azimut.models.teacomputers.Currency;
import innovitics.azimut.utilities.CustomJsonRootName;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
@CustomJsonRootName(plural = "azAccounts", singular = "azAccount")
public class BusinessAzimutClient extends BusinessUser{

	private BusinessClientCashBalance businessClientCashBalance;
	private List<BusinessClientCashBalance> businessClientCashBalances;
	private List<BusinessTransaction> transactions;
	private List<BusinessClientBankAccountDetails> bankList;
	private BusinessClientBankAccountDetails bankAccountDetails;
	private Double pendingAmount;
	private Double balance;
	private String lastTransactionDate;
	private String balanceCurrency;
	private Double totalPendingAmount;
	private String tPACurrency;
	private List<AzimutAccount> azimutAccounts;
	private BusinessAzimutDataLookup lookupData;
	private BusinessClientBankAccountDetails [] clientBankAccounts;
	private Long entityTypeId;
	private String param;
	private AzimutDetails azimutDetails;
	private List<BusinessClientFund> businessClientFunds;
	private PaginatedEntity<BusinessClientFund> paginatedBusinessClientFunds;
	private BigDecimal totalPosition;	
	private List<EportfolioDetail> eportfolioDetails;
	private String reportType;
	private Long currencyId;
	private List<Currency> currency;
	private Boolean owned;
	
	public Double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(Double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getLastTransactionDate() {
		return lastTransactionDate;
	}

	public void setLastTransactionDate(String lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	public String getBalanceCurrency() {
		return balanceCurrency;
	}

	public void setBalanceCurrency(String balanceCurrency) {
		this.balanceCurrency = balanceCurrency;
	}

	public Double getTotalPendingAmount() {
		return totalPendingAmount;
	}

	public void setTotalPendingAmount(Double totalPendingAmount) {
		this.totalPendingAmount = totalPendingAmount;
	}

	public String gettPACurrency() {
		return tPACurrency;
	}

	public void settPACurrency(String tPACurrency) {
		this.tPACurrency = tPACurrency;
	}

	public BusinessClientCashBalance getBusinessClientCashBalance() {
		return businessClientCashBalance;
	}

	public void setBusinessClientCashBalance(BusinessClientCashBalance businessClientCashBalance) {
		this.businessClientCashBalance = businessClientCashBalance;
	}

	public List<BusinessClientCashBalance> getBusinessClientCashBalances() {
		return businessClientCashBalances;
	}

	public void setBusinessClientCashBalances(List<BusinessClientCashBalance> businessClientCashBalances) {
		this.businessClientCashBalances = businessClientCashBalances;
	}

	public List<BusinessTransaction> getBusinessTransactions() {
		return transactions;
	}

	public void setBusinessTransactions(List<BusinessTransaction> transactions) {
		this.transactions = transactions;
	}

	public List<BusinessClientBankAccountDetails> getBankList() {
		return bankList;
	}

	public void setBankList(List<BusinessClientBankAccountDetails> bankList) {
		this.bankList = bankList;
	}

	public BusinessClientBankAccountDetails getBankAccountDetails() {
		return bankAccountDetails;
	}

	public void setBankAccountDetails(BusinessClientBankAccountDetails bankAccountDetails) {
		this.bankAccountDetails = bankAccountDetails;
	}

	public List<AzimutAccount> getAzimutAccounts() {
		return azimutAccounts;
	}

	public void setAzimutAccounts(List<AzimutAccount> azimutAccounts) {
		this.azimutAccounts = azimutAccounts;
	}
	
	public Long getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public BusinessAzimutDataLookup getLookupData() {
		return lookupData;
	}

	public void setLookupData(BusinessAzimutDataLookup lookupData) {
		this.lookupData = lookupData;
	}

	public BusinessClientBankAccountDetails[] getClientBankAccounts() {
		return clientBankAccounts;
	}

	public void setClientBankAccounts(BusinessClientBankAccountDetails[] clientBankAccounts) {
		this.clientBankAccounts = clientBankAccounts;
	}


	public AzimutDetails getAzimutDetails() {
		return azimutDetails;
	}

	public void setAzimutDetails(AzimutDetails azimutDetails) {
		this.azimutDetails = azimutDetails;
	}


	public List<BusinessClientFund> getBusinessClientFunds() {
		return businessClientFunds;
	}

	public void setBusinessClientFunds(List<BusinessClientFund> businessClientFunds) {
		this.businessClientFunds = businessClientFunds;
	}

	public PaginatedEntity<BusinessClientFund> getPaginatedBusinessClientFunds() {
		return paginatedBusinessClientFunds;
	}

	public void setPaginatedBusinessClientFunds(PaginatedEntity<BusinessClientFund> paginatedBusinessClientFunds) {
		this.paginatedBusinessClientFunds = paginatedBusinessClientFunds;
	}

	public BigDecimal getTotalPosition() {
		return totalPosition;
	}

	public void setTotalPosition(BigDecimal totalPosition) {
		this.totalPosition = totalPosition;
	}

	public List<EportfolioDetail> getEportfolioDetails() {
		return eportfolioDetails;
	}

	public void setEportfolioDetails(List<EportfolioDetail> eportfolioDetails) {
		this.eportfolioDetails = eportfolioDetails;
	}

	public String getReportType() {
		return reportType;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public List<Currency> getCurrency() {
		return currency;
	}

	public void setCurrency(List<Currency> currency) {
		this.currency = currency;
	}

	public Boolean getOwned() {
		return owned;
	}

	public void setOwned(Boolean owned) {
		this.owned = owned;
	}

	public BusinessAzimutClient(BusinessClientBankAccountDetails[] clientBankAccounts) {
		super();
		this.clientBankAccounts = clientBankAccounts;
	}

	public BusinessAzimutClient(AzimutEntityType azimutEntityType) {
		super();
		this.entityTypeId=azimutEntityType.getTypeId();
		this.param=azimutEntityType.getParam();
	}
	
	
		public BusinessAzimutClient(AzimutDetails azimutDetails) {
		super();
		this.azimutDetails = azimutDetails;
	}

		public BusinessAzimutClient() 
	{
	
	}

	@Override
	public String toString() {
		return "BusinessAzimutClient [businessClientCashBalance=" + businessClientCashBalance + ", transactions="
				+ transactions + ", bankList=" + bankList + ", bankAccountDetails=" + bankAccountDetails
				+ ", pendingAmount=" + pendingAmount + ", balance=" + balance + ", lastTransactionDate="
				+ lastTransactionDate + ", balanceCurrency=" + balanceCurrency + ", totalPendingAmount="
				+ totalPendingAmount + ", tPACurrency=" + tPACurrency + ", id=" + id + ", userPhone=" + userPhone
				+ ", password=" + password + ", secondPassword=" + secondPassword + ", oldPassword=" + oldPassword
				+ ", newPassword=" + newPassword + ", deviceId=" + deviceId + ", userId=" + userId + ", userIdType="
				+ userIdType + ", nickName=" + nickName + ", emailAddress=" + emailAddress + ", countryPhoneCode="
				+ countryPhoneCode + ", phoneNumber=" + phoneNumber + ", profilePicture=" + profilePicture
				+ ", signedPdf=" + signedPdf + ", picturePath=" + picturePath + ", pdfPath=" + pdfPath
				+ ", isChangeNoApproved=" + isChangeNoApproved + ", verificationPercentage=" + verificationPercentage
				+ ", isVerified=" + isVerified + ", migrated=" + migrated + ", businessFlow=" + businessFlow
				+ ", flowId=" + flowId + ", countryCode=" + countryCode + ", file=" + file + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", documentURL=" + documentURL
				+ ", documentName=" + documentName + ", documentSize=" + documentSize + ", documentSubDirectory="
				+ documentSubDirectory + ", searchFromDate=" + searchFromDate + ", searchToDate=" + searchToDate
				+ ", searchDates=" + searchDates + ", azIdType=" + azIdType + ", azId=" + azId + ", sorting=" + sorting
				+ ", bankId=" + bankId + ", accountId=" + accountId + ", language=" + language + "]";
	}

	


	
	
}
