package innovitics.azimut.rest.entities;

import innovitics.azimut.AzimutParent;

public abstract class BaseInput extends AzimutParent {

	protected String url;

	public abstract String getUrl();
	public abstract String setBaseUrl();
	
	
}
