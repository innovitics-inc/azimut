package innovitics.azimut;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessutilities.CachingLayer;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.rest.RestContract;
import innovitics.azimut.utilities.datautilities.ArrayUtility;

public class AzimutParent<I, O,REQ, RES, B extends BaseBusinessEntity> {
	@Autowired
	protected ArrayUtility arrayUtility;
	@Autowired 
	protected CachingLayer cachingLayer;
	@Autowired 
	protected ConfigProperties configProperties;
	@Autowired protected  RestContract<I, O, REQ, RES, B> restContract;
	
	protected Timestamp getMinutesBefore(String value)
	{
		Timestamp current=new Timestamp(System.currentTimeMillis());		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(current.getTime());	    
		int valueExpiryInMinutes=Integer.valueOf(value);
		cal.add(Calendar.MINUTE, -valueExpiryInMinutes);
		Timestamp currentMinusMinutesInValue = new Timestamp(cal.getTime().getTime());
		return currentMinusMinutesInValue;
	}
}
