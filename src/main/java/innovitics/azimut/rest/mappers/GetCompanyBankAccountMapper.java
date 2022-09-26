package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import innovitics.azimut.businessmodels.user.BusinessCompanyBankAccount;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetCompanyBankAccountsApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.CompanyBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.CompanyBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetCompanyBankAccountResponse;
import innovitics.azimut.utilities.datautilities.ListUtility;

public class GetCompanyBankAccountMapper extends RestMapper<GetCompanyBankAccountsInput, GetCompanyBankAccountsOutput, GetCompanyBankAccountResponse, BusinessCompanyBankAccount> {

	@Autowired GetCompanyBankAccountsApiConsumer getCompanyBankAccountsApiConsumer;
	@Autowired ListUtility<CompanyBankAccountOutput> companyBankAccountListUtility;
	
	@Override
	BusinessCompanyBankAccount consumeRestService(BusinessCompanyBankAccount businessCompanyBankAccount, String params) throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessCompanyBankAccount> consumeListRestService(BusinessCompanyBankAccount businessCompanyBankAccount, String params) throws HttpClientErrorException, Exception {
		return  this.createListBusinessEntityFromOutput(this.getCompanyBankAccountsApiConsumer.invoke(this.createInput(businessCompanyBankAccount),CompanyBankAccountResponse[].class, params));
	}

	@Override
	GetCompanyBankAccountsInput createInput(BusinessCompanyBankAccount businessCompanyBankAccount) {
		GetCompanyBankAccountsInput input=new GetCompanyBankAccountsInput();
		return input;
	}

	@Override
	BusinessCompanyBankAccount createBusinessEntityFromOutput(GetCompanyBankAccountsOutput getCompanyBankAccountsOutput) {
		
		return null;
	}

	@Override
	protected List<BusinessCompanyBankAccount> createListBusinessEntityFromOutput(GetCompanyBankAccountsOutput getCompanyBankAccountsOutput) 
	{
		List<BusinessCompanyBankAccount> businessCompanyBankAccounts=new ArrayList<BusinessCompanyBankAccount>();
		if(getCompanyBankAccountsOutput!=null&&companyBankAccountListUtility.isListPopulated(getCompanyBankAccountsOutput.getCompanyBankAccountOutputs()))
		{
			
		}
		
		return businessCompanyBankAccounts;
	}
	
	BusinessCompanyBankAccount getConversion(CompanyBankAccountOutput companyBankAccountOutput)
	{
		BusinessCompanyBankAccount businessCompanyBankAccount=new BusinessCompanyBankAccount();
		if(companyBankAccountOutput!=null)
		{
					
		
		}
		return businessCompanyBankAccount; 
		
	}

}
