package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.ClientBalanceOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceOutput;
import innovitics.azimut.rest.entities.teacomputers.GetTransactionsOutput;
import innovitics.azimut.rest.entities.teacomputers.TransactionOutput;
import innovitics.azimut.rest.models.teacomputers.ClientCashBalanceResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceRequest;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceResponse;
import innovitics.azimut.rest.models.teacomputers.TransactionResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.MyLogger;
@Service
public class GetClientCashBalanceApiConsumer extends RestTeaComputerApiConsumer<GetClientCashBalanceRequest, ClientCashBalanceResponse[], GetClientBalanceInput, GetClientBalanceOutput> {

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
	public GetClientBalanceOutput generateOutPutFromResponse(ResponseEntity<ClientCashBalanceResponse[]> responseEntity) {
		
		GetClientBalanceOutput output=new GetClientBalanceOutput();
		List<ClientBalanceOutput> clientBalanceOutputs=new ArrayList<ClientBalanceOutput>();
		
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{		
				for(int i=0;i<responseEntity.getBody().length;i++)
				{
					ClientBalanceOutput clientBalanceOutput = new ClientBalanceOutput();
					if(StringUtility.isStringPopulated(responseEntity.getBody()[i].getBalance()))
					clientBalanceOutput.setBalance(Double.valueOf(responseEntity.getBody()[i].getBalance()));
		
					if(StringUtility.isStringPopulated(responseEntity.getBody()[i].getCurrencyID()))
					clientBalanceOutput.setCurrencyID(Long.valueOf(responseEntity.getBody()[i].getCurrencyID()));					
		
					if(StringUtility.isStringPopulated(responseEntity.getBody()[i].getCurrencyRate()))
						clientBalanceOutput.setCurrencyRate(Double.valueOf(responseEntity.getBody()[i].getCurrencyRate()));					
			
					
					clientBalanceOutput.setInPendingTrans(responseEntity.getBody()[i].getInPendingTrans());
					clientBalanceOutput.setOutPendingTrans(responseEntity.getBody()[i].getOutPendingTrans());
					
					clientBalanceOutput.setCurrencyName(responseEntity.getBody()[i].getCurrencyName());
					clientBalanceOutput.setPendingTransfer(responseEntity.getBody()[i].getPendingTransfer());
					clientBalanceOutput.setTotalBuyValue(responseEntity.getBody()[i].getTotalBuyValue());
					
					
					clientBalanceOutputs.add(clientBalanceOutput);
				}
				output.setClientBalanceOutputs(clientBalanceOutputs);
		}
	
		return output;
	}

	@Override
	public void validateResponse(ResponseEntity<ClientCashBalanceResponse[]> responseEntity) throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());	
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) 
	{
		MyLogger.info("Handling the Exception in the Get Client Balance API:::");		
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
	protected String generateSignature(GetClientCashBalanceRequest getClientCashBalanceRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",getClientCashBalanceRequest.getIdTypeId().toString(),getClientCashBalanceRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(ClientCashBalanceResponse[] teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void generateResponseListSignature(ClientCashBalanceResponse[] clientCashBalanceResponses) throws IntegrationException 
	{
		
		if(this.arrayUtility.isArrayPopulated(clientCashBalanceResponses))
		{
			for(ClientCashBalanceResponse  clientCashBalanceResponse: clientCashBalanceResponses)
			{
				if(clientCashBalanceResponse!=null)
				{
					if(StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",
							clientCashBalanceResponse!=null&&clientCashBalanceResponse.getCurrencyID()!=null?clientCashBalanceResponse.getCurrencyID().toString():null
							,clientCashBalanceResponse!=null&&clientCashBalanceResponse.getBalance()!=null?clientCashBalanceResponse.getBalance().toString():null), clientCashBalanceResponse.getSignature())
							||!StringUtility.isStringPopulated(clientCashBalanceResponse!=null?clientCashBalanceResponse.getSignature():null))
						{
							throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
						}
				}
			}

		}

	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
		
	}

	@Override
	public Class<ClientCashBalanceResponse[]> getResponseClassType() {
		// TODO Auto-generated method stub
		return ClientCashBalanceResponse[].class;
	}

}
