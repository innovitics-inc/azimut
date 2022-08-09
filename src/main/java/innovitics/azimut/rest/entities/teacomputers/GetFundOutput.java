package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetFundOutput extends TeaComputerOutput{

	
	List<GetFundByIdOutput> getFundOutputs;

	GetFundByIdOutput getFundByIdOutput;

	public List<GetFundByIdOutput> getGetFundOutputs() {
		return getFundOutputs;
	}

	public void setGetFundOutputs(List<GetFundByIdOutput> getFundOutputs) {
		this.getFundOutputs = getFundOutputs;
	}

	public GetFundByIdOutput getGetFundByIdOutput() {
		return getFundByIdOutput;
	}

	public void setGetFundByIdOutput(GetFundByIdOutput getFundByIdOutput) {
		this.getFundByIdOutput = getFundByIdOutput;
	}
	
	
	
}
