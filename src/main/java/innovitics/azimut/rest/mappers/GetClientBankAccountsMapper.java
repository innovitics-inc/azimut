package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetClientBankAccountsApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.ClientBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientBankAccountsResponse;
import innovitics.azimut.rest.models.teacomputers.TransactionResponse;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class GetClientBankAccountsMapper extends RestMapper<GetClientBankAccountsInput, GetClientBankAccountsOutput, GetClientBankAccountsResponse, BusinessClientBankAccountDetails>{

@Autowired ListUtility<ClientBankAccountOutput> listUtility;
@Autowired GetClientBankAccountsApiConsumer getClientBankAccountsApiConsumer;
	@Override
	BusinessClientBankAccountDetails consumeRestService(BusinessClientBankAccountDetails businessClientBankAccountDetails,
			String params) throws HttpClientErrorException, Exception {
		List<BusinessClientBankAccountDetails> businessClientBankAccountsDetails=new ArrayList<BusinessClientBankAccountDetails>();
		businessClientBankAccountsDetails=this.createListBusinessEntityFromOutput(this.getClientBankAccountsApiConsumer.invoke(this.createInput(businessClientBankAccountDetails),ClientBankAccountResponse[].class, params));
		for(BusinessClientBankAccountDetails iterator:businessClientBankAccountsDetails)
		{
			if(iterator!=null&&iterator.getId().longValue()==businessClientBankAccountDetails.getId().longValue())
			{
				return iterator;
			}
		}		
		return null;
	}

	@Override
	List<BusinessClientBankAccountDetails> consumeListRestService(BusinessClientBankAccountDetails businessClientBankAccountDetails,
			String params) throws HttpClientErrorException, Exception {
		return  this.createListBusinessEntityFromOutput(this.getClientBankAccountsApiConsumer.invoke(this.createInput(businessClientBankAccountDetails),ClientBankAccountResponse[].class, params));
	}

	@Override
	GetClientBankAccountsInput createInput(BusinessClientBankAccountDetails businessClientBankAccountDetails) {
		GetClientBankAccountsInput input=new  GetClientBankAccountsInput();
		input.setIdNumber(businessClientBankAccountDetails.getAzId());
		input.setIdTypeId(businessClientBankAccountDetails.getAzIdType());
		input.setBankId(businessClientBankAccountDetails.getBankId());
		return input;
	}

	@Override
	BusinessClientBankAccountDetails createBusinessEntityFromOutput(GetClientBankAccountsOutput getClientBankAccountsOutput) {
		return this.getConversion(getClientBankAccountsOutput.getClientBankAccountOutput());
	}

	@Override
	protected List<BusinessClientBankAccountDetails> createListBusinessEntityFromOutput(
			GetClientBankAccountsOutput getClientBankAccountsOutput) {
		List<BusinessClientBankAccountDetails> businessClientBankAccountsDetails=new ArrayList<BusinessClientBankAccountDetails>();	
		if(getClientBankAccountsOutput!=null&&listUtility.isListPopulated(getClientBankAccountsOutput.getClientBankAccountOutputs()))
		{
			for(ClientBankAccountOutput clientBankAccountOutput:getClientBankAccountsOutput.getClientBankAccountOutputs())
			{
				businessClientBankAccountsDetails.add(this.getConversion(clientBankAccountOutput));
			}
		}
		else
		{
			return new ArrayList<BusinessClientBankAccountDetails>();
		}
		return businessClientBankAccountsDetails;
	}
	
	BusinessClientBankAccountDetails getConversion(ClientBankAccountOutput clientBankAccountOutput)
	{
		BusinessClientBankAccountDetails businessClientBankAccountDetails=new BusinessClientBankAccountDetails();	
		if(clientBankAccountOutput!=null)
		{
			businessClientBankAccountDetails.setId(clientBankAccountOutput.getBankId());
			businessClientBankAccountDetails.setBankName(clientBankAccountOutput.getBankName());
			businessClientBankAccountDetails.setBranchId(clientBankAccountOutput.getBranchId());
			businessClientBankAccountDetails.setBranchName(clientBankAccountOutput.getBranchName());
			
			businessClientBankAccountDetails.setCurrencyName(clientBankAccountOutput.getCurrencyName());
			
			businessClientBankAccountDetails.setAccountNumber(clientBankAccountOutput.getAccountNo());
			businessClientBankAccountDetails.setIban(clientBankAccountOutput.getIban());
			businessClientBankAccountDetails.setAccountId(clientBankAccountOutput.getAccountId());
			businessClientBankAccountDetails.setStatus(clientBankAccountOutput.getStatusId());
			businessClientBankAccountDetails.setStatusName(clientBankAccountOutput.getAccountStatusName());
			businessClientBankAccountDetails.setAccountStatus(clientBankAccountOutput.getAccountStatus());
			businessClientBankAccountDetails.setSwiftCode(clientBankAccountOutput.getSwiftCode());
			this.logger.info("BusinessClientBankAccountDetails::::"+businessClientBankAccountDetails.toString());
		}
		return businessClientBankAccountDetails;		
	}

}
