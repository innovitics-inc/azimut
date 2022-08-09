package innovitics.azimut.utilities.businessutilities;

public enum BusinessSearchOperation {

	SEARCH("SEARCH",0),
	FILTER("FILTER",1);
	
	BusinessSearchOperation(String operation, int code) {
		this.code = code;
		this.operation = operation;
		// TODO Auto-generated constructor stub
	}
	private final int code;
	private final String operation;
	public int getCode() {
		return code;
	}
	public String getOperation() {
		return operation;
	}
	
	
	
}
