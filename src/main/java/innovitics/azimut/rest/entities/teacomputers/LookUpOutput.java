package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class LookUpOutput extends TeaComputerOutput{

	List<LookUpOutputs> outputs;

	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	

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
