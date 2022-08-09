package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessutilities.SearchFilter;
import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.user.UserDynamicRepository;
import innovitics.azimut.repositories.user.UserRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserSpecification;
@Service
public class UserService extends AbstractService<User,String>{

	@Autowired(required = true)
	private	UserRepository userRepository;
	
	@Autowired(required = true)
	private	UserDynamicRepository userDynamicRepository;
	
	@Autowired(required = true)
	private	UserSpecification userSpecification;
	
	public List<User> findAll() {
	
		return this.userRepository.findAll();
	
	}
	
	
	public User save(User user) {
	
		return this.userRepository.save(user);
	
	}
	
	
	
	public User update(User user) {
		
		return this.userRepository.save(user);
		
	}
	
	
	public User findById(long id){
		User user = new User();

		user = this.userRepository.getById(id);

		return user;
	
	}
	

	
	public User findByPhoneNumber(String countryPhoneCode,String phoneNumber) {
		/*User user= new User();
		user=userRepository.findByPhoneNumber(countryPhoneCode, phoneNumber);
		return user*/;
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("countryPhoneCode", countryPhoneCode,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("phoneNumber", phoneNumber,SearchOperation.EQUAL, null));

		return this.userDynamicRepository.findOne(this.userSpecification.findByCriteria(searchCriteriaList)).get();
	
	}

	
	public User findByUserPhone(String param) {
		/*User user=new User();
		user=this.userRepository.findByUserPhone(param);
		return user*/;	
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userPhone", param,SearchOperation.EQUAL, null));		
		return this.userDynamicRepository.findOne(this.userSpecification.findByCriteria(searchCriteriaList)).get();
	
	}
	
	
	public User findByUserPhoneAndPassword(String userPhone,String password) {
		/*User user=new User();
		user=this.userRepository.findByUserPhoneAndPassword(userPhone,password);
		return user*/;
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userPhone", userPhone,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("password", password,SearchOperation.EQUAL, null));
		return this.userDynamicRepository.findOne(this.userSpecification.findByCriteria(searchCriteriaList)).get();

	
		
	}
	
	public User findByUserPhoneAndPassword(String countryPhoneCode,String phoneNumber,String password) {
		/*User user=new User();
		user=this.userRepository.findByPhoneCodePhoneNumberPassword(countryPhoneCode,phoneNumber,password);
		return user*/;
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("countryPhoneCode", countryPhoneCode,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("phoneNumber", phoneNumber,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("password", password,SearchOperation.EQUAL, null));
		return this.userDynamicRepository.findOne(this.userSpecification.findByCriteria(searchCriteriaList)).get();
	}
	
	

}
