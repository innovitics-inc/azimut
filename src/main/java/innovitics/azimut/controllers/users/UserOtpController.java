package innovitics.azimut.controllers.users;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.BusinessUserOTP;
import innovitics.azimut.businessservices.BusinessOTPService;

import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.datautilities.StringUtility;

@RestController
@RequestMapping("/api/otp")
public class UserOtpController extends BaseGenericRestController<BusinessUserOTP,String>
{
@Autowired BusinessOTPService businessOTPService;
	@PostMapping(value="/sendOTP",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUserOTP>> sendOTP (
			@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestBody BusinessUserOTP businessUserOTP,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUserOTP.class,businessOTPService.addUserOtp(this.getCurrentRequestHolder(token)),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}

}
