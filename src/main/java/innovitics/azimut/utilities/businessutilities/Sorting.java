package innovitics.azimut.utilities.businessutilities;

public enum Sorting {

	ASC("ASC",0),
	DESC("DESC",1);
	
	Sorting(String order, int code) {
		this.code = code;
		this.order = order;
		// TODO Auto-generated constructor stub
	}
	private final int code;
	private final String order;
	public int getCode() {
		return code;
	}
	public String getOrder() {
		return order;
	}
}
