package innovitics.azimut.rest.entities.teacomputers;

public class GetClientBankAccountsInput extends TeaComputerInput {

	private Long accountId;
	private Boolean isActive;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
