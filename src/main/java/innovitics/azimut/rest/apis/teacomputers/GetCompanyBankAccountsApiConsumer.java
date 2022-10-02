package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.ClientFundOutput;
import innovitics.azimut.rest.entities.teacomputers.CompanyBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientFundsOutput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetCompanyBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientFundResponse;
import innovitics.azimut.rest.models.teacomputers.CompanyBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientFundsRequest;
import innovitics.azimut.rest.models.teacomputers.GetCompanyBankAccountRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetCompanyBankAccountsApiConsumer  extends RestTeaComputerApiConsumer<GetCompanyBankAccountRequest, CompanyBankAccountResponse[], GetCompanyBankAccountsInput, GetCompanyBankAccountsOutput>{

	
	public final static String PATH="/lookups/GetCompanyBankAcc"; 
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
					
					companyBankAccountOutput.setAccountNo(responseEntity.getBody()[i].getAccountNo());
					companyBankAccountOutput.setBankId(Long.valueOf(responseEntity.getBody()[i].getBankId()));
					companyBankAccountOutput.setBankName(responseEntity.getBody()[i].getBankName());
					companyBankAccountOutput.setBranchName(responseEntity.getBody()[i].getBranchName());
					companyBankAccountOutput.setIban(responseEntity.getBody()[i].getIban());
					companyBankAccountOutput.setSwiftCode(responseEntity.getBody()[i].getSwiftCode());
					companyBankAccountOutput.setAccountNo(responseEntity.getBody()[i].getAccountNo());
					companyBankAccountOutput.setCurrencyId(responseEntity.getBody()[i].getCurrencyID());
					companyBankAccountOutput.setCurrencyName(responseEntity.getBody()[i].getCurrencyName());
					
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
	protected void generateResponseListSignature(CompanyBankAccountResponse[] companyBankAccountResponses) throws IntegrationException 
	{
		if(this.arrayUtility.isArrayPopulated(companyBankAccountResponses))
		{
			for(CompanyBankAccountResponse  companyBankAccountResponse: companyBankAccountResponses)
			{
				if(companyBankAccountResponse!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("","CompanyBankAccResponse"),companyBankAccountResponse.getSignature())
							||!StringUtility.isStringPopulated(companyBankAccountResponse.getSignature())))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}

		
	}
	
	@Override
	public String generateURL(String params) 
	{
		return super.generateURL(params)+PATH;
	}
	

}
