package innovitics.azimut.services.kyc;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain.NamedEntityGraph;

import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.repositories.EnhancedKYCPageRepository;
import innovitics.azimut.repositories.kyc.KYCPageDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.KYCPageSpecification;
@Service
public class KYCPageService extends  AbstractService<KYCPage, String>{
@Autowired KYCPageDynamicRepository kycPageDynamicRepository;
@Autowired KYCPageSpecification kycPageSpecification;
@Autowired EnhancedKYCPageRepository enhancedKYCPageRepository;

	public List<KYCPage> getByUserType(Long userIdType)
	{
		this.logger.info("Old implementation");
		/*List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", userTypId.toString(),SearchOperation.PARENT_EQUAL,"userType"));		
		return kycPageDynamicRepository.findAll(this.kycPageSpecification.findByCriteria(searchCriteriaList));*/
		
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", userIdType.toString(),SearchOperation.PARENT_EQUAL,"userType"));
		return kycPageDynamicRepository.findAll(this.kycPageSpecification.findByCriteria(searchCriteriaList),new NamedEntityGraph(EntityGraphType.FETCH, "KYCPage.details"));
	}
	
	public KYCPage getById(Long id)
	{
		//return this.enhancedKYCPageRepository.getEnhanced5(id);	
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id.toString(),SearchOperation.EQUAL,null));
		return kycPageDynamicRepository.findOne(kycPageSpecification.findByCriteria(searchCriteriaList), new NamedEntityGraph(EntityGraphType.FETCH, "KYCPage.details")).get();
	}
	
	public Page<KYCPage> getPaginatedKYCPageById(Long userTypId)
	{
		return null;
	}
	
	public List<KYCPage> getEnhanced(Long userTypId)
	{
		return this.enhancedKYCPageRepository.getEnhanced2(userTypId);	
	}
	
	public Integer findPageWeightById(Long id)
	{
		return this.kycPageDynamicRepository.findPageWeightById(id);
	}

	
}
