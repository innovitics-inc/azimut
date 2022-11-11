package innovitics.azimut.rest.apis.valify;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.valify.ValifyInput;
import innovitics.azimut.rest.entities.valify.ValifyOCRInput;
import innovitics.azimut.rest.entities.valify.ValifyOCROutput;
import innovitics.azimut.rest.models.valify.ValifyImageData;
import innovitics.azimut.rest.models.valify.ValifyOCRPassportRequest;
import innovitics.azimut.rest.models.valify.ValifyOCRPassportResponse;
import innovitics.azimut.rest.models.valify.ValifyPassportResult;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class ValifyOCRPassportApiConsumer extends RestValifyApiConsumer<ValifyOCRPassportRequest,ValifyOCRPassportResponse,ValifyOCRInput,ValifyOCROutput>  {

	final static String PATH="/v1/ocr/";
	@Override
	public HttpEntity<String> generateRequestFromInput(ValifyOCRInput input) {
		
		ValifyOCRPassportRequest request =new ValifyOCRPassportRequest();
		ValifyImageData data=new ValifyImageData();
		request.setDocument_type(input.getDoucmentType());
		data.setImg(input.getImage());
		data.setBundle_key(this.configProperties.getValifyBundleKey());
		this.populateGeneralRequestDetails(null, data, input);
		request.setData(data);
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(null,0,input.getToken()));
		
		return httpEntity;
	}

	@Override
	public ValifyOCROutput generateOutPutFromResponse(ResponseEntity<ValifyOCRPassportResponse> responseEntity) {
		ValifyOCROutput output=new ValifyOCROutput();
		ValifyOCRPassportResponse response=responseEntity.getBody();
		
		ValifyPassportResult result= response.getResult();
		this.populateGeneralResponseDetails(response, output);
		
		output.setName(result.getName());
		output.setSurname(result.getSurname());
		output.setPassportNumber(result.getPassport_number());
		output.setExpirationDate(result.getExpiration_date());
		output.setDateOfBirth(result.getDate_of_birth());
		output.setSex(result.getSex());
		output.setNationality(result.getNationality());
		output.setValidity(result.getValidity());
		output.setEgyptian(false);
		return output;
		
	}

	@Override
	public void validateResponse(ResponseEntity<ValifyOCRPassportResponse> responseEntity) throws IntegrationException {
		if(!this.validateResponseStatus(responseEntity))
		{
			if(responseEntity!=null&&responseEntity.getBody()!=null)
			{
				throw this.populateIntegrationException(responseEntity.getBody());
			}
		}
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.validateExceptionType(exception);
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}
	
	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
		
	}

	@Override
	public HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(ValifyOCRInput valifyInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<ValifyOCRPassportResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return ValifyOCRPassportResponse.class;
	}

	
}
