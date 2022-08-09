package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import innovitics.azimut.businessmodels.user.AzimutAccount;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.AddAccountApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.AddAccountInput;
import innovitics.azimut.rest.entities.teacomputers.AddAccountOutput;
import innovitics.azimut.rest.models.teacomputers.AddAccountResponse;
@Component
public class AddAccountMapper extends RestMapper<AddAccountInput, AddAccountOutput, AddAccountResponse, AzimutAccount>{

	@Autowired AddAccountApiConsumer addAccountApiConsumer;
	@Override
	AzimutAccount consumeRestService(AzimutAccount azimutAccount, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return this.createBusinessEntityFromOutput(this.addAccountApiConsumer.invoke(this.createInput(azimutAccount), AddAccountResponse.class, params));
	}

	@Override
	List<AzimutAccount> consumeListRestService(AzimutAccount baseBusinessEntity, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	AddAccountInput createInput(AzimutAccount azimutAccount) {
		AddAccountInput input=new AddAccountInput();
		input.setIdTypeId(azimutAccount.getIdType());
		input.setIdNumber(azimutAccount.getUserId());
		input.setIdDate(azimutAccount.getIdDate());
		input.setIdMaturityDate(azimutAccount.getIdMaturityDate());
		input.setClientAML(azimutAccount.getClientAML());
		input.setiDIssueCityId(azimutAccount.getiDIssueCityId());
		input.setiDIssueCountryId(azimutAccount.getiDIssueCountryId());
		input.setiDIssueDistrictId(azimutAccount.getiDIssueDistrictId());
		input.setCustomerNameAr(azimutAccount.getCustomerNameAr());
		input.setCustomerNameEn(azimutAccount.getCustomerNameEn());
		input.setBirthDate(azimutAccount.getBirthDate());
		input.setSexId(azimutAccount.getSexId());
		input.setClientTypeId(azimutAccount.getClientTypeId());
		input.setEmail(azimutAccount.getEmail());
		input.setMobile(azimutAccount.getPhoneNumber());
		input.setPhone(azimutAccount.getPhone());
		input.setAddressAr(azimutAccount.getAddressAr());
		input.setAddressEn(azimutAccount.getAddressEn());
		input.setCityId(azimutAccount.getCityId());
		input.setCountryId(azimutAccount.getCountryId());
		input.setNationalityId(azimutAccount.getNationalityId());
		input.setExternalcode(azimutAccount.getExternalcode());
		input.setOccupation(azimutAccount.getOccupation());
		input.setPostalNo(azimutAccount.getPostalNo());
		return input;
	}

	@Override
	AzimutAccount createBusinessEntityFromOutput(AddAccountOutput BaseOutput) {

		AzimutAccount azimutAccount=new AzimutAccount();
		
		return  azimutAccount;
	}

	@Override
	protected List<AzimutAccount> createListBusinessEntityFromOutput(AddAccountOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

}
