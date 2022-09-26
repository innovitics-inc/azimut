package innovitics.azimut.rest.apis.teacomputers;

import java.util.Date;

import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.AbstractBaseRestConsumer;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public abstract class RestTeaComputerEportApiConsumer <TeaComputerRequest, TeaComputerResponse, TeaComputerInput, TeaComputerOutput> 
extends RestTeaComputerApiConsumer<TeaComputerRequest, TeaComputerResponse, TeaComputerInput, TeaComputerOutput>{

	protected void populateCredentials(innovitics.azimut.rest.models.teacomputers.TeaComputerRequest request)
	{
		request.setUserName(this.configProperties.getTeaComputersEportfolioUsername());
		
	}
	
	@Override
	public String generateURL(String params)
	{
		return this.configProperties.getTeaComputersEportUrl();
		
	}
	
	@Override
	public IntegrationException handleError(HttpClientErrorException httpClientErrorException)  {
		this.logger.info("httpClientErrorException:::"+httpClientErrorException.toString());
		int errorCode=ErrorCode.FAILED_TO_INTEGRATE.getCode();
		String errorMessage="";
		innovitics.azimut.rest.models.teacomputers.TeaComputerResponse  teaComputerResponse=new innovitics.azimut.rest.models.teacomputers.TeaComputerResponse() ;
		ObjectMapper mapper = new ObjectMapper();
		try 
		{
			this.logger.info("Parsing the exception to the teaComputerResponse:::");
			this.logger.info("httpClientErrorException.getResponseBodyAsString():::"+httpClientErrorException.getResponseBodyAsString());
			teaComputerResponse = mapper.readValue(httpClientErrorException.getResponseBodyAsString(), innovitics.azimut.rest.models.teacomputers.TeaComputerResponse.class);
			errorMessage=teaComputerResponse.getErrorMessage();
			if(StringUtility.isStringPopulated(teaComputerResponse.getErrorCode()))
			{
				errorCode=Integer.valueOf(teaComputerResponse.getErrorCode());
			}
			
			this.logger.info("teaComputerResponse:::"+teaComputerResponse.toString());
		} catch (JsonProcessingException e) {
			this.logger.info("Failed to Parse:::");
			e.printStackTrace();
			return new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		
		IntegrationException integrationException=new IntegrationException(errorCode, new Date(), errorMessage,null, errorMessage,httpClientErrorException.getStackTrace());
		return  integrationException; 
}
	
}
