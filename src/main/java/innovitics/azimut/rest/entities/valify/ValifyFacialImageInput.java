package innovitics.azimut.rest.entities.valify;

public class ValifyFacialImageInput extends ValifyInput{

	private String firstImage;
	private String secondImage;
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getSecondImage() {
		return secondImage;
	}
	public void setSecondImage(String secondImage) {
		this.secondImage = secondImage;
	}
	
}
