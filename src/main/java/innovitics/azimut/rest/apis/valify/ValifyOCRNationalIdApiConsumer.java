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
import innovitics.azimut.rest.models.valify.ValifyNationalIdResult;
import innovitics.azimut.rest.models.valify.ValifyOCRNationalIdRequest;
import innovitics.azimut.rest.models.valify.ValifyOCRNationalIdResponse;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class ValifyOCRNationalIdApiConsumer extends RestValifyApiConsumer<ValifyOCRNationalIdRequest,ValifyOCRNationalIdResponse,ValifyOCRInput,ValifyOCROutput> {

	final static String PATH="/v1/ocr/";
	@Override
	public HttpEntity<String> generateRequestFromInput(ValifyOCRInput input) 
	{
		ValifyOCRNationalIdRequest request =new ValifyOCRNationalIdRequest();
		ValifyImageData data=new ValifyImageData();
		request.setDocument_type(input.getDoucmentType());
		data.setFront_img(input.getFrontImage());
		data.setBack_img(input.getBackImage());
		data.setBundle_key(this.configProperties.getValifyBundleKey());
		this.populateGeneralRequestDetails(null, data, input);
		request.setData(data);
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(null,0,input.getToken()));
		
		return httpEntity;
	}

	@Override
	public ValifyOCROutput generateOutPutFromResponse(ResponseEntity<ValifyOCRNationalIdResponse> responseEntity) {
		
		ValifyOCROutput output=new ValifyOCROutput();
		ValifyOCRNationalIdResponse response=responseEntity.getBody();
	
		ValifyNationalIdResult result= response.getResult();
		this.populateGeneralResponseDetails(response, output);
		output.setFirstName(result.getFirst_name());
		output.setFullName(result.getFull_name());
		output.setStreet(result.getStreet());
		output.setArea(result.getArea());
		output.setFrontNid(result.getFront_nid());
		output.setSerialNumber(result.getSerial_number());
		output.setBackNid(output.getBackNid());
		output.setReleaseDate(result.getRelease_date());
		output.setGender(result.getGender());
		output.setMaritalStatus(result.getMarital_status());
		output.setProfession(result.getProfession());
		output.setReligion(result.getReligion());
		output.setHusbandName(result.getHusband_name());
		output.setDateOfBirth(result.getDate_of_birth());
		output.setExpiryDate(result.getExpiry_date());
		output.setEgyptian(true);
		return output;
		
	}

	@Override
	public void validateResponse(ResponseEntity<ValifyOCRNationalIdResponse> responseEntity)
			throws IntegrationException {
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
	public HttpEntity<MultiValueMap<String, String>> generateMappedRequestFromInput(ValifyOCRInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<ValifyOCRNationalIdResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return ValifyOCRNationalIdResponse.class;
	}

	 
}
