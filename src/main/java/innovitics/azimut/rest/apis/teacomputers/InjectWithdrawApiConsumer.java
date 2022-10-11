package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.InjectWithdrawInput;
import innovitics.azimut.rest.entities.teacomputers.InjectWithdrawOutput;
import innovitics.azimut.rest.models.teacomputers.InjectWithdrawRequest;
import innovitics.azimut.rest.models.teacomputers.InjectWithdrawResponse;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class InjectWithdrawApiConsumer extends RestTeaComputerApiConsumer<InjectWithdrawRequest, InjectWithdrawResponse, InjectWithdrawInput, InjectWithdrawOutput> {

	@Override
	public HttpEntity<String> generateRequestFromInput(InjectWithdrawInput input) {
		InjectWithdrawRequest request=new InjectWithdrawRequest();
		this.populateCredentials(request);
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setOrderDate(DateUtility.getCurrentDayMonthYear());
		request.setModuleTypeId(input.getModuleType());
		request.setOrderValue(input.getOrderValue());
		request.setAccountNo(input.getAccountNo());
		request.setCurrencyId(input.getCurrencyId());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public InjectWithdrawOutput generateOutPutFromResponse(ResponseEntity<InjectWithdrawResponse> responseEntity) {
		InjectWithdrawOutput injectWithdrawOutput=new InjectWithdrawOutput();
		injectWithdrawOutput.setOrderId(responseEntity.getBody().getOrderId());
		return injectWithdrawOutput;
	}

	@Override
	public void validateResponse(ResponseEntity<InjectWithdrawResponse> responseEntity) throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Inject/Withdraw API:::");
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
	protected String generateSignature(InjectWithdrawRequest injectWithdrawRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",injectWithdrawRequest.getIdTypeId().toString(),injectWithdrawRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(InjectWithdrawResponse injectWithdrawResponse) throws IntegrationException 
	{
		if(injectWithdrawResponse!=null)
		{
			if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",injectWithdrawResponse.getMessage()), injectWithdrawResponse.getSignature()))
					||!StringUtility.isStringPopulated(injectWithdrawResponse.getSignature()))
			{
				throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
			}
			
		}	
	}

	@Override
	protected void generateResponseListSignature(InjectWithdrawResponse injectWithdrawResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generateURL(String params) 
	{
		return super.generateURL(null)+"/"+params;
	}
}
