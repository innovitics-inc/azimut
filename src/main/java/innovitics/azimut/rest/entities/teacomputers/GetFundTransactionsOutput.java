package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetFundTransactionsOutput extends TeaComputerOutput{

	
	private List<FundTransactionOutput> fundTransactionOutputs;

	public List<FundTransactionOutput> getFundTransactionOutputs() {
		return fundTransactionOutputs;
	}

	public void setFundTransactionOutputs(List<FundTransactionOutput> fundTransactionOutputs) {
		this.fundTransactionOutputs = fundTransactionOutputs;
	}
	
}
