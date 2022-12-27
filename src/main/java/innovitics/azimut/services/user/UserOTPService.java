package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.UserOTP;
import innovitics.azimut.repositories.user.UserOtpRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserOtpSepcification;
@Service
public class UserOTPService extends AbstractService<UserOTP, String>{

	@Autowired UserOtpRepository userOtpRepository;
	@Autowired UserOtpSepcification userOtpSepcification;
	
	public UserOTP save(UserOTP userOTP)
	{
		return this.userOtpRepository.save(userOTP);
	}
	
	public UserOTP findByUser(String userPhone)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userPhone", userPhone,SearchOperation.EQUAL,null));
		return this.userOtpRepository.findOne(this.userOtpSepcification.findByCriteria(searchCriteriaList)).get();
	}
}
