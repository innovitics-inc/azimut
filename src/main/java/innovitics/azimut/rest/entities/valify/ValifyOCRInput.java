package innovitics.azimut.rest.entities.valify;

public class ValifyOCRInput extends ValifyInput{
	
	private String frontImage;
	private String backImage;
	private String image;
	private String doucmentType;
	public String getFrontImage() {
		return frontImage;
	}
	public void setFrontImage(String frontImage) {
		this.frontImage = frontImage;
	}
	public String getBackImage() {
		return backImage;
	}
	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDoucmentType() {
		return doucmentType;
	}
	public void setDoucmentType(String doucmentType) {
		this.doucmentType = doucmentType;
	}
	
	
	
}
