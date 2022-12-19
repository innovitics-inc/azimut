package innovitics.azimut.businessservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.user.UserOTP;
import innovitics.azimut.services.user.UserOTPService;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.UserOtpMapper;

@Service
public class BusinessOTPService extends AbstractBusinessService<BusinessUserOTP>{		
	@Autowired UserOTPService  userOTPService;
	@Autowired UserOtpMapper userOtpMapper;

	
		public BusinessUserOTP addUserOtp(BusinessUser tokenizedBusinessUser)
		{
			BusinessUserOTP responseBusinessUserOTP=new BusinessUserOTP();
			try 
			{
				responseBusinessUserOTP=(BusinessUserOTP) this.restContract.getData(this.restContract.sendVerificationCodeMapper, new BusinessUserOTP(tokenizedBusinessUser.getUserPhone()), null);			
				return this.userOtpMapper.convertBasicUnitToBusinessUnit(this.userOTPService.save(this.userOtpMapper.convertBusinessUnitToBasicUnit(responseBusinessUserOTP, true)));
			}
			catch(Exception exception)
			{
				this.exceptionHandler.handleException(exception);
			}
			return responseBusinessUserOTP;
		}
		
		
		public BusinessUserOTP verifyUserOtp(BusinessUser tokenizedBusinessUser,BusinessUserOTP businessUserOTP)
		{
			BusinessUserOTP responseBusinessUserOTP=new BusinessUserOTP();
			try 
			{
				responseBusinessUserOTP=(BusinessUserOTP) this.restContract.getData(this.restContract.verifyMapper, new BusinessUserOTP(tokenizedBusinessUser.getUserPhone()), null);			
				return this.userOtpMapper.convertBasicUnitToBusinessUnit(this.userOTPService.save(this.userOtpMapper.convertBusinessUnitToBasicUnit(responseBusinessUserOTP, true)));
			}
			catch(Exception exception)
			{
				this.exceptionHandler.handleException(exception);
			}
			return responseBusinessUserOTP;
		}
		


}
