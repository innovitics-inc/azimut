package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.FundTransactionOutput;
import innovitics.azimut.rest.entities.teacomputers.GetFundTransactionsInput;
import innovitics.azimut.rest.entities.teacomputers.GetFundTransactionsOutput;
import innovitics.azimut.rest.models.teacomputers.FundTransactionResponse;
import innovitics.azimut.rest.models.teacomputers.GetFundTransactionsRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetFundTransactionsApiConsumer extends RestTeaComputerApiConsumer<GetFundTransactionsRequest, FundTransactionResponse[], GetFundTransactionsInput, GetFundTransactionsOutput>{

	@Override
	public HttpEntity<String> generateRequestFromInput(GetFundTransactionsInput input) {
		GetFundTransactionsRequest request=new GetFundTransactionsRequest();
		this.populateCredentials(request);
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setFundID(input.getFundId());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetFundTransactionsOutput generateOutPutFromResponse(ResponseEntity<FundTransactionResponse[]> responseEntity) 
	{
		GetFundTransactionsOutput getFundTransactionsOutput=new GetFundTransactionsOutput();
		List<FundTransactionOutput> fundTransactionOutputs=new ArrayList<FundTransactionOutput>();
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{
			for(FundTransactionResponse fundTransactionResponse:responseEntity.getBody())
			{
				FundTransactionOutput fundTransactionOutput=new FundTransactionOutput();
				fundTransactionOutput.setFundId(fundTransactionResponse.getFundId());
				fundTransactionOutput.setTransactionId(fundTransactionResponse.getTransactionID());
				fundTransactionOutput.setOrderDate(fundTransactionResponse.getOrderDate());
				fundTransactionOutput.setOrderTypeId(fundTransactionResponse.getOrderTypeId());
				fundTransactionOutput.setOrderValue(fundTransactionResponse.getOrderValue());
				fundTransactionOutput.setValueDate(fundTransactionResponse.getValueDate());
				fundTransactionOutput.setQuantity(fundTransactionResponse.getQuantity());
				fundTransactionOutputs.add(fundTransactionOutput);
			}
			
			getFundTransactionsOutput.setFundTransactionOutputs(fundTransactionOutputs);
		}
		return getFundTransactionsOutput;
		
	}

	@Override
	public void validateResponse(ResponseEntity<FundTransactionResponse[]> responseEntity) throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Get Fund Transactions API:::");
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
	protected String generateSignature(GetFundTransactionsRequest getFundTransactionsRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",getFundTransactionsRequest.getIdTypeId().toString(),getFundTransactionsRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(FundTransactionResponse[] fundTransactionResponses)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String generateURL(String params) 
	{
		return super.generateBaseURL(params)+"/"+params;
	}

	
	@Override
	protected void generateResponseListSignature(FundTransactionResponse[] fundTransactionResponses)
			throws IntegrationException {
		if(this.arrayUtility.isArrayPopulated(fundTransactionResponses))
		{
			for(FundTransactionResponse  fundTransactionResponse: fundTransactionResponses)
			{
				if(fundTransactionResponse!=null)
				{
					if(StringUtility.stringsDontMatch
							(this.teaComputersSignatureGenerator.generateSignature(fundTransactionResponse.getTransactionID()!=null?fundTransactionResponse.getTransactionID().toString():null,
									fundTransactionResponse.getFundId()!=null?fundTransactionResponse.getFundId().toString():null), fundTransactionResponse.getSignature())
							||!StringUtility.isStringPopulated(fundTransactionResponse.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}

	}

	@Override
	public Class<FundTransactionResponse[]> getResponseClassType() {
		// TODO Auto-generated method stub
		return FundTransactionResponse[].class;
	}
		
	@Override
	public void transferFromInputToOutput(GetFundTransactionsInput input,GetFundTransactionsOutput output)
	{
		if(input!=null&&output!=null)
		{
			output.setOrderStatus(input.getOrderStatus());
		}
	}

}
