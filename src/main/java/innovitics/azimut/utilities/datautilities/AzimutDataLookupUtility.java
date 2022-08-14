package innovitics.azimut.utilities.datautilities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessAzimutDataLookup;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.AzimutEntityType;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
@Component
public class AzimutDataLookupUtility extends ParentUtility {

@Autowired TeaComputerService teaComputerService;
@Autowired ExceptionHandler exceptionHandler;


public BusinessAzimutDataLookup getLookups(BusinessAzimutDataLookup businessAzimutDataLookup)
{

	if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.COUNTRY.getTypeId()))
	{
		List<Country> countries=this.getTeaComputerCountries();
		businessAzimutDataLookup.setCountries(countries);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.CITY.getTypeId()))
	{
		List<City> cities=this.getTeaComputerCitiesByCountryId(businessAzimutDataLookup.getCountryId());
		businessAzimutDataLookup.setCities(cities);
	}
	else if(NumberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), AzimutEntityType.NATIONALITY.getTypeId()))
	{
		List<Nationality> nationalities=this.getTeaComputerNationalities();
		businessAzimutDataLookup.setNationalities(nationalities);
	}

	
	
	return businessAzimutDataLookup;
}


/*	public List<BusinessAzimutDataLookup> assignValuesToMap(List<AzimutEntity> azimutEntities)
	{
		List<BusinessAzimutDataLookup> businessAzimutDataLookups=new ArrayList<BusinessAzimutDataLookup>();
		
		
		Set<Long> uniqueEntityTypeIdSet=new HashSet<Long>();
		for(AzimutEntity azimutEntity:azimutEntities)
		{
			uniqueEntityTypeIdSet.add(azimutEntity.getEntityType());
		}
		List<Long> typeIdList=this.longListUtility.convertSetToList(uniqueEntityTypeIdSet);
		for(Long entityTypeId:typeIdList)
		{
			businessAzimutDataLookups.add(new BusinessAzimutDataLookup(null, entityTypeId, null));
		}
		for(BusinessAzimutDataLookup businessAzimutDataLookup:businessAzimutDataLookups)
		{
			List<BusinessEntityValues> designatedValueList=new ArrayList<BusinessEntityValues>();
			for(AzimutEntity azimutEntity:azimutEntities)
			{
				if(this.numberUtility.areLongValuesMatching(businessAzimutDataLookup.getEntityTypeId(), azimutEntity.getEntityType()))
				{
					designatedValueList.add(this.addToValueList(azimutEntity));
				}
			}
			businessAzimutDataLookup.setEntityValueList(designatedValueList);
		}
		
		
		return businessAzimutDataLookups;
	}

	BusinessEntityValues addToValueList(AzimutEntity azimutEntity)
	{
		BusinessEntityValues businessEntityValueList=new BusinessEntityValues();
		
		List<BusinessAzimutEntityDetail> azimutEntityDetails=new ArrayList<BusinessAzimutEntityDetail>();
		
		businessEntityValueList.setEntityId(azimutEntity.getId());
		for(AzimutDataLookup azimutDataLookup:azimutEntity.getDetails())
		{
			azimutEntityDetails.add(this.convertDataLookUpToEntityDetail(azimutDataLookup));
		}
		businessEntityValueList.setEntityDetails(azimutEntityDetails);
		return businessEntityValueList;
	}
	
	BusinessAzimutEntityDetail convertDataLookUpToEntityDetail(AzimutDataLookup azimutDataLookup)
	{
		BusinessAzimutEntityDetail businessAzimutEntityDetail=new BusinessAzimutEntityDetail();
		businessAzimutEntityDetail.setKey(azimutDataLookup.getEntityKey());
		businessAzimutEntityDetail.setValue(azimutDataLookup.getEntityValue());
		
		return businessAzimutEntityDetail;
	}
	*/

	List<Country> getTeaComputerCountries()
	{	
		try 
		{			
			return this.teaComputerService.getAllCountries();			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return null;
	}

	List<City> getTeaComputerCitiesByCountryId(Long countryId)
	{	
		try 
		{			
			return this.teaComputerService.getAllCitiesByCountryId(countryId);			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return null;
	}
	List<Nationality> getTeaComputerNationalities()
	{	
		try 
		{			
			return this.teaComputerService.getAllNationalities();			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return null;
	}

}
