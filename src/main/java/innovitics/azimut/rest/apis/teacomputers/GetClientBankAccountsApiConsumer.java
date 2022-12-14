package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.ClientBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBankAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBankAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientBankAccountsRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetClientBankAccountsApiConsumer extends RestTeaComputerApiConsumer<GetClientBankAccountsRequest, ClientBankAccountResponse[], GetClientBankAccountsInput, GetClientBankAccountsOutput>{

	
	public static final String PATH="/GetClientBankAcc";
	public static final String ACCOUNT_NOT_EXISTING="185";
	public static final String ACTIVE_ACCOUNT_STATUS="2";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(GetClientBankAccountsInput input) {
		GetClientBankAccountsRequest request= new GetClientBankAccountsRequest();
		this.populateCredentials(request);
		request.setIdNumber(input.getIdNumber());
		request.setIdTypeId(input.getIdTypeId());
		request.setAccountID(input.getAccountId());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));			
		return httpEntity;
	}

	@Override
	public GetClientBankAccountsOutput generateOutPutFromResponse(
			ResponseEntity<ClientBankAccountResponse[]> responseEntity) {
		GetClientBankAccountsOutput getClientBankAccountsOutput=new GetClientBankAccountsOutput();		
		if(responseEntity!=null)
		{
			if(arrayUtility.isArrayPopulated(responseEntity.getBody()))
			{
				List<ClientBankAccountOutput> clientBankAccountOutputs=new ArrayList<ClientBankAccountOutput>();
				for(ClientBankAccountResponse clientBankAccountResponse:responseEntity.getBody())
				{
					
						ClientBankAccountOutput clientBankAccountOutput=new ClientBankAccountOutput();
						clientBankAccountOutput.setBankId(StringUtility.isStringPopulated(clientBankAccountResponse.getBankId())?Long.valueOf(clientBankAccountResponse.getBankId()):null);
						clientBankAccountOutput.setBankName(clientBankAccountResponse.getBankName());
						clientBankAccountOutput.setBranchId(StringUtility.isStringPopulated(clientBankAccountResponse.getBranchId())?Long.valueOf(clientBankAccountResponse.getBranchId()):null);
						clientBankAccountOutput.setBranchName(clientBankAccountResponse.getBranchName());
						clientBankAccountOutput.setCurrencyId(StringUtility.isStringPopulated(clientBankAccountResponse.getCurrencyId())?Long.valueOf(clientBankAccountResponse.getCurrencyId()):null);
						clientBankAccountOutput.setCurrencyName(clientBankAccountResponse.getCurrencyName());					
						clientBankAccountOutput.setAccountNo(clientBankAccountResponse.getAccountNo());
						clientBankAccountOutput.setIban(clientBankAccountResponse.getiBAN());
						clientBankAccountOutput.setAccountId(clientBankAccountResponse.getAccountID());
						clientBankAccountOutput.setAccountStatus(StringUtility.isStringPopulated(clientBankAccountResponse.getAccountStatus())?Long.valueOf(clientBankAccountResponse.getAccountStatus()):null);
						clientBankAccountOutput.setAccountStatusName(clientBankAccountResponse.getAccountStatusName());
						clientBankAccountOutput.setSwiftCode(clientBankAccountResponse.getSwiftCode());
						clientBankAccountOutputs.add(clientBankAccountOutput);
					
					
				}
				getClientBankAccountsOutput.setClientBankAccountOutputs(clientBankAccountOutputs);
			}
			else
			{
				getClientBankAccountsOutput.setClientBankAccountOutputs(null);
			}
			
		}
		return getClientBankAccountsOutput;
	}

	@Override
	public void validateResponse(ResponseEntity<ClientBankAccountResponse[]> responseEntity)
			throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
		
		
	}
	
	@Override
	public IntegrationException handleException(Exception exception) {
		if(exception instanceof IntegrationException)
		{
			IntegrationException integrationException=(IntegrationException)exception;			
			return integrationException;
		}
		else
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
		
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
		
	}

	@Override
	protected String generateSignature(GetClientBankAccountsRequest getClientBankAccountsRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",getClientBankAccountsRequest.getIdTypeId().toString(),getClientBankAccountsRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(ClientBankAccountResponse[] clientBankAccountResponses) throws IntegrationException {
		if(this.arrayUtility.isArrayPopulated(clientBankAccountResponses))
		{
			for(ClientBankAccountResponse  clientBankAccountResponse: clientBankAccountResponses)
			{
				if(clientBankAccountResponse!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",clientBankAccountResponse.getBankId().toString(),
							clientBankAccountResponse.getAccountNo()),clientBankAccountResponse.getSignature()!=null?clientBankAccountResponse.getSignature():null))
							||!StringUtility.isStringPopulated(clientBankAccountResponse.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}

	}

	@Override
	protected void generateResponseListSignature(ClientBankAccountResponse[] teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<ClientBankAccountResponse[]> getResponseClassType() {
		// TODO Auto-generated method stub
		return ClientBankAccountResponse[].class;
	}
	

}
