package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderInput;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderOutput;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderRequest;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderResponse;
import innovitics.azimut.utilities.crosslayerenums.OrderType;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class CancelOrderApiConsumer extends PlaceOrderApiConsumer {

	public static final String PATH="/CancelOrder";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(PlaceOrderInput input) {
		PlaceOrderRequest request=new PlaceOrderRequest();
		this.populateCredentials(request);
		request.setTransactionID(input.getTransactionId());
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setSignature(this.generateSignature(request));
		
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}
	
	@Override
	public PlaceOrderOutput generateOutPutFromResponse(ResponseEntity<PlaceOrderResponse> responseEntity) 
	{
		PlaceOrderOutput placeOrderOutput=new PlaceOrderOutput();
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{
			placeOrderOutput.setMessage(responseEntity.getBody().getMessage());
		}
		return placeOrderOutput;
	}
	
	@Override
	protected String generateSignature(PlaceOrderRequest placeOrderRequest) {
		return this.teaComputersSignatureGenerator.generateSignature(placeOrderRequest.getIdTypeId().toString(),placeOrderRequest.getIdNumber());
	}
	@Override
	protected void generateResponseSignature(PlaceOrderResponse placeOrderResponse) throws IntegrationException 
	{
		
		if(placeOrderResponse!=null)
		{
			if((StringUtility.stringsDontMatch(teaComputersSignatureGenerator.generateSignature(placeOrderResponse.getMessage()), placeOrderResponse.getSignature()))
					||!StringUtility.isStringPopulated(placeOrderResponse.getSignature()))
			{
				throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
			}
			
		}
	
	}
	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}
}
