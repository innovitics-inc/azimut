package innovitics.azimut.rest.entities.teacomputers;

public class LookUpInput extends TeaComputerInput{

	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "LookUpInput [typeId=" + typeId + "]";
	}
	
	
}
