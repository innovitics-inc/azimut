package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.EportfolioOutput;
import innovitics.azimut.rest.entities.teacomputers.GetEportfolioInput;
import innovitics.azimut.rest.entities.teacomputers.GetEportfolioOutput;
import innovitics.azimut.rest.models.teacomputers.GetEportfolioRequest;
import innovitics.azimut.rest.models.teacomputers.GetEportfolioResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetEportfolioApiConsumer extends RestTeaComputerEportApiConsumer<GetEportfolioRequest, GetEportfolioResponse, GetEportfolioInput, GetEportfolioOutput>{

	public final static String PATH="/portfolio/Summary"; 
	
	
	@Override
	public HttpEntity<String> generateRequestFromInput(GetEportfolioInput input) {
		
		GetEportfolioRequest request=new GetEportfolioRequest();
		this.populateCredentials(request);
		request.setIdType(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setSignature(this.generateSignature(request));
		request.setLang(input.getLocale());
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetEportfolioOutput generateOutPutFromResponse(ResponseEntity<GetEportfolioResponse> responseEntity) {
		GetEportfolioOutput getEportfolioOutput=new GetEportfolioOutput();
		
		List<EportfolioOutput> eportfolioOutputs=new ArrayList<EportfolioOutput>(); 
		
	
		if(this.validateResponseStatus(responseEntity)&&responseEntity.hasBody())
		{		
				for(int i=0;i<responseEntity.getBody().getData().length;i++)
				{
					EportfolioOutput eportfolioOutput = new EportfolioOutput();
					eportfolioOutput.setClientId(responseEntity.getBody().getData()[i].getClientID());
					eportfolioOutput.setId(responseEntity.getBody().getData()[i].getId());
					eportfolioOutput.setName(responseEntity.getBody().getData()[i].getName());
					eportfolioOutput.setPortfolioId(responseEntity.getBody().getData()[i].getPortfolioID());
					eportfolioOutput.setValue(responseEntity.getBody().getData()[i].getValue());
					eportfolioOutput.setWeight(responseEntity.getBody().getData()[i].getWeight());
					eportfolioOutputs.add(eportfolioOutput);
				}
				getEportfolioOutput.setEportfolioOutputs(eportfolioOutputs);
		}
		return getEportfolioOutput;
	}

	@Override
	public void validateResponse(ResponseEntity<GetEportfolioResponse> responseEntity) throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseListSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Get Eportfolio API:::");
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
	protected String generateSignature(GetEportfolioRequest getEportfolioRequest) {
		return this.teaComputersSignatureGenerator.generateSignature(true, this.configProperties.getTeaComputersEportfolioKey(),getEportfolioRequest.getIdTypeId().toString(),getEportfolioRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(GetEportfolioResponse getEportfolioResponse) throws IntegrationException {
		if(getEportfolioResponse!=null&&this.arrayUtility.isArrayPopulated(getEportfolioResponse.getData()))
		{
			if(getEportfolioResponse.getData()[0]!=null&&StringUtility.isStringPopulated(getEportfolioResponse.getData()[0].getId()))
			{
				if(StringUtility.stringsDontMatch(getEportfolioResponse.getSignature(), this.teaComputersSignatureGenerator.generateSignature(true, this.configProperties.getTeaComputersEportfolioKey(),"Summary",getEportfolioResponse.getData()[0].getId()))||
						
						!StringUtility.isStringPopulated(getEportfolioResponse.getSignature()))
				{
					throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
				}
			}
		}
		
	}

	@Override
	protected void generateResponseListSignature(GetEportfolioResponse eportfolioResponse) throws IntegrationException {
		
	}
	
	@Override
	public String generateURL(String params) 
	{
		return super.generateURL(params)+PATH;
	}

}
