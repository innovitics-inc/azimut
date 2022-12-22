package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.HoldClientBankAccountInput;
import innovitics.azimut.rest.entities.teacomputers.HoldClientBankAccountOutput;
import innovitics.azimut.rest.models.teacomputers.HoldClientBankAccountRequest;
import innovitics.azimut.rest.models.teacomputers.HoldClientBankAccountResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;

@Service
public class HoldClientBankAccountApiConsumer extends RestTeaComputerApiConsumer<HoldClientBankAccountRequest, HoldClientBankAccountResponse, HoldClientBankAccountInput, HoldClientBankAccountOutput>{

	public static final String PATH="/HoldClientBankAcc";
	@Override
	public HttpEntity<String> generateRequestFromInput(HoldClientBankAccountInput input) {
		HoldClientBankAccountRequest request=new HoldClientBankAccountRequest();
		this.populateCredentials(request);
		request.setIdNumber(input.getIdNumber());
		request.setIdTypeId(input.getIdTypeId());
		request.setAccountID(input.getAccountId());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;

	}

	@Override
	public HoldClientBankAccountOutput generateOutPutFromResponse(ResponseEntity<HoldClientBankAccountResponse> responseEntity) {
		return new HoldClientBankAccountOutput();
	}

	@Override
	public void validateResponse(ResponseEntity<HoldClientBankAccountResponse> responseEntity) throws IntegrationException {
		if (this.validateResponseStatus(responseEntity)) 
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		MyLogger.info("Handling the Exception in the Hold Client Bank Account API:::");
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
		return HttpMethod.POST;
	}

	@Override
	protected String generateSignature(HoldClientBankAccountRequest holdClientBankAccountRequest) {
		return this.teaComputersSignatureGenerator.generateSignature(holdClientBankAccountRequest.getIdTypeId().toString(),holdClientBankAccountRequest.getIdNumber(),holdClientBankAccountRequest.getAccountID().toString());
	}

	@Override
	protected void generateResponseSignature(HoldClientBankAccountResponse response) throws IntegrationException {
		if(response!=null)
		{
			if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",response.getMessage()), response.getSignature()))
					||!StringUtility.isStringPopulated(response.getSignature()))
			{
				throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
			}
			
		}
		else 
		{
			throw new IntegrationException(ErrorCode.NO_DATA_FOUND);
		}
		
		
	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}

	@Override
	protected void generateResponseListSignature(HoldClientBankAccountResponse teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<HoldClientBankAccountResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return HoldClientBankAccountResponse.class;
	}

}
