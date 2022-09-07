package innovitics.azimut.rest.models.teacomputers;

public class GetFundTransactionsRequest extends TeaComputerRequest{

	private Long fundID;

	public Long getFundID() {
		return fundID;
	}

	public void setFundID(Long fundID) {
		this.fundID = fundID;
	}
	
}
