package innovitics.azimut.repositories.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;
import innovitics.azimut.repositories.AbstractRepository;
@Repository
public class UserDeviceRepositoryImpl extends AbstractRepository<UserDevice>  implements UserDeviceRepositoryCustom {

	@Override
	public List<UserDevice> findLatestUserDeviceLogin(Long userId,String deviceId) {
		List<UserDevice> latestUserDevices=new ArrayList<UserDevice>(); 
		List<Object[]> listOfObjectArrays=this.grouping(UserDevice.class, "id", userId.toString(), "updatedAt","deviceId",deviceId,"user",null);
		
		for(int i=0;i<listOfObjectArrays.size();i++)
		{
			Object[] objectArray=listOfObjectArrays.get(i);
			this.logger.info("Object "+ i +" details");
			
				UserDevice userDevice=new UserDevice();
				userDevice.setId((Long)(objectArray[0]));
				userDevice.setUserId((String)(objectArray[1]));
				userDevice.setUserPhone((String)(objectArray[2]));
				userDevice.setDeviceId((String)(objectArray[3]));
				userDevice.setCreatedAt((Date)(objectArray[4]));
				userDevice.setUpdatedAt((Date)(objectArray[5]));				
				User user=new User();
				user.setId(userId);
				userDevice.setUser(user);
				latestUserDevices.add(userDevice);
		}
		
		return latestUserDevices;
	}

	@Override
	protected void setSelectedValues(CriteriaBuilder criteriaBuilder,CriteriaQuery<Object[]> criteriaQuery)
	{
		this.logger.info("Adding the selected values");
		Root<UserDevice> root = criteriaQuery.from(UserDevice.class);
		criteriaQuery.multiselect(root.get("id"),root.get("userId"),root.get("userPhone"),root.get("deviceId"),root.get("createdAt"),criteriaBuilder.max(root.get("updatedAt"))).groupBy(root.get("deviceId"));
	}
	
	@Override
	protected void setWhereConditions(CriteriaBuilder criteriaBuilder,CriteriaQuery<Object[]> criteriaQuery,String searchKey,String searchValue)
	{
		/*
		Timestamp current=new Timestamp(System.currentTimeMillis());		
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(current.getTime());	    
		int tokenExpiryMinutes=Integer.valueOf(this.configProperties.getJwTokenDurationInMinutes());
	    cal.add(Calendar.MINUTE, -tokenExpiryMinutes);
	    Timestamp currentMinusTokenExpiry = new Timestamp(cal.getTime().getTime());
	    Root<UserDevice> root = criteriaQuery.from(UserDevice.class);
	    Join<UserDevice, User> join = root.join("user");
	    */
		Root<UserDevice> root = criteriaQuery.from(UserDevice.class);
		Join<UserDevice, User> join = root.join("user");
	    criteriaBuilder.equal(join.get(searchKey),searchValue);
	    //criteriaQuery.where(criteriaBuilder.equal(root.get("updatedAt"), currentMinusTokenExpiry.toString()));
	}
}
