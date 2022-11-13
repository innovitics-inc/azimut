package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.businessmodels.user.BusinessClientCashBalance;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.BaseRestConsumer;
import innovitics.azimut.rest.apis.teacomputers.AddClientBankAccountApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.AddClientBankAccountInput;
import innovitics.azimut.rest.entities.teacomputers.AddClientBankAccountOutput;
import innovitics.azimut.rest.models.teacomputers.AddClientBankAccountRequest;
import innovitics.azimut.rest.models.teacomputers.AddClientBankAccountResponse;
@Component
public class AddClientBankAccountMapper extends RestMapper<AddClientBankAccountInput, AddClientBankAccountOutput,AddClientBankAccountRequest ,AddClientBankAccountResponse, BusinessClientBankAccountDetails>{

	@Autowired AddClientBankAccountApiConsumer addClientBankAccountApiConsumer;

	
	public void consumeRestServiceInALoop(BusinessAzimutClient businessAzimutClient,String azId,Long azIdType) throws HttpClientErrorException, IntegrationException, Exception
	{
		for(BusinessClientBankAccountDetails businessClientBankAccountDetails:businessAzimutClient.getClientBankAccounts())
		{ 
			businessClientBankAccountDetails.setAzId(azId);
			businessClientBankAccountDetails.setAzIdType(azIdType);
			//this.consumeRestService(businessClientBankAccountDetails, null);
			
			this.wrapAdvancedBaseBusinessEntity(false, businessClientBankAccountDetails, null);
		}
	}
	
	
	
	@Override
	BusinessClientBankAccountDetails consumeRestService(BusinessClientBankAccountDetails businessClientBankAccountDetails,String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return this.createBusinessEntityFromOutput(this.addClientBankAccountApiConsumer.invoke(this.createInput(businessClientBankAccountDetails), AddClientBankAccountResponse.class, params));
	}

	@Override
	List<BusinessClientBankAccountDetails> consumeListRestService(BusinessClientBankAccountDetails baseBusinessEntity,
			String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	AddClientBankAccountInput createInput(BusinessClientBankAccountDetails businessClientBankAccountDetails) {
		AddClientBankAccountInput input=new AddClientBankAccountInput();
		input.setIdTypeId(businessClientBankAccountDetails.getAzIdType());
		input.setIdNumber(businessClientBankAccountDetails.getAzId());
		
		input.setBankId(businessClientBankAccountDetails.getBankId());
		input.setBranchId(businessClientBankAccountDetails.getBranchId());
		input.setCurrencyId(businessClientBankAccountDetails.getCurrencyId());
		input.setAccountNo(businessClientBankAccountDetails.getAccountNumber());
		input.setSwiftCode(businessClientBankAccountDetails.getSwiftCode());
		input.setIban(businessClientBankAccountDetails.getIban());
		return input;
	}

	@Override
	BusinessClientBankAccountDetails createBusinessEntityFromOutput(AddClientBankAccountOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<BusinessClientBankAccountDetails> createListBusinessEntityFromOutput(
			AddClientBankAccountOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected void setConsumer(BusinessClientBankAccountDetails businessClientBankAccountDetails) {
		this.consumer=addClientBankAccountApiConsumer;
		
	}
	
	

}
