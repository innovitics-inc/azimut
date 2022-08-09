package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.GetFundByIdInput;
import innovitics.azimut.rest.entities.teacomputers.GetFundOutput;
import innovitics.azimut.rest.models.teacomputers.GetFundRequest;
import innovitics.azimut.rest.models.teacomputers.GetSingleFundResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetFundByIdApiConsumer	extends RestTeaComputerApiConsumer<GetFundRequest, GetSingleFundResponse, GetFundByIdInput, GetFundOutput> {

	@Override
	public HttpEntity<String> generateRequestFromInput(GetFundByIdInput input) {
		return new HttpEntity<>(this.generateHeaders(input.getLocale(),0));
	}

	@Override
	public GetFundOutput generateOutPutFromResponse(ResponseEntity<GetSingleFundResponse> responseEntity) {
		GetFundOutput output=new GetFundOutput();
		
		return null;
		
	}

	@Override
	public void validateResponse(ResponseEntity<GetSingleFundResponse> responseEntity) throws IntegrationException {
		
		if (!this.validateResponseStatus(responseEntity)) {
			throw new IntegrationException(ErrorCode.FAILED_TO_INTEGRATE);
		}
		if (responseEntity.getBody() == null) {
			throw new IntegrationException(ErrorCode.NO_DATA_FOUND);
		} 


	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
		
	}

	@Override
	public String generateURL(String params) {
		return this.configProperties.getAzimutUrl()+"/list/fund"+(StringUtility.isStringPopulated(params)?"/"+params:"");
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	protected String generateSignature(GetFundRequest teaComputerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateResponseSignature(GetSingleFundResponse teaComputerResponse) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void generateResponseListSignature(GetSingleFundResponse teaComputerResponse)
			throws IntegrationException {
		// TODO Auto-generated method stub
		
	}
}