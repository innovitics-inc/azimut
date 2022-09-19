package innovitics.azimut;

import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.businessutilities.CachingLayer;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.datautilities.ArrayUtility;

public class AzimutParent {
	@Autowired
	protected ArrayUtility arrayUtility;
	@Autowired 
	protected CachingLayer cachingLayer;
	@Autowired 
	protected ConfigProperties configProperties;

}
