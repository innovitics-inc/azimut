package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.UserInteraction;
import innovitics.azimut.repositories.user.UserInteractionRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserInteractionChildSpecification;

@Service
public class UserInteractionService extends AbstractService<UserInteraction, String> {

	@Autowired UserInteractionRepository userInteractionRepository;
	@Autowired UserInteractionChildSpecification userInteractionChildSpecification;
	
	public UserInteraction addUserInteraction(UserInteraction userInteraction)
	{
		return this.userInteractionRepository.save(userInteraction);
	}
	public List<UserInteraction> getUserInteractionsByType(Integer type)
	{
	
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("type", type,SearchOperation.EQUAL, null));
		
		return this.userInteractionRepository.findAll(userInteractionChildSpecification.findByCriteria(searchCriteriaList));
	}
	
}
