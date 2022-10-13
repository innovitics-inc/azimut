package innovitics.azimut.rest.apis.teacomputers;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetTransactionsInput;
import innovitics.azimut.rest.entities.teacomputers.GetTransactionsOutput;
import innovitics.azimut.rest.entities.teacomputers.TransactionOutput;
import innovitics.azimut.rest.models.teacomputers.ClientBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.GetTransactionsRequest;
import innovitics.azimut.rest.models.teacomputers.TransactionResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetClientTransactionListApiConsumer extends RestTeaComputerApiConsumer<GetTransactionsRequest, TransactionResponse[], GetTransactionsInput, GetTransactionsOutput> {

	public static final String PATH="/GetInformsTrans";
	@Override
	public HttpEntity<String> generateRequestFromInput(GetTransactionsInput input) {
		GetTransactionsRequest request=new GetTransactionsRequest();	
		this.populateCredentials(request);
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setFromDate(input.getFromDate());
		request.setToDate(input.getToDate());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetTransactionsOutput generateOutPutFromResponse(ResponseEntity<TransactionResponse[]> responseEntity) {
		GetTransactionsOutput output=new GetTransactionsOutput();
		List<TransactionOutput> transactionOutputs=new ArrayList<TransactionOutput>();
		
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{		
				for(int i=0;i<responseEntity.getBody().length;i++)
				{
					TransactionOutput transactionOutput = new TransactionOutput();
					transactionOutput.setTransValue(responseEntity.getBody()[i].getTransValue());
					transactionOutput.setTransTypeName(responseEntity.getBody()[i].getTransTypeName());
					transactionOutput.setTransStatusName(responseEntity.getBody()[i].getStatusName());
					transactionOutput.setTransDate(responseEntity.getBody()[i].getTransDate());
					transactionOutput.setOrderTypeName(responseEntity.getBody()[i].getOrderTypeName());
					transactionOutput.setCurrencyId(responseEntity.getBody()[i].getCurrencyId());
					transactionOutput.setCurrencyName(responseEntity.getBody()[i].getCurrencyName());
					transactionOutputs.add(transactionOutput);
				}
				output.setTransactionOutputs(transactionOutputs);
		}
	
		return output;
	}

	@Override
	public void validateResponse(ResponseEntity<TransactionResponse[]> responseEntity) throws IntegrationException {
		if (this.validateResponseStatus(responseEntity)) 
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Get Client Transactions API:::");
		if(exception instanceof IntegrationException)
		{
			IntegrationException integrationException=(IntegrationException)exception;			
			return integrationException;
		}
		else
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
	protected String generateSignature(GetTransactionsRequest getTransactionsRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",getTransactionsRequest.getIdTypeId().toString(),getTransactionsRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(TransactionResponse[] transactionResponses) throws IntegrationException {
		if(this.arrayUtility.isArrayPopulated(transactionResponses))
		{
			for(TransactionResponse  transactionResponse: transactionResponses)
			{
				if(transactionResponse!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",transactionResponse.getTransValue().toString(),
							transactionResponse.getNetValue()), transactionResponse.getSignature()))
							||!StringUtility.isStringPopulated(transactionResponse.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}

	}
	@Override
	protected void generateResponseListSignature(TransactionResponse[] teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	
}
