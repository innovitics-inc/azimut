package innovitics.azimut.utilities.crosslayerenums;

public enum PaymentGateway {

	
	PAYTABS(1,"paytabs");
	
	PaymentGateway(int id, String name) {
		this.id=id;
		this.name=name;
	}

	private final long id;
	
	private final String name;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
	
}
