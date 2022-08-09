package innovitics.azimut.rest.models.valify;

public class ValifyOCRPassportResponse extends ValifyOCRIdResponse{

	private ValifyPassportResult result;

	public ValifyPassportResult getResult() {
		return result;
	}

	public void setResult(ValifyPassportResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ValifyOCRPassportResponse [result=" + result + "]";
	}
	
	
	
}
