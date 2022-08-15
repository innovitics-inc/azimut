package innovitics.azimut.utilities.crosslayerenums;

public enum AzimutEntityType {

	COUNTRY(1L,"Country","GetCountries"),
	CITY(2L,"City","GetCities"),
	ID(3L,"ID","GetCountries"),
	NATIONALITY(4L,"Nationality","GetNationalities"),
	DISTRICT(5L,"District","GetCountries"),
	FUND(6L,"Fund","GetCountries"),
	SEX(7L,"Sex","GetCountries"),
	ORDER_TYPE(8L,"Order Type","GetCountries"),
	CLIENT_STATUS(9L,"Client Status","GetCountries"),
	CLIENT_TYPE(10L,"Client Type","GetCountries"),
	CURRENCY(11L,"Currency Type","GetCurrencies"),
	BANK(11L,"Currency Type","GetBanks"),
	BRANCH(11L,"Currency Type","GetBranches")

	
	;

	AzimutEntityType(Long typeId, String type,String param) {
		this.typeId=typeId;
		this.type=type;
		this.param=param;
	}

	private final Long typeId;
	private final String type;
	private final String param;
	
	public Long getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	public String getParam() {
		return param;
	}
	
}
