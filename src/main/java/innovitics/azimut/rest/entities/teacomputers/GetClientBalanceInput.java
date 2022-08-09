package innovitics.azimut.rest.entities.teacomputers;

public class GetClientBalanceInput extends TeaComputerInput {


	private String fundId;
	public String getFundId() {
		return fundId;
	}
	public void setFundId(String fundId) {
		this.fundId = fundId;
	}
	@Override
	public String toString() {
		return "GetClientBalanceInput [fundId=" + fundId + ", locale=" + locale + ", idNumber=" + idNumber
				+ ", idTypeId=" + idTypeId + ", username=" + username + ", password=" + password + "]";
	}

	
	
}
