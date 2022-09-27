package innovitics.azimut.rest.apis.teacomputers;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderInput;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderOutput;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderRequest;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderResponse;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class PlaceOrderApiConsumer extends RestTeaComputerApiConsumer<PlaceOrderRequest, PlaceOrderResponse, PlaceOrderInput, PlaceOrderOutput>{

	@Override
	public HttpEntity<String> generateRequestFromInput(PlaceOrderInput input) {
		PlaceOrderRequest request=new PlaceOrderRequest();
		this.populateCredentials(request);
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setFundID(input.getFundId());
		request.setOrderDate((new Date()).toString());
		request.setOrderTypeId(null);
		
		return null;
	}

	@Override
	public PlaceOrderOutput generateOutPutFromResponse(ResponseEntity<PlaceOrderResponse> responseEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateResponse(ResponseEntity<PlaceOrderResponse> responseEntity) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Palce Orders API:::");
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
	protected String generateSignature(PlaceOrderRequest teaComputerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateResponseSignature(PlaceOrderResponse teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateResponseListSignature(PlaceOrderResponse teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

}
