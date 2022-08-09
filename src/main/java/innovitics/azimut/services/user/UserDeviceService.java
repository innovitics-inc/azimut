package innovitics.azimut.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;
import innovitics.azimut.repositories.user.UserDeviceRepository;
import innovitics.azimut.services.AbstractService;
@Service
public class UserDeviceService extends AbstractService<UserDevice,String>{
	
	@Autowired UserDeviceRepository userDeviceRepository;
	
	
	public UserDevice addUserDevice(UserDevice userDevice)
	{
		return	this.userDeviceRepository.save(userDevice);
	}
	
	
	public UserDevice updateUserDevice(UserDevice userDevice)
	{
		return	this.userDeviceRepository.save(userDevice);
	}

}
