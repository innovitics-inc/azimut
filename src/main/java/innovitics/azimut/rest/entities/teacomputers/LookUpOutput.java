package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class LookUpOutput extends TeaComputerOutput{

	List<LookUpOutputs> outputs;

	public List<LookUpOutputs> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<LookUpOutputs> outputs) {
		this.outputs = outputs;
	}

	@Override
	public String toString() {
		return "LookUpOutput [outputs=" + outputs + "]";
	}
	
	
}
