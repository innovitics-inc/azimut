package innovitics.azimut.businessmodels.user;

import java.util.List;
import java.util.Map;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.models.user.AzimutDataLookup;
import innovitics.azimut.utilities.CustomJsonRootName;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
@CustomJsonRootName(plural = "azAccounts", singular = "azAccount")
public class BusinessAzimutClient extends BusinessUser{

	private BusinessClientCashBalance businessClientCashBalance;
	private List<BusinessTransaction> transactions;
	private List<BusinessClientBankAccountDetails> bankList;
	private BusinessClientBankAccountDetails bankAccountDetails;
	private Double pendingAmount;
	private Long balance;
	private String lastTransactionDate;
	private String balanceCurrency;
	private Double totalPendingAmount;
	private String tPACurrency;
	private List<AzimutAccount> azimutAccounts;
	private BusinessAzimutDataLookup lookupData;
	private BusinessClientBankAccountDetails [] clientBankAccounts;
	private Long entityTypeId;
	private String param;

	
	public Double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(Double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
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

	public List<BusinessTransaction> getTransactions() {
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

	
	
	
	

	public BusinessAzimutClient(BusinessClientBankAccountDetails[] clientBankAccounts) {
		super();
		this.clientBankAccounts = clientBankAccounts;
	}

	public BusinessAzimutClient(AzimutEntityType azimutEntityType) {
		super();
		this.entityTypeId=azimutEntityType.getTypeId();
		this.param=azimutEntityType.getParam();
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
