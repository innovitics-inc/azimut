package innovitics.azimut.models;

public enum OTPMethod {

	MAIL(1,"mail"),
	SMS(2,"sms"),
	PUSH(3,"push")
	;

	OTPMethod(int methodId, String method) {
		this.methodId=methodId;
		this.method=method;
	}

	private final int methodId;
	private final String method;
	public int getMethodId() {
		return methodId;
	}
	public String getMethod() {
		return method;
	}
	

}
