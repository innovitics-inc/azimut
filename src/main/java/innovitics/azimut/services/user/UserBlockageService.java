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
import innovitics.azimut.utilities.logging.MyLogger;
@Service
public class UserBlockageService extends AbstractService<UserBlockage, String> {

	@Autowired UserBlockageRepository userBlockageRepository;
	@Autowired UserBlockageSpecification userBlockageSpecification;
	
	public UserBlockage findByUserPhone(String userPhone)
	{
		UserBlockage userBlockage=new UserBlockage();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
	    searchCriteriaList.add(new SearchCriteria("userPhone",userPhone,SearchOperation.EQUAL,null));
	    searchCriteriaList.add(new SearchCriteria("userId",null,SearchOperation.IS_NULL,null));
	    userBlockage= this.userBlockageRepository.findOne(this.userBlockageSpecification.findByCriteria(searchCriteriaList)).get();
	    if(userBlockage!=null)
	    {  
	    	detemineBlockage(userBlockage);
	    	userBlockage.setUser(null);
	    }
	    return userBlockage;
	}
	
	public UserBlockage findByUserId(Long userId)
	{
		UserBlockage userBlockage=new UserBlockage();
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
	    searchCriteriaList.add(new SearchCriteria("id",userId.toString(),SearchOperation.PARENT_EQUAL, "user"));
	    
	    userBlockage= this.userBlockageRepository.findOne(this.userBlockageSpecification.findByCriteria(searchCriteriaList)).get();
	    if(userBlockage!=null)
	    {  
	    	detemineBlockage(userBlockage);
	    	userBlockage.setUser(null);
	    }
	    return userBlockage;
	}
	
	public UserBlockage updateUserBlockage(UserBlockage userBlockage)
	{
		MyLogger.info("user phone:::::"+userBlockage.getUserPhone());
		userBlockage.setUpdatedAt(new Date());
		UserBlockage updatedUserBlockage= this.userBlockageRepository.save(userBlockage);
		
		detemineBlockage(updatedUserBlockage);
		return updatedUserBlockage;		
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
		userBlockage.setBlock(false);
		this.userBlockageRepository.save(userBlockage);
		return userBlockage;
	}
	
	public UserBlockage addUserBlockage(String userPhone)
	{
		MyLogger.info("user phone:::::"+userPhone);
		UserBlockage userBlockage=new UserBlockage();	
		userBlockage.setErrorCount(1);
		userBlockage.setUserPhone(userPhone);
		userBlockage.setCreatedAt(new Date());
		userBlockage.setUpdatedAt(new Date());
		userBlockage.setBlock(false);
		this.userBlockageRepository.save(userBlockage);
		return userBlockage;
	}
	
	void detemineBlockage(UserBlockage userBlockage)
	{
	  	if(userBlockage.getErrorCount()>=this.configProperties.getBlockageNumberOfTrials())
    	{
    		userBlockage.setBlock(true);
    	}
    	else
    	{
    		userBlockage.setBlock(false);
    	}
  
	}
}
