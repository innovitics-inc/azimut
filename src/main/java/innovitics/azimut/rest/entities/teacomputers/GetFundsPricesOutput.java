package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetFundsPricesOutput extends TeaComputerOutput{

	List<FundPriceOutput> fundPriceOutputs;

	public List<FundPriceOutput> getFundPriceOutputs() {
		return fundPriceOutputs;
	}

	public void setFundPriceOutputs(List<FundPriceOutput> fundPriceOutputs) {
		this.fundPriceOutputs = fundPriceOutputs;
	}
	
	
	
}
