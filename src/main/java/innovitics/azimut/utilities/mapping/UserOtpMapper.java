package innovitics.azimut.utilities.mapping;

import java.util.Date;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.models.user.UserOTP;
@Component
public class UserOtpMapper extends Mapper<UserOTP, BusinessUserOTP>{

	@Override
	public UserOTP convertBusinessUnitToBasicUnit(BusinessUserOTP businessUserOTP, boolean save) {
		UserOTP userOTP=new UserOTP();
		userOTP.setId(businessUserOTP.getId());
		userOTP.setUserPhone(businessUserOTP.getUserPhone());
		userOTP.setFunctionality(businessUserOTP.getFunctionality());
		userOTP.setOtpMethod(businessUserOTP.getOtpMethod());
		userOTP.setCreatedAt(new Date());
		userOTP.setUpdatedAt(save?businessUserOTP.getUpdatedAt():new Date());
		userOTP.setNextTrial(null);
				
		return userOTP;
	}

	@Override
	public BusinessUserOTP convertBasicUnitToBusinessUnit(UserOTP userOTP) {
		BusinessUserOTP businessUserOTP=new BusinessUserOTP();
		businessUserOTP.setId(userOTP.getId());
		businessUserOTP.setUserPhone(userOTP.getUserPhone());
		businessUserOTP.setFunctionality(userOTP.getFunctionality());
		businessUserOTP.setOtpMethod(userOTP.getOtpMethod());
		businessUserOTP.setCreatedAt(userOTP.getCreatedAt());
		businessUserOTP.setUpdatedAt(userOTP.getUpdatedAt());
		businessUserOTP.setNextTrial(null);
				
		return businessUserOTP;
	}

}
