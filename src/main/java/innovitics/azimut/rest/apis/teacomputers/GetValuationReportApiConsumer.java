package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetValuationReportInput;
import innovitics.azimut.rest.entities.teacomputers.GetValuationReportOutput;
import innovitics.azimut.rest.models.teacomputers.GetValuationReportRequest;
import innovitics.azimut.rest.models.teacomputers.GetValuationReportResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetValuationReportApiConsumer extends RestTeaComputerEportApiConsumer<GetValuationReportRequest, GetValuationReportResponse, GetValuationReportInput, GetValuationReportOutput>{

	
	public final static String PATH="/Report/SendValReport"; 
	
	@Override
	public HttpEntity<String> generateRequestFromInput(GetValuationReportInput input) {
		
		GetValuationReportRequest request=new GetValuationReportRequest();
		this.populateCredentials(request);
		request.setIdType(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setSignature(this.generateSignature(request));
		request.setLang(input.getLocale());
		request.setShowAbsReturn(true);
		request.setTitle("Title");
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
	}

	@Override
	public GetValuationReportOutput generateOutPutFromResponse(ResponseEntity<GetValuationReportResponse> responseEntity) {
		GetValuationReportOutput getValuationReportOutput=new GetValuationReportOutput();
		getValuationReportOutput.setFilePath(responseEntity.getBody().getFilePath());
		return getValuationReportOutput;
	}

	@Override
	public void validateResponse(ResponseEntity<GetValuationReportResponse> responseEntity)
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
	protected String generateSignature(GetValuationReportRequest getValuationReportRequest) {
		return this.teaComputersSignatureGenerator.generateSignature(true, this.configProperties.getTeaComputersEportfolioKey(),getValuationReportRequest.getIdType().toString(),getValuationReportRequest.getIdNumber());
	}

	@Override
	protected void generateResponseSignature(GetValuationReportResponse getValuationReportResponse) throws IntegrationException {
		
		
			if(getValuationReportResponse!=null)
			{
				if(StringUtility.stringsDontMatch(getValuationReportResponse.getSignature(), this.teaComputersSignatureGenerator.generateSignature(true, this.configProperties.getTeaComputersEportfolioKey(),"SendValReport",getValuationReportResponse.getFilePath()))||
						
						!StringUtility.isStringPopulated(getValuationReportResponse.getSignature()))
				{
					throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
				}
			}
		
		
	}
		
	

	@Override
	protected void generateResponseListSignature(GetValuationReportResponse teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String generateURL(String params) 
	{
		return super.generateURL(params)+PATH;
	}

}
