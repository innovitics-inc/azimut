package innovitics.azimut.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.AuthenticationResponse;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessAdminUserService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.security.JwtUtil;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.logging.MyLogger;

@RestController
@RequestMapping("/admin")
public class AdminLoginController extends BaseGenericRestController<AuthenticationResponse> {

	@Autowired BusinessAdminUserService businessAdminUserService;
	@PostMapping(value="/authenticate")
	public ResponseEntity<BaseGenericResponse<AuthenticationResponse>> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws Exception, BusinessException
	{	
		try 
		{				
			return this.generateBaseGenericResponse(AuthenticationResponse.class,this.businessAdminUserService.authenticateAdminUser(authenticationRequest,this.jwtUtil),null,null);
		} 
		catch (BusinessException businessException) {
			return this.handleBaseGenericResponseException(businessException,language);
		}
	}
	
}
