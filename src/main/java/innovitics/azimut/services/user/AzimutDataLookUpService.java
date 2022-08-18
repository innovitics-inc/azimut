package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain.NamedEntityGraph;

import innovitics.azimut.models.azimutdetails.AzimutDetails;
import innovitics.azimut.models.user.AzimutDataLookup;
import innovitics.azimut.models.user.AzimutEntity;
import innovitics.azimut.repositories.user.AzimutDataLookupDynamicRepository;
import innovitics.azimut.repositories.user.AzimutDetailsDynamicRepository;
import innovitics.azimut.repositories.user.AzimutEntityDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.AzimutEntitySepcification;
import innovitics.azimut.utilities.dbutilities.specifications.child.AzmiutDataLookupSpecification;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.AzimutDataLookupParentSpecification;
@Service
public class AzimutDataLookUpService extends AbstractService<AzimutDataLookup, String>
{
	@Autowired AzimutDataLookupParentSpecification azimutDataLookupParentSpecification;
	@Autowired AzimutDataLookupDynamicRepository azimutDataLookupDynamicRepository;
	
	@Autowired AzimutEntitySepcification azimutEntitySepcification;
	@Autowired AzimutEntityDynamicRepository azimutEntityDynamicRepository;
	
	@Autowired AzimutDetailsDynamicRepository azimutDetailsDynamicRepository;
	
	public List<AzimutDataLookup> getFieldValues(Long [] entityTypes)
	{
		List<AzimutDataLookup> azimutDataLookups=new ArrayList<AzimutDataLookup>();		
		if(this.arrayUtility.isArrayPopulated(entityTypes))
		{
			List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
			searchCriteriaList.add(new SearchCriteria("entityType", this.arrayUtility.generateObjectListFromObjectArray(entityTypes),SearchOperation.PARENT_IN, "parent"));
			azimutDataLookups= this.azimutDataLookupDynamicRepository.findAll(this.azimutDataLookupParentSpecification.findByCriteria(searchCriteriaList));
		}
		else
		{
			azimutDataLookups= this.azimutDataLookupDynamicRepository.findAll();
		}
		return azimutDataLookups;
	}
	public List<AzimutEntity> getEntityValues(Long [] entityTypes)
	{
		List<AzimutEntity> azimutEntities=new ArrayList<AzimutEntity>();		
		if(this.arrayUtility.isArrayPopulated(entityTypes))
		{
			List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
			searchCriteriaList.add(new SearchCriteria("entityType", this.arrayUtility.generateObjectListFromObjectArray(entityTypes),SearchOperation.IN, null));
			azimutEntities= this.azimutEntityDynamicRepository.findAll(this.azimutEntitySepcification.findByCriteria(searchCriteriaList),new NamedEntityGraph(EntityGraphType.FETCH, "AzimutEntity.details"));
		}
		else
		{
			azimutEntities= this.azimutEntityDynamicRepository.findAll();
		}
		return azimutEntities;
	}
	
	public void save(AzimutEntity azimutEntity)
	{
		this.azimutEntityDynamicRepository.save(azimutEntity);
	}
	public void saveAll(List<AzimutDataLookup> azimutdataLookUps)
	{
		this.azimutDataLookupDynamicRepository.saveAll(azimutdataLookUps);
	}
	
	public AzimutDetails getAzimutDetails()
	{
		return this.azimutDetailsDynamicRepository.findAll().get(0);
	}
	
}
