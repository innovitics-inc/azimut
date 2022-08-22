package innovitics.azimut.controllers.authreg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.AuthenticationResponse;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.Token;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.pdfgenerator.Executor;
import innovitics.azimut.utilities.businessutilities.EmailUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController extends BaseGenericRestController<AuthenticationResponse, String> {

	@Autowired BusinessUserService  businessUserService;
	@Autowired EmailUtility emailUtility;

	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<BaseGenericResponse<AuthenticationResponse>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception, BusinessException
	{
		BusinessUser businessUser = new BusinessUser();
		try {
			this.logger.info("Authentication Request:::"+authenticationRequest.toString());
			this.validation.validateAuthenticationCredentials(authenticationRequest);
			businessUser=this.businessUserService.beautifyUser(this.businessUserService.getByUserPhoneAndPassword(authenticationRequest.getCountryPhoneCode()+authenticationRequest.getPhoneNumber(),authenticationRequest.getPassword(),authenticationRequest.getDeviceId()));
			String tmpdir = System.getProperty("java.io.tmpdir");
			this.logger.info("Temp file path: " + tmpdir);
			businessUser.setTempDirectory(tmpdir);
			return this.generateBaseGenericResponse(AuthenticationResponse.class, new AuthenticationResponse(this.jwtUtil.generateTokenUsingUserDetails(businessUser),businessUser),null,null);
			
		} 
		catch (BusinessException businessException) {
			return this.handleBaseGenericResponseException(businessException);
		}
	}
	@RequestMapping(value="/refresh", method=RequestMethod.POST)
	public ResponseEntity<BaseGenericResponse<AuthenticationResponse>> refreshToken(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody AuthenticationRequest authenticationRequest) throws Exception, BusinessException
	{
		try 
		{			
			
			return this.generateBaseGenericResponse(AuthenticationResponse.class,new AuthenticationResponse(this.jwtUtil.validateTokenInBodyThenGenerateNewToken(token,authenticationRequest.getRefreshToken()),null), null, null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
		
		
	
	}
		
	@PostMapping(value="/forgotPassword",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<AuthenticationResponse>> forgotPassword(@RequestBody AuthenticationRequest authenticationRequest) throws BusinessException {
		try
		{
			BusinessUser businessUser=this.businessUserService.beautifyUser(this.businessUserService.forgotUserPassword(authenticationRequest));
			return this.generateBaseGenericResponse(AuthenticationResponse.class,new AuthenticationResponse(this.jwtUtil.generateTokenUsingUserDetails(businessUser),businessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/saveUserTemporarily",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<AuthenticationResponse>> saveUserTemporarily(@RequestBody BusinessUser businessUser) throws BusinessException {
		try
		{
			BusinessUser responseBusinessUser=this.businessUserService.saveUser(businessUser);
			return this.generateBaseGenericResponse(AuthenticationResponse.class,new AuthenticationResponse(this.jwtUtil.generateTokenUsingUserDetails(responseBusinessUser),responseBusinessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
}