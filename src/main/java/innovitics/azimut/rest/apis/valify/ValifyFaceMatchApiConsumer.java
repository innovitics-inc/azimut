package innovitics.azimut.rest.apis.valify;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.valify.ValifyAccessTokenOutput;
import innovitics.azimut.rest.entities.valify.ValifyFacialImageInput;
import innovitics.azimut.rest.entities.valify.ValifyFacialImageOutput;
import innovitics.azimut.rest.entities.valify.ValifyInput;
import innovitics.azimut.rest.models.valify.ValifyAccessTokenRequest;
import innovitics.azimut.rest.models.valify.ValifyAccessTokenResponse;
import innovitics.azimut.rest.models.valify.ValifyFacialImageRequest;
import innovitics.azimut.rest.models.valify.ValifyFacialImageResponse;
import innovitics.azimut.rest.models.valify.ValifyFacialImageResult;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class ValifyFaceMatchApiConsumer extends RestValifyApiConsumer<ValifyFacialImageRequest, ValifyFacialImageResponse,ValifyFacialImageInput ,ValifyFacialImageOutput> {

	final static String PATH="/v1/face/match/";
	@Override
	public HttpEntity<String> generateRequestFromInput(ValifyFacialImageInput input) {
		ValifyFacialImageRequest request =new ValifyFacialImageRequest();
		this.populateGeneralRequestDetails(request, null, input);
		request.setFirst_img(input.getFirstImage());
		request.setSecond_img(input.getSecondImage());
		request.setBundle_key(this.configProperties.getValifyBundleKey());
		HttpEntity<String> httpEntity=this.stringfy(request,this.generateHeaders(null, 0,input.getToken()));		
		return httpEntity;
	}

	@Override
	public ValifyFacialImageOutput generateOutPutFromResponse(ResponseEntity<ValifyFacialImageResponse> responseEntity) {
		
		ValifyFacialImageOutput output=new ValifyFacialImageOutput();
		ValifyFacialImageResponse response=responseEntity.getBody(); 
		ValifyFacialImageResult valifyFacialImageResult=response.getResult();
		this.populateGeneralResponseDetails(responseEntity.getBody(),output); 
		output.setConfidence(valifyFacialImageResult.getConfidence());
		output.setSimilar(valifyFacialImageResult.isIs_similar());
		  
		return output;
	}

	@Override
	public void validateResponse(ResponseEntity<ValifyFacialImageResponse> responseEntity) throws IntegrationException {
		boolean isResponseSane=this.validateResponseStatus(responseEntity);
		if(!isResponseSane)
		{
			if(responseEntity!=null&&responseEntity.getBody()!=null)
			{
				throw this.populateIntegrationException(responseEntity.getBody());
			}
		}
		else if(isResponseSane)
		{
			if(responseEntity.getBody().getResult()!=null&&!responseEntity.getBody().getResult().isIs_similar())
			{
				throw new IntegrationException(ErrorCode.IMAGES_NOT_SIMIILAR);
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
	public Class<ValifyFacialImageResponse> getResponseClassType() {
		// TODO Auto-generated method stub
		return ValifyFacialImageResponse.class;
	}
}
