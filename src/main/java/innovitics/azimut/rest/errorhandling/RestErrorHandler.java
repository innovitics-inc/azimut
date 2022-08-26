package innovitics.azimut.rest.errorhandling;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EncodingUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.BaseRestConsumer;
import innovitics.azimut.rest.models.BaseRestResponse;
import innovitics.azimut.rest.models.valify.ValifyResponse;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Component
public class RestErrorHandler  implements ResponseErrorHandler {
	public final static Logger logger = LogManager.getLogger(ResponseErrorHandler.class.getName());

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		if(!response.getStatusCode().is2xxSuccessful())
		{
			return true;
		}
		return false;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		Class<? extends ClientHttpResponse> bresponse= response.getClass();
		try {
			String message="";
			
			Field field= bresponse.getField("message");
			message=(String) field.get(message);
			this.logger.info("Message Value:::"+message);
		
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	public void handleError(HttpClientErrorException httpClientErrorException) throws IntegrationException {
			this.logger.info("httpClientErrorException:::"+httpClientErrorException.toString());
			ValifyResponse baseRestResponse=new ValifyResponse();
			ObjectMapper mapper = new ObjectMapper();
			try {
				baseRestResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), ValifyResponse.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
			}
			this.logger.info("baseRestResponse:::"+baseRestResponse.toString());
	}
}
