package innovitics.azimut.rest.models.valify;

public class ValifyFacialImageResponse extends ValifyResponse{

	private ValifyFacialImageResult result;

	public ValifyFacialImageResult getResult() {
		return result;
	}

	public void setResult(ValifyFacialImageResult valifyFacialImageResult) {
		this.result = valifyFacialImageResult;
	}

	@Override
	public String toString() {
		return "ValifyFacialImageResponse [valifyFacialImageResult=" + result + "]";
	}
	
	
	
}
