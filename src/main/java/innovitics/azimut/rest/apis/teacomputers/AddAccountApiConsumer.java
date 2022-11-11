package innovitics.azimut.rest.apis.teacomputers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.AddAccountInput;
import innovitics.azimut.rest.entities.teacomputers.AddAccountOutput;
import innovitics.azimut.rest.models.teacomputers.AddAccountRequest;
import innovitics.azimut.rest.models.teacomputers.AddAccountResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class AddAccountApiConsumer extends RestTeaComputerApiConsumer<AddAccountRequest, AddAccountResponse, AddAccountInput, AddAccountOutput> {

	public static final String PATH="/AddCustomer";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(AddAccountInput input) {
		AddAccountRequest request=new AddAccountRequest();
		this.populateCredentials(request);
		
		request.setIdTypeId(input.getIdTypeId());
		request.setIdNumber(input.getIdNumber());
		request.setIdDate(input.getIdDate());
		request.setIdMaturityDate(input.getIdMaturityDate());
		request.setClientAML(input.getClientAML());
		request.setiDIssueCityId(input.getiDIssueCityId());
		request.setiDIssueCountryId(input.getiDIssueCountryId());
		request.setiDIssueDistrictId(input.getiDIssueDistrictId());
		request.setCustomerNameAr(input.getCustomerNameAr());
		request.setCustomerNameEn(input.getCustomerNameEn());
		request.setBirthDate(input.getBirthDate());
		request.setSexId(input.getSexId());
		request.setClientTypeId(input.getClientTypeId());
		request.setEmail(input.getEmail());
		request.setMobile(input.getMobile());
		request.setPhone(input.getPhone());
		request.setAddressAr(input.getAddressAr());
		request.setAddressEn(input.getAddressEn());
		request.setCityId(input.getCityId());
		request.setCountryId(input.getCountryId());
		request.setNationalityId(input.getNationalityId());
		request.setExternalcode(input.getExternalcode());
		request.setOccupation(input.getOccupation());
		request.setPostalNo(input.getPostalNo());
		request.setSignature(this.generateSignature(request));
		
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));
		return httpEntity;
		
	}

	@Override
	public AddAccountOutput generateOutPutFromResponse(ResponseEntity<AddAccountResponse> responseEntity) {
		return new AddAccountOutput();
	}

	@Override
	public void validateResponse(ResponseEntity<AddAccountResponse> responseEntity) throws IntegrationException {
		if (this.validateResponseStatus(responseEntity)) 
		{
			this.generateResponseSignature(responseEntity.getBody());
		}
		if (responseEntity.getBody() == null) 
		{
			throw new IntegrationException(ErrorCode.NO_DATA_FOUND);
		} 

		this.generateResponseSignature(responseEntity.getBody());
		
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	protected String generateSignature(AddAccountRequest request) {
		if(request!=null&&StringUtility.isStringPopulated(request.getIdNumber())&&request.getIdTypeId()!=null&&StringUtility.isStringPopulated(request.getIdTypeId().toString()))
		{
			return this.teaComputersSignatureGenerator.generateSignature(request.getIdTypeId()!=null?request.getIdTypeId().toString():null,request.getIdNumber(),request.getMobile());
		}
		return null;
	}

	@Override
	protected void generateResponseSignature(AddAccountResponse response) throws IntegrationException 
	{
				if(response!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",response.getMessage()), response.getSignature()))
							||!StringUtility.isStringPopulated(response.getSignature()))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
				else 
				{
					throw new IntegrationException(ErrorCode.NO_DATA_FOUND);
				}
		
	}

	@Override
	protected void generateResponseListSignature(AddAccountResponse teaComputerResponse) throws IntegrationException {
		
	}
	@Override
	public String generateURL(String params) {
		return this.generateBaseURL(params)+PATH;
	}

	@Override
	public Class<AddAccountResponse> getResponseClassType() {
		return AddAccountResponse.class;
	}

}
