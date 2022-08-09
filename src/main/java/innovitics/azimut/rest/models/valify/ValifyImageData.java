package innovitics.azimut.rest.models.valify;

public class ValifyImageData extends ValifyData{

	 private String front_img;
	 private String back_img;	 
	 private String img;

	public String getFront_img() {
		return front_img;
	}
	public void setFront_img(String front_img) {
		this.front_img = front_img;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getBack_img() {
		return back_img;
	}
	public void setBack_img(String back_img) {
		this.back_img = back_img;
	}

}
