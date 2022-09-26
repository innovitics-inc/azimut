package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.CompanyBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetCompanyBankAccountRequest;

public class GetCompanyBankAccountsApiConsumer  extends RestTeaComputerApiConsumer<GetCompanyBankAccountRequest, CompanyBankAccountResponse[], GetCompanyBankAccountsInput, GetCompanyBankAccountsOutput>{

	@Override
	public HttpEntity<String> generateRequestFromInput(GetCompanyBankAccountsInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetCompanyBankAccountsOutput generateOutPutFromResponse(ResponseEntity<CompanyBankAccountResponse[]> responseEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateResponse(ResponseEntity<CompanyBankAccountResponse[]> responseEntity)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String generateSignature(GetCompanyBankAccountRequest teaComputerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateResponseSignature(CompanyBankAccountResponse[] teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateResponseListSignature(CompanyBankAccountResponse[] teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

}
