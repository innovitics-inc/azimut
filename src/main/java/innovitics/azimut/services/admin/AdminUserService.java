package innovitics.azimut.services.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.admin.AdminUser;
import innovitics.azimut.repositories.admin.AdminUserDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.AdminUserSpecification;

@Service
public class AdminUserService extends AbstractService<AdminUser, String>{

	@Autowired AdminUserDynamicRepository  adminUserDynamicRepository; 
	@Autowired AdminUserSpecification adminUserSpecification;
	
	public AdminUser findById(Long id)
	{
		return adminUserDynamicRepository.getById(id);
	}
	
	public AdminUser findByUserName(String username)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("email", username,SearchOperation.EQUAL, null));
		searchCriteriaList.add(new SearchCriteria("roleId", 1L,SearchOperation.EQUAL, null));
		return this.adminUserDynamicRepository.findOne(this.adminUserSpecification.findByCriteria(searchCriteriaList)).get();

	}	
}
