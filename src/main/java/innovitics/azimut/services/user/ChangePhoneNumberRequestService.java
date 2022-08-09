package innovitics.azimut.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.ChangePhoneNumberRequest;
import innovitics.azimut.repositories.user.ChangePhoneNumberRequestDynamicRepository;
import innovitics.azimut.repositories.user.ChangePhoneNumberRequestRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.ChangePhoneNumberRequestSpecification;
@Service
public class ChangePhoneNumberRequestService extends  AbstractService<ChangePhoneNumberRequest, String>{

	@Autowired private ChangePhoneNumberRequestRepository changePhoneNumberRequestRepository;
	
	@Autowired private ChangePhoneNumberRequestDynamicRepository  changePhoneNumberRequestDynamicRepository;
	@Autowired private ChangePhoneNumberRequestSpecification changePhoneNumberRequestSpecification;

	
	public ChangePhoneNumberRequest addChangePhoneNumberRequest(ChangePhoneNumberRequest changePhoneNumberRequest)
	{
		return this.changePhoneNumberRequestRepository.save(changePhoneNumberRequest);
	}
	
	
	public ChangePhoneNumberRequest updateChangePhoneNumberRequest(ChangePhoneNumberRequest changePhoneNumberRequest)
	{
		return this.changePhoneNumberRequestRepository.save(changePhoneNumberRequest);
	}
	
	
	public List<ChangePhoneNumberRequest> getChangePhoneNumberRequestsByOldPhoneNumber(String oldPhoneNumber)
	{
		//return this.changePhoneNumberRequestRepository.findByOldPhoneNumber(oldPhoneNumber);
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("oldPhoneNumber", oldPhoneNumber,SearchOperation.EQUAL, null));
		return this.changePhoneNumberRequestDynamicRepository.findAll(this.changePhoneNumberRequestSpecification.findByCriteria(searchCriteriaList));
	}
}
