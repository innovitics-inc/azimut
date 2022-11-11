package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetReportInput;
import innovitics.azimut.rest.entities.teacomputers.GetReportOutput;
import innovitics.azimut.rest.models.teacomputers.GetReportRequest;
import innovitics.azimut.rest.models.teacomputers.GetReportResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetReportApiConsumer extends RestTeaComputerEportApiConsumer<GetReportRequest, GetReportResponse, GetReportInput, GetReportOutput>{

	
	@Override
	public HttpEntity<String> generateRequestFromInput(GetReportInput input) {
		
		GetReportRequest request=new GetReportRequest();
		
		if(StringUtility.stringsMatch(input.getReportType(), StringUtility.VALUATION_REPORT))
			
		{
			this.populateCredentials(request);			
			request.setShowAbsReturn(true);
			request.setTitle("Title");			
		}
		else if (StringUtility.stringsMatch(input.getReportType(), StringUtility.REQUEST_STATEMENT))
		{	
			request.setUserName(this.configProperties.getTeaComputersUsername());
			request.setToDate(input.getToDate());
			request.setFromDate(input.getFromDate());
			request.setCurrencyId(input.getCurrencyId());
		}
		request.setIdType(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());		
		request.setLang(input.getLocale());
		request.setReportType(input.getReportType());
		request.setSignature(this.generateSignature(request));
		request.setReportType(null);
		
	
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetReportOutput generateOutPutFromResponse(ResponseEntity<GetReportResponse> responseEntity) {
		GetReportOutput getValuationReportOutput=new GetReportOutput();
		getValuationReportOutput.setFilePath(responseEntity.getBody().getFilePath());
		return getValuationReportOutput;
	}

	@Override
	public void validateResponse(ResponseEntity<GetReportResponse> responseEntity)
			throws IntegrationException {
		if(this.validateResponseStatus(responseEntity))
		{
			this.generateResponseSignature(responseEntity.getBody());
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		this.logger.info("Handling the Exception in the Get Valuation Report API:::");
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
	protected String generateSignature(GetReportRequest getReportRequest) {
		
		if(getReportRequest!=null&&StringUtility.stringsMatch(getReportRequest.getReportType(), StringUtility.VALUATION_REPORT))
		{
			return this.teaComputersSignatureGenerator.generateSignature(true, this.configProperties.getTeaComputersEportfolioKey(),getReportRequest.getIdType().toString(),getReportRequest.getIdNumber());
		}
		else if(getReportRequest!=null&&StringUtility.stringsMatch(getReportRequest.getReportType(), StringUtility.REQUEST_STATEMENT))
		{
			return this.teaComputersSignatureGenerator.generateSignature(getReportRequest.getIdType().toString(),getReportRequest.getIdNumber());
		}
		return null;
	}

	@Override
	protected void generateResponseSignature(GetReportResponse getReportResponse) throws IntegrationException 
	{
			if(getReportResponse!=null)
			{
				
				if(StringUtility.isStringPopulated(getReportResponse.getUrl())&&getReportResponse.getUrl().contains(StringUtility.VALUATION_REPORT))
				{
					if(StringUtility.stringsDontMatch(getReportResponse.getSignature(), this.teaComputersSignatureGenerator.generateSignature(true, this.configProperties.getTeaComputersEportfolioKey(),"SendValReport",getReportResponse.getFilePath()))||
						
							!StringUtility.isStringPopulated(getReportResponse.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
				}
				else if(StringUtility.isStringPopulated(getReportResponse.getUrl())&&getReportResponse.getUrl().contains(StringUtility.REQUEST_STATEMENT))
				{
					if(StringUtility.stringsDontMatch(getReportResponse.getSignature(), this.teaComputersSignatureGenerator.generateSignature("AccountStatement",getReportResponse.getFilePath()))||
							
							!StringUtility.isStringPopulated(getReportResponse.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
				}
					
				
			}	
	}
		
	

	@Override
	protected void generateResponseListSignature(GetReportResponse teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String generateURL(String params) 
	{
		return super.generateBaseURL(params)+"/"+params;
	}
	
	protected void populateResponse(String url,ResponseEntity<GetReportResponse> responseEntity)
	{
		if(responseEntity!=null&&StringUtility.isStringPopulated(url))
		{
			GetReportResponse response=responseEntity.getBody();
			response.setUrl(url);
			
		}
	}

	@Override
	public Class<GetReportResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return GetReportResponse.class;
	};
}
