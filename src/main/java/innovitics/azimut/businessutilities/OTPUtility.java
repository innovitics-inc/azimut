package innovitics.azimut.businessutilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.user.UserOTP;
import innovitics.azimut.services.user.UserOTPService;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.mapping.UserOtpMapper;
@Component
public class OTPUtility {
@Autowired UserOTPService  userOTPService;
@Autowired UserOtpMapper userOtpMapper;
@Autowired ExceptionHandler exceptionHandler;
@Autowired ListUtility<UserOTP> listUtility;

	public BusinessUserOTP addUserOtp(BusinessUserOTP businessUserOTP)
	{
		
		UserOTP userOTP=this.userOtpMapper.convertBusinessUnitToBasicUnit(businessUserOTP, true);
		
		return this.userOtpMapper.convertBasicUnitToBusinessUnit(this.userOTPService.save(userOTP));
	}
	
	
	private void getFilteredBusinessUSerOTP(BusinessUserOTP businessUserOTP) throws BusinessException
	{
		List<UserOTP> userOTPs=new ArrayList<UserOTP>();
		 try
		 {
			  userOTPs=this.userOTPService.getFilteredUserOTP(businessUserOTP.getCountryPhoneCode()+businessUserOTP.getPhoneNumber(), businessUserOTP.getFunctionality());
		 }
		 catch(Exception exception)
		 {
			 
			 this.exceptionHandler.handleBusinessException(exception, ErrorCode.NO_DATA_FOUND);
			 userOTPs=null;
		 }
	}
	
	
}
