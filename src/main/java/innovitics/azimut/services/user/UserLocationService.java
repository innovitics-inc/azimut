package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.repositories.user.UserLocationsDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserLocationDynamicSpecification;
@Service
public class UserLocationService extends AbstractService<UserLocation, String>{
	
	@Autowired UserLocationsDynamicRepository  userLocationsDynamicRepository; 
	@Autowired UserLocationDynamicSpecification userLocationDynamicSpecification;
	
	public UserLocation findByUserId (Long userId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userId", userId,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("deletedAt", "",SearchOperation.IS_NULL, null));
		return this.userLocationsDynamicRepository.findOne(userLocationDynamicSpecification.findByCriteria(searchCriteriaList)).get();
		
	}
	public UserLocation addUserLocation(UserLocation userLocation)
	{
		return this.userLocationsDynamicRepository.save(userLocation);
	}

	public void softdeleteOldUserLocations(Long userId)
	{
		this.userLocationsDynamicRepository.deleteOldUserLocations(userId);
	}
}
