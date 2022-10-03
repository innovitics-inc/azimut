package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.repositories.user.UserBlockageRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.UserBlockageSpecification;
@Service
public class UserBlockageService extends AbstractService<UserBlockage, String> {

	@Autowired UserBlockageRepository userBlockageRepository;
	@Autowired UserBlockageSpecification userBlockageSpecification;
	
	public UserBlockage findByUserId(Long userId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
	    searchCriteriaList.add(new SearchCriteria("id",userId.toString(),SearchOperation.PARENT_EQUAL, "user"));		
		return this.userBlockageRepository.findOne(this.userBlockageSpecification.findByCriteria(searchCriteriaList)).get();	
	}
	
	public UserBlockage updateUserBlockage(UserBlockage userBlockage)
	{
		return this.userBlockageRepository.save(userBlockage);		
	}
	
	public UserBlockage addUserBlockage(User user)
	{
		UserBlockage userBlockage=new UserBlockage();	
		userBlockage.setErrorCount(1);
		userBlockage.setUserPhone(user.getUserPhone());
		userBlockage.setUserId(user.getUserId());
		userBlockage.setCreatedAt(new Date());
		userBlockage.setUpdatedAt(new Date());
		userBlockage.setUser(user);
		return this.userBlockageRepository.save(userBlockage);	
	}
}
