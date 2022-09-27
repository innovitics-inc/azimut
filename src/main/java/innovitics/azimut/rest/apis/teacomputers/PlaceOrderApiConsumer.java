package innovitics.azimut.rest.apis.teacomputers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderInput;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderOutput;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderRequest;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderResponse;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class PlaceOrderApiConsumer extends RestTeaComputerApiConsumer<PlaceOrderRequest, PlaceOrderResponse, PlaceOrderInput, PlaceOrderOutput>{

	
	public static final String PATH="/PlaceOrder";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(PlaceOrderInput input) {
		PlaceOrderRequest request=new PlaceOrderRequest();
		this.populateCredentials(request);
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setFundID(input.getFundId());
		request.setOrderDate(DateUtility.getCurrentDayMonthYear());
		request.setOrderTypeId(input.getOrderTypeId());
		request.setOrderValue(input.getOrderValue());
		request.setExternalOrderID(UUID.randomUUID().toString());
		request.setSignature(this.generateSignature(request));
		
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
		
	}

	@Override
	public PlaceOrderOutput generateOutPutFromResponse(ResponseEntity<PlaceOrderResponse> responseEntity) {
		
		return new PlaceOrderOutput();
	}

	@Override
	public void validateResponse(ResponseEntity<PlaceOrderResponse> responseEntity) throws IntegrationException {
		if (this.validateResponseStatus(responseEntity)) 
		{
			this.generateResponseSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Place Orders API:::");
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
	protected String generateSignature(PlaceOrderRequest placeOrderRequest) {
		return this.teaComputersSignatureGenerator.generateSignature(placeOrderRequest.getIdTypeId().toString(),placeOrderRequest.getIdNumber(),placeOrderRequest.getFundID().toString(),placeOrderRequest.getOrderValue().toString());
	}

	@Override
	protected void generateResponseSignature(PlaceOrderResponse placeOrderResponse) throws IntegrationException 
	{
		
		if(placeOrderResponse!=null)
		{
			if((StringUtility.stringsDontMatch(teaComputersSignatureGenerator.generateSignature(placeOrderResponse.getMessage(),placeOrderResponse.getTransactionID().toString()), placeOrderResponse.getSignature()))
					||!StringUtility.isStringPopulated(placeOrderResponse.getSignature()))
			{
				throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
			}
			
		}
	
	}

	@Override
	protected void generateResponseListSignature(PlaceOrderResponse teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

}
