package innovitics.azimut.rest.models.valify;

public class ValifyOCRNationalIdResponse extends ValifyOCRIdResponse{

	private ValifyNationalIdResult result;

	public ValifyNationalIdResult getResult() {
		return result;
	}

	public void setResult(ValifyNationalIdResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ValifyOCRNationalIdResponse [result=" + result + "]";
	}
	
	
}
