package innovitics.azimut.rest.apis.paytabs;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.rest.entities.paytabs.PaytabsInput;
import innovitics.azimut.rest.entities.paytabs.PaytabsOutput;
import innovitics.azimut.rest.models.paytabs.PaytabsRequest;
import innovitics.azimut.rest.models.paytabs.PaytabsResponse;

public abstract class RestPaytabsApiConsumer <PaytabsRequest, PaytabsResponse, PaytabsInput, PaytabsOutput> 
extends AbstractBaseRestConsumer<PaytabsRequest, PaytabsResponse, PaytabsInput, PaytabsOutput> {

	@Override
	public HttpHeaders generateHeaders(String locale,long contentLength) {
		HttpHeaders headers=this.httpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("lang",locale);
		logger.info("Generated Headers:::"+headers.toString());
		return headers;
	}
	
	@Override
	public String generateBaseURL(String params)
	{
		return this.configProperties.getPaytabsUrl();
	}
}
