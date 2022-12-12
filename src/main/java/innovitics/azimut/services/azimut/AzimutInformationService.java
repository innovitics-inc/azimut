package innovitics.azimut.services.azimut;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.azimutdetails.AzimutInformation;
import innovitics.azimut.repositories.azimut.AzimutInformationRespository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.AzimutInformationChildSpecification;

@Service
public class AzimutInformationService extends AbstractService<AzimutInformation,String> {

	@Autowired AzimutInformationRespository azimutInformationRespository;
	
	@Autowired AzimutInformationChildSpecification azimutInformationChildSpecification;

	public AzimutInformation getById(Long id)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id,SearchOperation.EQUAL, null));		
		return this.azimutInformationRespository.findOne(this.azimutInformationChildSpecification.findByCriteria(searchCriteriaList)).get();
	}
	
	public List<AzimutInformation> getAllByType(Integer type)
	{
		
		if(type!=null)
		{
			List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
			searchCriteriaList.add(new SearchCriteria("informationType", type,SearchOperation.EQUAL, null));
			return this.azimutInformationRespository.findAll(this.azimutInformationChildSpecification.findByCriteria(searchCriteriaList));
		}
		else
			return this.azimutInformationRespository.findAll();
		
		
	}
	
	
	
}
