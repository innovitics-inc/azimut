package innovitics.azimut.utilities.crosslayerenums;

public enum PDFFieldNames {
	ACCOUNT_HOLDER_NAME(1,"Text180"),
	ADDRESS(2,"Text2"),
	COUNTRY(3,"Text3"),
	CITY(4,"Text4"),
	FAX(5,"Text5"),
	TELEPHONE(6,"Text6")
	;

	PDFFieldNames(long id,String fieldName) {
		this.id=id;
		this.fieldName=fieldName;
	}

	private final long id;
	private final String fieldName;
	public long getId() {
		return id;
	}
	public String getFieldName() {
		return fieldName;
	}
	
	
	

}
