package innovitics.azimut.utilities.crosslayerenums;

public enum ContractMap {

	AT_HOME(3,"Home"),
	REMOTELY(1,"Remotely"),
	AT_AZIMUT(2,"Azimut");
	
	ContractMap(int mapId, String map) {
		this.mapId=mapId;
		this.map=map;
	}

	private final int mapId;
	private final String map;
	
	public int getMapId() {
		return mapId;
	}
	public String getMap() {
		return map;
	}
	
	
	
	
}
