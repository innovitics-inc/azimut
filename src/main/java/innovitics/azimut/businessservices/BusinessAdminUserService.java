package innovitics.azimut.businessservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.AuthenticationResponse;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.user.Token;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.admin.AdminUser;
import innovitics.azimut.security.JwtUtil;
import innovitics.azimut.services.admin.AdminUserService;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.validations.validators.adminuser.LoginAdminUser;

@Service
public class BusinessAdminUserService extends AbstractBusinessService<BusinessAdminUser>{

	
	@Autowired AdminUserService adminUserService;
	@Autowired LoginAdminUser loginAdminUser;
	
	public AuthenticationResponse authenticateAdminUser(AuthenticationRequest authenticationRequest,JwtUtil jwtUtil) throws IntegrationException, BusinessException
	{
		
		this.validation.validate(authenticationRequest, loginAdminUser, BusinessAdminUser.class.getName());	
		try 
		{
			BusinessAdminUser businessAdminUser=new BusinessAdminUser(authenticationRequest.getEmail(),authenticationRequest.getPassword());
			this.restContract.getData(this.restContract.adminUserLoginMapper, businessAdminUser, null);
			Token token=jwtUtil.generateTokenUsingUserDetails(businessAdminUser);
			return new AuthenticationResponse(token, null);
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleException(exception);
		}
	}
	
	
	public BusinessAdminUser findAdminUserByUsername(String username) throws BusinessException
	{
		try 
		{
			BusinessAdminUser businessAdminUser=new BusinessAdminUser();
			this.adminUserService.findByUserName(username);
			businessAdminUser.setEmailAddress(username);
			return businessAdminUser;
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleBusinessException(exception,ErrorCode.USER_NOT_FOUND);
		}
	}
}
