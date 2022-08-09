package innovitics.azimut.businessmodels;

public class Document {

	private String name;
	private String size;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Document [name=" + name + ", size=" + size + ", url=" + url + "]";
	}
	
	
	
	
}
