package innovitics.azimut.rest.entities.valify;

public class ValifyFacialImageOutput extends ValifyOutput{
	
	private boolean isSimilar; 
	private float confidence;
	public boolean isSimilar() {
		return isSimilar;
	}
	public void setSimilar(boolean isSimilar) {
		this.isSimilar = isSimilar;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
	

}
