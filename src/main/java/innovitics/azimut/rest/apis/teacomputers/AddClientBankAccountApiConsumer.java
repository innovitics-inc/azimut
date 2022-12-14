package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.AddClientBankAccountInput;
import innovitics.azimut.rest.entities.teacomputers.AddClientBankAccountOutput;
import innovitics.azimut.rest.models.teacomputers.AddAccountRequest;
import innovitics.azimut.rest.models.teacomputers.AddClientBankAccountRequest;
import innovitics.azimut.rest.models.teacomputers.AddClientBankAccountResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class AddClientBankAccountApiConsumer extends RestTeaComputerApiConsumer<AddClientBankAccountRequest, AddClientBankAccountResponse, AddClientBankAccountInput, AddClientBankAccountOutput>{

	public static final String PATH="/AddClientBankAcc";

	@Override
	public HttpEntity<String> generateRequestFromInput(AddClientBankAccountInput input) {
		AddClientBankAccountRequest request=new AddClientBankAccountRequest();
		this.populateCredentials(request);
		
		request.setIdNumber(input.getIdNumber());
		request.setIdTypeId(input.getIdTypeId());
		request.setAccountNo(input.getAccountNo());
		request.setBankId(input.getBankId());
		request.setBranchId(input.getBranchId());
		request.setCurrencyId(input.getCurrencyId());
		request.setSwiftCode(input.getSwiftCode());
		request.setIBAN(input.getIban());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public AddClientBankAccountOutput generateOutPutFromResponse(ResponseEntity<AddClientBankAccountResponse> responseEntity) {
		return new AddClientBankAccountOutput();
	}

	@Override
	public void validateResponse(ResponseEntity<AddClientBankAccountResponse> responseEntity) throws IntegrationException {
		
		if (this.validateResponseStatus(responseEntity)) 
		{
			this.generateResponseSignature(responseEntity.getBody());
		}
		if (responseEntity.getBody() == null) 
		{
			throw new IntegrationException(ErrorCode.NO_DATA_FOUND);
		} 

		
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	protected String generateSignature(AddClientBankAccountRequest addClientBankAccountRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",addClientBankAccountRequest.getIdTypeId()!=null?addClientBankAccountRequest.getIdTypeId().toString():null,addClientBankAccountRequest.getIdNumber());

	}

	@Override
	protected void generateResponseSignature(AddClientBankAccountResponse response) throws IntegrationException {
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
	protected void generateResponseListSignature(AddClientBankAccountResponse teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}

	@Override
	public Class<AddClientBankAccountResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return AddClientBankAccountResponse.class;
	}

}
