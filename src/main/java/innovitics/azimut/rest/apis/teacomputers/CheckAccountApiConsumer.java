package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.ClientAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientAccountsInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientAccountsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientAccountsRequest;
import innovitics.azimut.rest.models.teacomputers.TeaComputerResponse;
import innovitics.azimut.rest.models.teacomputers.TransactionResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class CheckAccountApiConsumer extends RestTeaComputerApiConsumer<GetClientAccountsRequest, ClientAccountResponse[], GetClientAccountsInput, GetClientAccountsOutput> {

	public static final String PATH="/CheckAccount";
	public static final String MOBILE_NUMBER_NOT_EXISTING_CODE="79";
	@Override
	public HttpEntity<String> generateRequestFromInput(GetClientAccountsInput input) {
		GetClientAccountsRequest request=new GetClientAccountsRequest();
		this.populateCredentials(request);
		request.setMobile(input.getMobile());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetClientAccountsOutput generateOutPutFromResponse(ResponseEntity<ClientAccountResponse[]> responseEntity) {
		GetClientAccountsOutput output=new GetClientAccountsOutput();
		List<ClientAccountOutput> clientAccountOutputs=new ArrayList<ClientAccountOutput>();		
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{		
				for(int i=0;i<responseEntity.getBody().length;i++)
				{
					ClientAccountOutput clientAccountOutput = new ClientAccountOutput();
					clientAccountOutput.setClientName(responseEntity.getBody()[i].getClientName());
					clientAccountOutput.setMobile(responseEntity.getBody()[i].getMobile());
					
					if(StringUtility.isStringPopulated(responseEntity.getBody()[i].getIdTypeId()))
					{
						clientAccountOutput.setIdTypeId(Long.valueOf(responseEntity.getBody()[i].getIdTypeId()));
					}
					clientAccountOutput.setIdNumber(responseEntity.getBody()[i].getIdNumber());
					
					clientAccountOutput.setMobile(responseEntity.getBody()[i].getMobile());
					
					clientAccountOutputs.add(clientAccountOutput);
				}
				output.setClientAccountOutputs(clientAccountOutputs);
		}	
		return output;
	}

	@Override
	public void validateResponse(ResponseEntity<ClientAccountResponse[]> responseEntity) throws IntegrationException 
	{
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());	
		}

		
	}
	
	@Override
	public IntegrationException handleException(Exception exception) 
	{
		this.logger.info("Handling the Exception in the Check Account API:::");
		if(exception instanceof IntegrationException)
		{
			this.logger.info("The exception was found to be an integration exception:::");
			IntegrationException integrationException=(IntegrationException)exception;
			
			this.logger.info("Check Account API Integration Exception error Code:::"+integrationException.getErrorCode());
			this.logger.info("Check Account API Integration Exception error message:::"+integrationException.getErrorMessage());
			
			return integrationException;
		}
		else
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
		
	}
	
	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	protected String generateSignature(GetClientAccountsRequest request) {
		if(request!=null&&StringUtility.isStringPopulated(request.getIdNumber())&&request.getIdTypeId()!=null&&StringUtility.isStringPopulated(request.getIdTypeId().toString()))
		{
			return this.teaComputersSignatureGenerator.generateSignature(request.getIdTypeId()!=null?request.getIdTypeId().toString():null,request.getIdNumber(),request.getMobile());
		}
		else
		{
			return this.teaComputersSignatureGenerator.generateSignature(request.getMobile());
		}
		
	}

	@Override
	protected void generateResponseSignature(ClientAccountResponse[] teaComputerResponse) throws IntegrationException 
	{
		
	}

	@Override
	protected void generateResponseListSignature(ClientAccountResponse[] clientAccountResponses) throws IntegrationException 
	{
		if(this.arrayUtility.isArrayPopulated(clientAccountResponses))
		{
			for(ClientAccountResponse  clientAccountResponse: clientAccountResponses)
			{
				if(clientAccountResponse!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",clientAccountResponse.getClientName(),
							clientAccountResponse.getMobile()), clientAccountResponse.getSignature()))
							||!StringUtility.isStringPopulated(clientAccountResponse.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}

	}

	@Override
	public Class<ClientAccountResponse[]> getResponseClassType() {
		
		return ClientAccountResponse[].class;
	}
		
}
