package innovitics.azimut.businessservices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.services.user.UserOTPService;
import innovitics.azimut.utilities.businessutilities.EmailUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.UserOtpMapper;

@Service
public class BusinessOTPService extends AbstractBusinessService<BusinessUserOTP>{		
	@Autowired UserOTPService  userOTPService;
	@Autowired UserOtpMapper userOtpMapper;
	@Autowired EmailUtility emailUtility;
	
		public BusinessUserOTP addUserOtp(BusinessUser tokenizedBusinessUser) throws BusinessException
		{
			BusinessUserOTP responseBusinessUserOTP=new BusinessUserOTP();
			try 
			{
				responseBusinessUserOTP=(BusinessUserOTP) this.restContract.getData(this.restContract.sendVerificationCodeMapper, new BusinessUserOTP(tokenizedBusinessUser.getUserPhone()), null);			
				this.userOtpMapper.convertBasicUnitToBusinessUnit(this.userOTPService.save(this.userOtpMapper.convertBusinessUnitToBasicUnit(responseBusinessUserOTP, true)));
			}
			catch(Exception exception)
			{
				throw this.exceptionHandler.handleException(exception);
			}
			return responseBusinessUserOTP;
		}
		
		
		public BusinessUserOTP verifyUserOtp(BusinessUser tokenizedBusinessUser,BusinessUserOTP businessUserOTP) throws BusinessException
		{
			BusinessUserOTP responseBusinessUserOTP=new BusinessUserOTP();
			try 
			{
				BusinessUserOTP localBusinessUserOTP=this.findOTPByUserPhone(tokenizedBusinessUser);
				localBusinessUserOTP.setOtp(businessUserOTP.getOtp());
				responseBusinessUserOTP=(BusinessUserOTP) this.restContract.getData(this.restContract.verifyMapper,localBusinessUserOTP, null);			
				this.userOtpMapper.convertBasicUnitToBusinessUnit(this.userOTPService.save(this.userOtpMapper.convertBusinessUnitToBasicUnit(responseBusinessUserOTP, true)));
				this.emailUtility.sendMailWithAttachment(tokenizedBusinessUser.getEmailAddress(),"Contract Download","Please find your contract attached", this.getContractFromBlob(tokenizedBusinessUser));
			}
			catch(Exception exception)
			{
				throw this.exceptionHandler.handleException(exception);
			}
			return responseBusinessUserOTP;
		}
		
		private BusinessUserOTP findOTPByUserPhone(BusinessUser tokenizedBusinessUser) throws BusinessException
		{
			try {
			return userOtpMapper.convertBasicUnitToBusinessUnit(this.userOTPService.findByUser(tokenizedBusinessUser.getUserPhone()));
			}
			catch(Exception exception)
			{
				throw this.handleBusinessException(exception, ErrorCode.INVALID_OTP);
			}
		}

		private ByteArrayOutputStream getContractFromBlob(BusinessUser tokenizedBusinessUser) throws BusinessException, IOException
		{
			return	this.blobFileUtility.downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(),  StringUtility.CONTRACTS_SUBDIRECTORY+"/"+tokenizedBusinessUser.getUserId(), StringUtility.CONTRACT_DOCUMENT_NAME +".pdf");
		}
		
}
