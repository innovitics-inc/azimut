package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.ClientFundOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientFundsInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientFundsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientFundResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientFundsRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetClientFundsApiConsumer extends RestTeaComputerApiConsumer<GetClientFundsRequest, ClientFundResponse[], GetClientFundsInput, GetClientFundsOutput> {

	public final static String PATH="/GetClientBalance"; 
	@Override
	public HttpEntity<String> generateRequestFromInput(GetClientFundsInput input) 
	{
		GetClientFundsRequest request=new GetClientFundsRequest();
		this.populateCredentials(request);
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}



	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Get Client Funds API:::");
		if(exception instanceof IntegrationException)
		{
			IntegrationException integrationException=(IntegrationException)exception;			
			return integrationException;
		}
		else
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
	}



	@Override
	public GetClientFundsOutput generateOutPutFromResponse(ResponseEntity<ClientFundResponse[]> responseEntity) 
	{
		GetClientFundsOutput getClientFundsOutput=new GetClientFundsOutput();
		
		List<ClientFundOutput> clientFundsOutputs=new ArrayList<ClientFundOutput>(); 
		
	
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{		
				for(int i=0;i<responseEntity.getBody().length;i++)
				{
					ClientFundOutput clientFundOutput = new ClientFundOutput();
					clientFundOutput.setAssetClass(responseEntity.getBody()[i].getAssetClass());
					clientFundOutput.setAvailableToBuy(responseEntity.getBody()[i].getAvailableToBuy());
					clientFundOutput.setAvailableToSell(responseEntity.getBody()[i].getAvailableToSell());
					clientFundOutput.setAvgcost(responseEntity.getBody()[i].getAvgcost());
					clientFundOutput.setCertificateName(responseEntity.getBody()[i].getCertificateName());
					clientFundOutput.setClientName(responseEntity.getBody()[i].getClientName());
					clientFundOutput.setClientStatus(responseEntity.getBody()[i].getClientStatus());
					clientFundOutput.setCurrencyName(responseEntity.getBody()[i].getCurrencyName());
					clientFundOutput.setCurrencyRate(responseEntity.getBody()[i].getCurrencyRate());
					clientFundOutput.setCurrencyId(responseEntity.getBody()[i].getCurrencyID());
					clientFundOutput.setFundId(responseEntity.getBody()[i].getFundID());
					clientFundOutput.setMobile(responseEntity.getBody()[i].getMobile());
					clientFundOutput.setQuantity(responseEntity.getBody()[i].getQuantity());
					clientFundOutput.setStatusName(responseEntity.getBody()[i].getStatusName());
					clientFundOutput.setTradePrice(responseEntity.getBody()[i].getTradePrice());
					clientFundsOutputs.add(clientFundOutput);
				}
				getClientFundsOutput.setClientFundOutputs(clientFundsOutputs);
		}
		return getClientFundsOutput;
	}



	@Override
	public void validateResponse(ResponseEntity<ClientFundResponse[]> responseEntity) throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}



	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}


	@Override
	public String generateURL(String params) 
	{
		return super.generateURL(params)+PATH;
	}
	
	@Override
	protected String generateSignature(GetClientFundsRequest getClientFundsRequest) {
		return this.teaComputersSignatureGenerator.generateSignature(getClientFundsRequest.getIdTypeId().toString(),getClientFundsRequest.getIdNumber());
	}



	@Override
	protected void generateResponseSignature(ClientFundResponse[] clientFundResponses) throws IntegrationException 
	{
				
	}



	@Override
	protected void generateResponseListSignature(ClientFundResponse[] clientFundResponses) throws IntegrationException {
		if(this.arrayUtility.isArrayPopulated(clientFundResponses))
		{
			for(ClientFundResponse  clientFundResponse: clientFundResponses)
			{
				if(clientFundResponse!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",clientFundResponse.getMobile(),
							clientFundResponse.getTradePrice()),clientFundResponse.getSignature())
							||!StringUtility.isStringPopulated(clientFundResponse.getSignature())))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}

		
	}

}
