package innovitics.azimut.utilities.crosslayerenums;

public enum AzimutEntityType {

	COUNTRY(1L,"Country"),
	CITY(2L,"City"),
	ID(3L,"ID"),
	NATIONALITY(4L,"Nationality"),
	DISTRICT(5L,"District"),
	FUND(6L,"Fund"),
	SEX(7L,"Sex"),
	ORDER_TYPE(8L,"Order Type"),
	CLIENT_STATUS(9L,"Client Status"),
	CLIENT_TYPE(10L,"Client Type"),
	
	;

	AzimutEntityType(Long typeId, String type) {
		this.typeId=typeId;
		this.type=type;
	}

	private final Long typeId;
	private final String type;
	
	public Long getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
}
