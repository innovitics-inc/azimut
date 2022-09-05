package innovitics.azimut.services.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;
import innovitics.azimut.repositories.user.UserDeviceRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.UserDeviceSpecification;
@Service
public class UserDeviceService extends AbstractService<UserDevice,String>{
	
	@Autowired UserDeviceRepository userDeviceRepository;
	@Autowired UserDeviceSpecification userDeviceSpecification;
	
	public UserDevice addUserDevice(UserDevice userDevice)
	{
		this.logger.info("Adding the device Id:::::");
		new Date();
		return	this.userDeviceRepository.save(userDevice);
	}
	
	
	public UserDevice updateUserDevice(UserDevice userDevice)
	{
		return	this.userDeviceRepository.save(userDevice);
	}

	
	public Long getUserDeviceWithinTokenExpiry(Long userId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();		
		//searchCriteriaList.add(new SearchCriteria("createdAt",dates,SearchOperation.BETWEEN, null));
		
		Timestamp current=new Timestamp(System.currentTimeMillis());		
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(current.getTime());
	    
		int tokenExpiryMinutes=Integer.valueOf(this.configProperties.getJwTokenDurationInMinutes());
	    cal.add(Calendar.MINUTE, -tokenExpiryMinutes);
	    
	    Timestamp currentMinusTokenExpiry = new Timestamp(cal.getTime().getTime());
	    
	    searchCriteriaList.add(new SearchCriteria("id",userId.toString(),SearchOperation.PARENT_EQUAL, "user"));		
		searchCriteriaList.add(new SearchCriteria("createdAt",currentMinusTokenExpiry.toString(),SearchOperation.AFTER, null));
		

		return this.userDeviceRepository.count(this.userDeviceSpecification.findByCriteria(searchCriteriaList));	
				
	}
	
	public UserDevice getUserDevicesWithUserIdAndDeviceId(Long userId,String deviceId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();		
	
	    searchCriteriaList.add(new SearchCriteria("id",userId.toString(),SearchOperation.PARENT_EQUAL, "user"));		

	    searchCriteriaList.add(new SearchCriteria("deviceId",deviceId,SearchOperation.EQUAL, null));
		
		return this.userDeviceRepository.findOne(this.userDeviceSpecification.findByCriteria(searchCriteriaList)).get();				
	}
	public List<UserDevice> getLatestUserDevicesWithUserIdAndDeviceId(Long userId,String deviceId)
	{
		return this.userDeviceRepository.findLatestUserDeviceLogin(userId,deviceId);
		
	}
	public List<UserDevice> getUserDevicesWithUserId(Long userId)
	{

		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();		
		
	    searchCriteriaList.add(new SearchCriteria("id",userId.toString(),SearchOperation.PARENT_EQUAL, "user"));		

		return this.userDeviceRepository.findAll(this.userDeviceSpecification.findByCriteria(searchCriteriaList));	
		
	}
	
}
