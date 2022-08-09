package innovitics.azimut.rest.models.valify;

public class ValifyFacialImageResult  extends ValifyResult{
	    
	  private  boolean is_similar; 
	  private  float confidence;
	public boolean isIs_similar() {
		return is_similar;
	}
	public void setIs_similar(boolean is_similar) {
		this.is_similar = is_similar;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
	@Override
	public String toString() {
		return "ValifyFacialImageResult [is_similar=" + is_similar + ", confidence=" + confidence + "]";
	}
	  
	  
	  
	    
}
