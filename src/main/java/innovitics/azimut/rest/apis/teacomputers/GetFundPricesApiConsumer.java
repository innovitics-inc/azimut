package innovitics.azimut.rest.apis.teacomputers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.FundPriceOutput;
import innovitics.azimut.rest.entities.teacomputers.GetFundsPricesInput;
import innovitics.azimut.rest.entities.teacomputers.GetFundsPricesOutput;
import innovitics.azimut.rest.models.teacomputers.ClientBankAccountResponse;
import innovitics.azimut.rest.models.teacomputers.FundPriceResponse;
import innovitics.azimut.rest.models.teacomputers.GetFundsPricesRequest;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class GetFundPricesApiConsumer extends RestTeaComputerApiConsumer<GetFundsPricesRequest, FundPriceResponse[],GetFundsPricesInput , GetFundsPricesOutput> {

	public static final String PATH="/GetFundPrice";
	
	@Override
	public HttpEntity<String> generateRequestFromInput(GetFundsPricesInput input) {
		GetFundsPricesRequest request= new GetFundsPricesRequest();
		this.populateCredentials(request);
		request.setFromDate(input.getFromDate());
		request.setToDate(input.getToDate());
		request.setSignature(this.generateSignature(request));
		HttpEntity<String> httpEntity=this.stringfy(request, this.generateHeaders(input.getLocale(), this.getContentLength(request)));			
		return httpEntity;
	}

	@Override
	public GetFundsPricesOutput generateOutPutFromResponse(ResponseEntity<FundPriceResponse[]> responseEntity) {
		GetFundsPricesOutput getFundsPricesOutput=new GetFundsPricesOutput();
		List<FundPriceOutput> fundPriceOutputs=new ArrayList<FundPriceOutput>();
		for(FundPriceResponse fundPriceResponse:responseEntity.getBody())
		{
			FundPriceOutput fundPriceOutput=new FundPriceOutput();
			if(fundPriceResponse!=null)			
			{
				fundPriceOutput.setFundId(fundPriceResponse.getFundID());
				fundPriceOutput.setTradePrice(fundPriceResponse.getTradePrice());
				fundPriceOutput.setPriceDate(fundPriceResponse.getPriceDate());
				fundPriceOutputs.add(fundPriceOutput);
			}
		}
		getFundsPricesOutput.setFundPriceOutputs(fundPriceOutputs);
		return getFundsPricesOutput;
		
	}

	@Override
	public void validateResponse(ResponseEntity<FundPriceResponse[]> responseEntity) throws IntegrationException {
		if (this.validateResponseStatus(responseEntity)) 
		{
			this.generateResponseListSignature(responseEntity.getBody());	
		}
				
	}

	@Override
	public IntegrationException handleException(Exception exception) {
		return this.exceptionHandler.handleAsIntegrationException(exception, ErrorCode.FAILED_TO_INTEGRATE);
		
		
	}

	@Override
	public HttpMethod chooseHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	public String generateURL(String params) {
		return super.generateBaseURL(params)+PATH;
	}

	@Override
	protected String generateSignature(GetFundsPricesRequest getFundsPricesRequest) {
		return this.teaComputersSignatureGenerator.generateSignature("",getFundsPricesRequest.getFromDate(),getFundsPricesRequest.getToDate());
	}

	@Override
	protected void generateResponseSignature(FundPriceResponse[] teaComputerResponse) throws IntegrationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateResponseListSignature(FundPriceResponse[] fundPriceResponses) throws IntegrationException {
		if(this.arrayUtility.isArrayPopulated(fundPriceResponses))
		{
			for(FundPriceResponse  fundPriceResponse: fundPriceResponses)
			{
				if(fundPriceResponse!=null)
				{
					if((StringUtility.stringsDontMatch(this.teaComputersSignatureGenerator.generateSignature("",fundPriceResponse!=null&&fundPriceResponse.getFundID()!=null?fundPriceResponse.getFundID().toString():null
							),fundPriceResponse!=null?fundPriceResponse.getSignature():null))
							||!StringUtility.isStringPopulated(fundPriceResponse!=null?fundPriceResponse.getSignature():null))
					{
						throw new IntegrationException(ErrorCode.INVALID_SIGNATURE);
					}
					
				}
			}

		}
		
	}

	@Override
	public Class<FundPriceResponse[]> getResponseClassType() {
		// TODO Auto-generated method stub
		return FundPriceResponse[].class;
	}

}
