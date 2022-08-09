package innovitics.azimut.rest.mappers;

import java.util.List;

import innovitics.azimut.businessmodels.user.BusinessCompanyBankAccount;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.CompanyBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.GetCompanyBankAccountResponse;

public class GetCompanyBankAccountMapper extends RestMapper<GetCompanyBankAccountsInput, GetCompanyBankAccountsOutput, GetCompanyBankAccountResponse, BusinessCompanyBankAccount> {

	@Override
	BusinessCompanyBankAccount consumeRestService(BusinessCompanyBankAccount businessCompanyBankAccount, String params)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessCompanyBankAccount> consumeListRestService(BusinessCompanyBankAccount businessCompanyBankAccount,
			String params) throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	GetCompanyBankAccountsInput createInput(BusinessCompanyBankAccount businessCompanyBankAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	BusinessCompanyBankAccount createBusinessEntityFromOutput(GetCompanyBankAccountsOutput getCompanyBankAccountsOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<BusinessCompanyBankAccount> createListBusinessEntityFromOutput(
			GetCompanyBankAccountsOutput getCompanyBankAccountsOutput) {
		// TODO Auto-generated method stub
		return null;
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
