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
import innovitics.azimut.rest.models.teacomputers.GetClientBankAccountsRequest;
import innovitics.azimut.rest.models.teacomputers.GetClientBankAccountsResponse;
import innovitics.azimut.rest.models.teacomputers.TransactionResponse;
import innovitics.azimut.utilities.crosslayerenums.ClientBankAccountStatus;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class GetClientBankAccountsMapper extends RestMapper<GetClientBankAccountsInput, GetClientBankAccountsOutput,GetClientBankAccountsRequest ,ClientBankAccountResponse[], BusinessClientBankAccountDetails>{

@Autowired ListUtility<ClientBankAccountOutput> listUtility;
@Autowired GetClientBankAccountsApiConsumer getClientBankAccountsApiConsumer;

	@Override
	BusinessClientBankAccountDetails consumeRestService(BusinessClientBankAccountDetails businessClientBankAccountDetails,
			String params) throws HttpClientErrorException, Exception {
		List<BusinessClientBankAccountDetails> businessClientBankAccountsDetails=new ArrayList<BusinessClientBankAccountDetails>();
		businessClientBankAccountsDetails=this.createListBusinessEntityFromOutput(this.getClientBankAccountsApiConsumer.invoke(this.createInput(businessClientBankAccountDetails),ClientBankAccountResponse[].class, params));
		for(BusinessClientBankAccountDetails iterator:businessClientBankAccountsDetails)
		{
			if(iterator!=null&&NumberUtility.areLongValuesMatching(iterator.getAccountId(), businessClientBankAccountDetails.getAccountId()))
			{
				return iterator;
			}
		}		
		return new BusinessClientBankAccountDetails();
	}

	@Override
	List<BusinessClientBankAccountDetails> consumeListRestService(BusinessClientBankAccountDetails businessClientBankAccountDetails,
			String params) throws HttpClientErrorException, Exception {
		return  this.createListBusinessEntityFromOutput(this.getClientBankAccountsApiConsumer.invoke(this.createInput(businessClientBankAccountDetails),ClientBankAccountResponse[].class, params),BooleanUtility.isTrue(businessClientBankAccountDetails.getIsActive()));
	}

	@Override
	GetClientBankAccountsInput createInput(BusinessClientBankAccountDetails businessClientBankAccountDetails) {
		GetClientBankAccountsInput input=new  GetClientBankAccountsInput();
		input.setIdNumber(businessClientBankAccountDetails.getAzId());
		input.setIdTypeId(businessClientBankAccountDetails.getAzIdType());
		input.setAccountId(businessClientBankAccountDetails.getAccountId());
		input.setIsActive(businessClientBankAccountDetails.getIsActive());
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
	
	protected List<BusinessClientBankAccountDetails> createListBusinessEntityFromOutput(
			GetClientBankAccountsOutput getClientBankAccountsOutput,boolean isActive) {
		List<BusinessClientBankAccountDetails> businessClientBankAccountsDetails=new ArrayList<BusinessClientBankAccountDetails>();	
		if(getClientBankAccountsOutput!=null&&listUtility.isListPopulated(getClientBankAccountsOutput.getClientBankAccountOutputs()))
		{
			for(ClientBankAccountOutput clientBankAccountOutput:getClientBankAccountsOutput.getClientBankAccountOutputs())
			{
				if(isActive)
				{
					if(clientBankAccountOutput!=null&&NumberUtility.areLongValuesMatching(clientBankAccountOutput.getAccountStatus(), ClientBankAccountStatus.ACTIVE.getStatusId()))
						businessClientBankAccountsDetails.add(this.getConversion(clientBankAccountOutput));
				}
				else
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
			businessClientBankAccountDetails.setCurrencyId(clientBankAccountOutput.getCurrencyId());
			businessClientBankAccountDetails.setAccountNumber(clientBankAccountOutput.getAccountNo());
			businessClientBankAccountDetails.setIban(clientBankAccountOutput.getIban());
			businessClientBankAccountDetails.setAccountId(clientBankAccountOutput.getAccountId());
			businessClientBankAccountDetails.setStatus(clientBankAccountOutput.getStatusId());
			businessClientBankAccountDetails.setStatusName(clientBankAccountOutput.getAccountStatusName());
			businessClientBankAccountDetails.setAccountStatus(clientBankAccountOutput.getAccountStatus());
			businessClientBankAccountDetails.setSwiftCode(clientBankAccountOutput.getSwiftCode());
			businessClientBankAccountDetails.setIsLocal(false);
			this.logger.info("BusinessClientBankAccountDetails::::"+businessClientBankAccountDetails.toString());
		}
		return businessClientBankAccountDetails;		
	}

	@Override
	protected void setConsumer(BusinessClientBankAccountDetails businessClientBankAccountDetails) {
		this.consumer=getClientBankAccountsApiConsumer;
		
	}

}
