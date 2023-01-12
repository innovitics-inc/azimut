package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.oldsystem.AdminLoginApiConsumer;
import innovitics.azimut.rest.entities.oldsystem.AdminUserLoginInput;
import innovitics.azimut.rest.entities.oldsystem.AdminUserLoginOutput;
import innovitics.azimut.rest.models.old.AdminUserLoginRequest;
import innovitics.azimut.rest.models.old.AdminUserLoginResponse;

@Component
public class AdminUserLoginMapper extends RestMapper<AdminUserLoginInput, AdminUserLoginOutput, AdminUserLoginRequest, AdminUserLoginResponse, BusinessAdminUser> {

	@Autowired AdminLoginApiConsumer adminLoginApiConsumer;
	
	@Override
	BusinessAdminUser consumeRestService(BusinessAdminUser businessAdminUser, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return null;
	}

	@Override
	List<BusinessAdminUser> consumeListRestService(BusinessAdminUser businessAdminUser, String params)throws IntegrationException, HttpClientErrorException, Exception {

		return null;
	}

	@Override
	AdminUserLoginInput createInput(BusinessAdminUser businessAdminUser) {
		return new AdminUserLoginInput(businessAdminUser.getEmailAddress(),businessAdminUser.getPassword());
	}

	@Override
	BusinessAdminUser createBusinessEntityFromOutput(AdminUserLoginOutput BaseOutput) {
		return new BusinessAdminUser();
	}

	@Override
	protected List<BusinessAdminUser> createListBusinessEntityFromOutput(AdminUserLoginOutput BaseOutput) {
		return null;
	}

	@Override
	protected void setConsumer(BusinessAdminUser baseBusinessEntity) {
		this.consumer=adminLoginApiConsumer;
	}

	

}
