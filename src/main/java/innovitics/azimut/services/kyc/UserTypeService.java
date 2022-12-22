package innovitics.azimut.services.kyc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.UserType;
import innovitics.azimut.repositories.kyc.UserTypeDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserTypeSpecification;
import innovitics.azimut.utilities.logging.MyLogger;
@Service
public class UserTypeService extends AbstractService<UserType, String>{

	
	@Autowired UserTypeDynamicRepository userTypeDynamicRepository;
	@Autowired UserTypeSpecification userTypeSpecification;
	
	
	public UserType getUserTypeById(Long id)
	{
		MyLogger.info("Fetching user type for ID:::"+id);
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("id", id,SearchOperation.EQUAL,null));
		UserType userType= this.userTypeDynamicRepository.findOne(this.userTypeSpecification.findByCriteria(searchCriteriaList)).get();
		return userType;
	}
	
	public List<UserType> findAll()
	{
		return this.userTypeDynamicRepository.findAll();
	}
	
	public UserType getUserTypeByTeacomputerId(Long teacomputerId)
	{
		MyLogger.info("Fetching user type for teacomputerID:::"+teacomputerId);
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("azimutIdTypeId", teacomputerId,SearchOperation.EQUAL,null));
		UserType userType= this.userTypeDynamicRepository.findOne(this.userTypeSpecification.findByCriteria(searchCriteriaList)).get();
		return userType;
	}
	
	
	public UserType addUserType (Long  userTypeTeacomputerId)
	{
		
		UserType userType=new UserType();
		userType.setAzimutIdTypeId(userTypeTeacomputerId);
		userType.setCreatedAt(new Date());
		userType.setUpdatedAt(new Date());
		
		return this.userTypeDynamicRepository.save(userType);
	}
}
