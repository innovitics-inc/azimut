package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceOutput;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceRequest;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetClientCashBalanceApiConsumer extends RestTeaComputerApiConsumer<GetClientCashBalanceRequest, GetClientCashBalanceResponse, GetClientBalanceInput, GetClientBalanceOutput> {

	public static final String PATH="/GetClientCashBalance";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(GetClientBalanceInput input) {
		GetClientCashBalanceRequest request =new GetClientCashBalanceRequest();
		this.populateCredentials(request);
		request.setIdNumber(input.getIdNumber());
		request.setIdTypeId(input.getIdTypeId());	
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));		
		return httpEntity;
	}

	@Override
	public GetClientBalanceOutput generateOutPutFromResponse(ResponseEntity<GetClientCashBalanceResponse> responseEntity) 
	{
		GetClientBalanceOutput output=new GetClientBalanceOutput();
		GetClientCashBalanceResponse response=responseEntity.getBody();
		output.setBalance(response.getBalance());
		output.setCurrencyID(response.getCurrencyID());
		output.setCurrencyName(response.getCurrencyName());
		output.setPendingTransfer(response.getPendingTransfer());
		return output;
		
	}

	@Override
	public void validateResponse(ResponseEntity<GetClientCashBalanceResponse> responseEntity)
			throws IntegrationException {
			if(!this.validateResponseStatus(responseEntity))
			{
			throw	this.handleTeaComputerError(responseEntity.getBody().getMessage(),responseEntity.getBody().getErrorCode());
			}
			this.generateResponseSignature(responseEntity.getBody());
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
		
	}

	@Override
	public String generateURL(String params) {
		return super.generateURL(params)+PATH;
		
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	protected String generateSignature(GetClientCashBalanceRequest getClientCashBalanceRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",getClientCashBalanceRequest.getIdTypeId().toString(),getClientCashBalanceRequest.getIdNumber(),this.configProperties.getTeaComputersKey());
	}

	@Override
	protected void generateResponseSignature(GetClientCashBalanceResponse getClientCashBalanceResponse) throws IntegrationException {
		
		if(StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",
				getClientCashBalanceResponse!=null&&getClientCashBalanceResponse.getCurrencyID()!=null?getClientCashBalanceResponse.getCurrencyID().toString():null
				,getClientCashBalanceResponse!=null&&getClientCashBalanceResponse.getBalance()!=null?getClientCashBalanceResponse.getBalance().toString():null,
						this.configProperties.getTeaComputersKey()), getClientCashBalanceResponse.getSignature())||!StringUtility.isStringPopulated(getClientCashBalanceResponse!=null?getClientCashBalanceResponse.getSignature():null))
		{
			throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
		}
	}

	@Override
	protected void generateResponseListSignature(GetClientCashBalanceResponse teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}


}
