package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.ClientFundOutput;
import innovitics.azimut.rest.entities.teacomputers.CompanyBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientFundsOutput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.CompanyBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientFundsRequest;
import innovitics.azimut.rest.models.teacomputers.GetCompanyBankAccountRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public class GetCompanyBankAccountsApiConsumer  extends RestTeaComputerApiConsumer<GetCompanyBankAccountRequest, CompanyBankAccountResponse[], GetCompanyBankAccountsInput, GetCompanyBankAccountsOutput>{

	@Override
	public HttpEntity<String> generateRequestFromInput(GetCompanyBankAccountsInput input) {
		GetCompanyBankAccountRequest request=new GetCompanyBankAccountRequest();
		this.populateCredentials(request);
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetCompanyBankAccountsOutput generateOutPutFromResponse(ResponseEntity<CompanyBankAccountResponse[]> responseEntity) {
		GetCompanyBankAccountsOutput getCompanyBankAccountsOutput=new GetCompanyBankAccountsOutput();
		
		List<CompanyBankAccountOutput> companyBankAccountOutputs=new ArrayList<CompanyBankAccountOutput>(); 
		
	
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{		
				for(int i=0;i<responseEntity.getBody().length;i++)
				{
					CompanyBankAccountOutput companyBankAccountOutput = new CompanyBankAccountOutput();
					
					companyBankAccountOutput.setAccountNo(null);
					companyBankAccountOutput.setBankId(null);
					companyBankAccountOutput.setBankName(null);
					companyBankAccountOutput.setBranchName(null);
					companyBankAccountOutput.setIban(null);
					companyBankAccountOutput.setSwiftCode(null);
					
					companyBankAccountOutputs.add(companyBankAccountOutput);
				}
				getCompanyBankAccountsOutput.setCompanyBankAccountOutputs(companyBankAccountOutputs);
		}
		
		return getCompanyBankAccountsOutput;
	}

	@Override
	public void validateResponse(ResponseEntity<CompanyBankAccountResponse[]> responseEntity)throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) 
	{
		this.logger.info("Handling the Exception in the Get Company Bank Accounts API:::");
		if(exception instanceof IntegrationException)
		{
			IntegrationException integrationException=(IntegrationException)exception;			
			return integrationException;
		}
		else
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
	}

	@Override
	public HttpMethod chooseHttpMethod() 
	{
		return HttpMethod.GET;
	}

	@Override
	protected String generateSignature(GetCompanyBankAccountRequest getCompanyBankAccountRequest) 
	{
		return this.teaComputersSignatureGenerator.generateSignature("CompanyBankAcc");
	}

	@Override
	protected void generateResponseSignature(CompanyBankAccountResponse[] teaComputerResponse)throws IntegrationException 
	{
		

		
	}

	@Override
	protected void generateResponseListSignature(CompanyBankAccountResponse[] teaComputerResponse) throws IntegrationException {
		
		
	}

}
