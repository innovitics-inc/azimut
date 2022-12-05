package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import innovitics.azimut.businessmodels.user.AzimutAccount;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.BaseRestConsumer;
import innovitics.azimut.rest.apis.teacomputers.CheckAccountApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.ClientAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientAccountsRequest;
import innovitics.azimut.rest.models.teacomputers.GetClientAccountsResponse;
import innovitics.azimut.utilities.crosslayerenums.UserIdType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class CheckAccountMapper extends RestMapper<GetClientAccountsInput, GetClientAccountsOutput,GetClientAccountsRequest,ClientAccountResponse[], AzimutAccount>{

	@Autowired CheckAccountApiConsumer checkAccountApiConsumer;
	@Autowired ListUtility<ClientAccountOutput> listUtility;
	@Override
	AzimutAccount consumeRestService(AzimutAccount azimutAccount, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return null;
	}

	@Override
	List<AzimutAccount> consumeListRestService(AzimutAccount azimutAccount, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return  this.createListBusinessEntityFromOutput(this.checkAccountApiConsumer.invoke(this.createInput(azimutAccount),ClientAccountResponse[].class, params));
	}

	@Override
	GetClientAccountsInput createInput(AzimutAccount azimutAccount) 
	{
		GetClientAccountsInput input =new GetClientAccountsInput();
		input.setMobile(azimutAccount.getPhoneNumber());
		input.setIdNumber(azimutAccount.getUserId());
		input.setIdTypeId(azimutAccount.getIdType());
		return input;
	}

	@Override
	AzimutAccount createBusinessEntityFromOutput(GetClientAccountsOutput getClientAccountsOutput) {
		return null;
		
	}

	@Override
	protected List<AzimutAccount> createListBusinessEntityFromOutput(GetClientAccountsOutput getClientAccountsOutput) {
		List<AzimutAccount> azimutAccounts=new ArrayList<AzimutAccount>();
		if( getClientAccountsOutput!=null&&this.listUtility.isListPopulated(getClientAccountsOutput.getClientAccountOutputs()))	
		{
			for(ClientAccountOutput clientAccountOutput: getClientAccountsOutput.getClientAccountOutputs())
			{
				azimutAccounts.add(this.getConversion(clientAccountOutput));
			}
			
		}
		return azimutAccounts;
	}
	
	
	AzimutAccount getConversion(ClientAccountOutput clientAccountOutput)
	{
		
		AzimutAccount azimutAccount=new AzimutAccount();
	
		azimutAccount.setFullName(clientAccountOutput.getClientName());
		azimutAccount.setPhoneNumber(clientAccountOutput.getMobile());
		azimutAccount.setAzId(clientAccountOutput.getIdNumber());
		azimutAccount.setAzIdType(clientAccountOutput.getIdTypeId());
		
		azimutAccount.setUserIdType(UserIdType.getById(clientAccountOutput.getIdTypeId()).getType());
		azimutAccount.setUserIdTypeAr(UserIdType.getById(clientAccountOutput.getIdTypeId()).getTypeAr());
		
		return azimutAccount; 	
	}

	@Override
	protected void setConsumer(AzimutAccount baseBusinessEntity) {
		this.consumer=checkAccountApiConsumer;
		
	}

}
