package innovitics.azimut.utilities.crosslayerenums;

public enum AzimutEntityType {

	COUNTRY(1L,"Country","GetCountry"),
	CITY(2L,"City","GetCity"),
	ID(3L,"ID","GetCountries"),
	NATIONALITY(4L,"Nationality","GetNationality"),
	DISTRICT(5L,"District","GetCountries"),
	FUND(6L,"Fund","GetCountries"),
	SEX(7L,"Sex","GetCountries"),
	ORDER_TYPE(8L,"Order Type","GetCountries"),
	CLIENT_STATUS(9L,"Client Status","GetCountries"),
	USER_TYPE(10L,"User Type","GetCountries"),
	CURRENCY(11L,"Currency Type","GetCurrencies"),
	BANK(12L,"Currency Type","GetBanks"),
	BRANCH(13L,"Currency Type","GetBranches")

	
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
