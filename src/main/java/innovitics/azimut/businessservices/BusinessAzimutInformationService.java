package innovitics.azimut.businessservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BusinessAzimutInformation;
import innovitics.azimut.businessmodels.BusinessAzimutInformationType;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.azimutdetails.AzimutInformation;
import innovitics.azimut.services.azimut.AzimutInformationService;
import innovitics.azimut.utilities.crosslayerenums.AzimutInformationType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.mapping.AzimutInformationMapper;

@Service
public class BusinessAzimutInformationService extends AbstractBusinessService<BusinessAzimutInformationType>{
	
	@Autowired AzimutInformationService azimutInformationService;
	@Autowired AzimutInformationMapper azimutInformationMapper;
	@Autowired ListUtility<AzimutInformation> azimutInformationListUtility; 
	public BusinessAzimutInformationType getById(Long id,String language)
	{
		return this.azimutInformationMapper.convertBasicUnitToBusinessAzimutInformationTypeUnit(this.azimutInformationService.getById(id), language);
	}
		
	public List<BusinessAzimutInformationType> getAll(String langauge)
	{
		List<BusinessAzimutInformationType> businessAzimutInformationTypes=new ArrayList<BusinessAzimutInformationType>();
		
		List<AzimutInformation> azimutInformations=this.azimutInformationService.getAllByType(null);
		
		Map<Integer,List<BusinessAzimutInformation>> map= AzimutInformationType.assign(azimutInformations, azimutInformationMapper,langauge);
		for (var entry : map.entrySet()) 
		{
			BusinessAzimutInformationType businessAzimutInformationType=new BusinessAzimutInformationType();
			businessAzimutInformationType.setType(entry.getKey());
			businessAzimutInformationType.setTypeName(AzimutInformationType.getInformationTypeById(entry.getKey(), langauge));
			businessAzimutInformationType.setBusinessAzimutInformationList(entry.getValue());
			businessAzimutInformationTypes.add(businessAzimutInformationType);
		}
		
		return businessAzimutInformationTypes;
	}
	

	public List<BusinessAzimutInformationType> getAzimutInformations(String langauge) throws BusinessException
	{
		
			List<BusinessAzimutInformationType> businessAzimutInformationTypes=new ArrayList<BusinessAzimutInformationType>();
			
			List<AzimutInformation> azimutInformations=this.azimutInformationService.getAllByType(null);
			
			if(azimutInformationListUtility.isListPopulated(azimutInformations))
			{	Map<Integer,List<BusinessAzimutInformation>> map= AzimutInformationType.assign(azimutInformations, azimutInformationMapper,langauge);
				for (var entry : map.entrySet()) 
				{
				BusinessAzimutInformationType businessAzimutInformationType=new BusinessAzimutInformationType();
				businessAzimutInformationType.setType(entry.getKey());
				businessAzimutInformationType.setTypeName(AzimutInformationType.getInformationTypeById(entry.getKey(), langauge));
				businessAzimutInformationType.setBusinessAzimutInformationList(entry.getValue());
				businessAzimutInformationTypes.add(businessAzimutInformationType);
				}
				return businessAzimutInformationTypes;
			}
			
			return Collections.<BusinessAzimutInformationType> emptyList();
		
		
	}
}
