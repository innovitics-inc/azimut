package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetEportfolioOutput extends TeaComputerOutput{
	private List<EportfolioOutput> eportfolioOutputs;

	public List<EportfolioOutput> getEportfolioOutputs() {
		return eportfolioOutputs;
	}

	public void setEportfolioOutputs(List<EportfolioOutput> eportfolioOutputs) {
		this.eportfolioOutputs = eportfolioOutputs;
	}
	
	
}
