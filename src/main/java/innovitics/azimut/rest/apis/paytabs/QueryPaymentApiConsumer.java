package innovitics.azimut.rest.apis.paytabs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.rest.entities.paytabs.QueryPaymentInput;
import innovitics.azimut.rest.entities.paytabs.QueryPaymentOutput;
import innovitics.azimut.rest.models.paytabs.PaytabsQueryRequest;
import innovitics.azimut.rest.models.paytabs.PaytabsQueryResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;

@Service
public class QueryPaymentApiConsumer extends RestPaytabsApiConsumer<PaytabsQueryRequest, PaytabsQueryResponse, QueryPaymentInput, QueryPaymentOutput>{

	private static final String PATH="/payment/query";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(QueryPaymentInput input) {
		PaytabsQueryRequest request=new PaytabsQueryRequest();
		this.populateCredentials(request, input);
		request.setTransactionReference(input.getTransactionReference());
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getPayPageLang(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public QueryPaymentOutput generateOutPutFromResponse(ResponseEntity<PaytabsQueryResponse> responseEntity) {
		QueryPaymentOutput queryPaymentOutput=new QueryPaymentOutput();
		if(responseEntity.hasBody())
		{
			if(StringUtility.isStringPopulated(responseEntity.getBody().getCartAmount()))
			{
				queryPaymentOutput.setCartAmount(Double.valueOf(responseEntity.getBody().getCartAmount()));
			}
			if(StringUtility.isStringPopulated(responseEntity.getBody().getPaymentResult().getResponseCode()))
			{
				queryPaymentOutput.setResponseStatus(responseEntity.getBody().getPaymentResult().getResponseCode());
			}
		}
		
		return queryPaymentOutput;
	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;	
	}

	@Override
	public Class<PaytabsQueryResponse> getResponseClassType() {
		return PaytabsQueryResponse.class;
	}

}
