package innovitics.azimut.utilities.crosslayerenums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.businessmodels.BusinessAzimutInformation;
import innovitics.azimut.models.azimutdetails.AzimutInformation;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.mapping.AzimutInformationMapper;

public enum AzimutInformationType {

	FAQ(1,"FAQ","الأسئلة الشائعة")	
	;
		
	AzimutInformationType(int id,String informationType, String informationTypeAr) {
		this.id=id;
		this.informationType=informationType;
		this.informationTypeAr = informationTypeAr;
	}
	

	private final int id;
	private final String informationType;
	private final String informationTypeAr;
	
	public int getId() {
		return id;
	}
	public String getInformationType() {
		return informationType;
	}
	public String getInformationTypeAr() {
		return informationTypeAr;
	}

	
	public static Map<Integer,List<BusinessAzimutInformation>> assign (List<AzimutInformation> azimutInformations,AzimutInformationMapper azimutInformationMapper,String language) 
	{
	
		Map<Integer,List<BusinessAzimutInformation>> map=new HashMap<Integer,List<BusinessAzimutInformation>>();
	    for(AzimutInformationType azimutInformationType : values()) 
	    {
	    	List<BusinessAzimutInformation> businessAzimutInformations=new ArrayList<BusinessAzimutInformation>();
	    	for(AzimutInformation azimutInformation:azimutInformations)
	    	{
	 		
	    	 if(NumberUtility.areIntegerValuesMatching(azimutInformation.getInformationType(), azimutInformationType.id))
	    	 {
	    		 businessAzimutInformations.add(azimutInformationMapper.convertBasicUnitToBusinessUnit(azimutInformation,language));
	    	 }
	       }
	     map.put(azimutInformationType.id, businessAzimutInformations);
	    }
	    return map;
	}
	
	public static String  getInformationTypeById(Integer id,String language) 
	{
		String result="";
	    for(AzimutInformationType azimutInformationType : values()) 
	    {
	        	if(NumberUtility.areIntegerValuesMatching(azimutInformationType.id, id))
	        	{
	        		if(StringUtility.stringsMatch(language, StringUtility.ENGLISH))
	        	    {  
	        			result = azimutInformationType.informationType;
	        	    }
	        		else if(StringUtility.stringsMatch(language, StringUtility.ARABIC))
	        		{
	        			result =  azimutInformationType.informationTypeAr;
	        		}
	        	    else 
	        	    {
	        	    	result = azimutInformationType.informationType;
	        	    }
	        		
	        	}	
	    
	    }
    	return result;
	}
	
}
