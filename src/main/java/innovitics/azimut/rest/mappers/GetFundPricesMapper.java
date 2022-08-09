package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetFundPricesApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.ClientBankAccountOutput;
import innovitics.azimut.rest.entities.teacomputers.FundPriceOutput;
import innovitics.azimut.rest.entities.teacomputers.GetFundsPricesInput;
import innovitics.azimut.rest.entities.teacomputers.GetFundsPricesOutput;
import innovitics.azimut.rest.entities.teacomputers.TransactionOutput;
import innovitics.azimut.rest.models.teacomputers.FundPriceResponse;
import innovitics.azimut.rest.models.teacomputers.GetFundsPricesResponse;
import innovitics.azimut.utilities.datautilities.ListUtility;
@Component
public class GetFundPricesMapper extends RestMapper<GetFundsPricesInput, GetFundsPricesOutput, GetFundsPricesResponse, BusinessFundPrice>{
	@Autowired GetFundPricesApiConsumer getFundPricesApiConsumer; 
	@Autowired ListUtility<FundPriceOutput> listUtility;
	@Override
	BusinessFundPrice consumeRestService(BusinessFundPrice businessFundPrice, String params) throws IntegrationException {
		return null;
	}

	@Override
	List<BusinessFundPrice> consumeListRestService(BusinessFundPrice businessFundPrice, String params) throws HttpClientErrorException, Exception {
				return  this.createListBusinessEntityFromOutput(this.getFundPricesApiConsumer.invoke(this.createInput(businessFundPrice),FundPriceResponse[].class, params));
	}

	@Override
	GetFundsPricesInput createInput(BusinessFundPrice businessFundPrice) {
		GetFundsPricesInput  input=new GetFundsPricesInput();
		input.setFromDate(businessFundPrice.getSearchFromDate());
		input.setToDate(businessFundPrice.getSearchToDate());
		return input;
	}

	@Override
	BusinessFundPrice createBusinessEntityFromOutput(GetFundsPricesOutput BaseOutput) {
		return null;
	}

	@Override
	protected List<BusinessFundPrice> createListBusinessEntityFromOutput(GetFundsPricesOutput getFundsPricesOutput) {
		List<BusinessFundPrice> businessFundPrices=new ArrayList<BusinessFundPrice>();
		if( getFundsPricesOutput!=null&&this.listUtility.isListPopulated(getFundsPricesOutput.getFundPriceOutputs()))	
		{
			for(FundPriceOutput fundPriceOutput: getFundsPricesOutput.getFundPriceOutputs())
			{
				businessFundPrices.add(this.getConversion(fundPriceOutput));
			}
			
		}
		return businessFundPrices;
	}
	
	BusinessFundPrice getConversion(FundPriceOutput fundPriceOutput)
	{
		BusinessFundPrice businessFundPrice=new BusinessFundPrice();	
		if(fundPriceOutput!=null)
		{
			businessFundPrice.setFundId(fundPriceOutput.getFundId());
			businessFundPrice.setTradePrice(fundPriceOutput.getTradePrice());
			businessFundPrice.setNav(fundPriceOutput.getTradePrice());
			businessFundPrice.setPriceDate(fundPriceOutput.getPriceDate());
		}
		return businessFundPrice;		
	}

	
	

}
