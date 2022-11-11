package innovitics.azimut.rest.mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.funds.BusinessClientFund;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetClientFundsApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.ClientFundOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientFundsInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientFundsOutput;
import innovitics.azimut.rest.models.teacomputers.ClientFundResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientFundsRequest;
import innovitics.azimut.rest.models.teacomputers.GetClientFundsResponse;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Component
public class GetClientFundsMapper extends RestMapper<GetClientFundsInput, GetClientFundsOutput,GetClientFundsRequest ,ClientFundResponse[], BusinessClientFund> 
{

	@Autowired GetClientFundsApiConsumer getClientFundsApiConsumer;

	@Override
	BusinessClientFund consumeRestService(BusinessClientFund businessClientFund, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return this.createBusinessEntityFromOutput(this.getClientFundsApiConsumer.invoke(this.createInput(businessClientFund),ClientFundResponse[].class, params));
	}

	@Override
	List<BusinessClientFund> consumeListRestService(BusinessClientFund businessClientFund, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return this.createListBusinessEntityFromOutput(this.getClientFundsApiConsumer.invoke(this.createInput(businessClientFund),ClientFundResponse[].class, params));
	}

	@Override
	GetClientFundsInput createInput(BusinessClientFund businessClientFund) 
	{
		GetClientFundsInput clientFundsInput=new GetClientFundsInput();
		
		clientFundsInput.setIdTypeId(businessClientFund.getAzIdType());
		clientFundsInput.setIdNumber(businessClientFund.getAzId());
		clientFundsInput.setFundId(businessClientFund.getFundId());
		
		return clientFundsInput;
	}

	@Override
	BusinessClientFund createBusinessEntityFromOutput(GetClientFundsOutput getClientFundsOutput) {
		if(getClientFundsOutput!=null&&getClientFundsOutput.getClientFundOutputs()!=null&&getClientFundsOutput.getClientFundOutputs().get(0)!=null)
		{
			return this.changeUnit(getClientFundsOutput.getClientFundOutputs().get(0));
		}
		return null;
	}

	@Override
	protected List<BusinessClientFund> createListBusinessEntityFromOutput(GetClientFundsOutput getClientFundsOutput) 
	{
	
		List<BusinessClientFund>  businessClientFunds=new ArrayList<BusinessClientFund>();
		
		if(getClientFundsOutput!=null&&getClientFundsOutput.getClientFundOutputs()!=null)
		{
			for(ClientFundOutput clientFundOutput:getClientFundsOutput.getClientFundOutputs()) 
				{
					BusinessClientFund businessClientFund=this.changeUnit(clientFundOutput);
					if(businessClientFund!=null)
					{
						businessClientFunds.add(businessClientFund);
					}
				
				}
			return businessClientFunds;
		}
				return null;
	}
	
	
	private BusinessClientFund changeUnit(ClientFundOutput clientFundOutput)
	{
		if (clientFundOutput != null && StringUtility.isStringPopulated(clientFundOutput.getCertificateName())) 
		{
			BusinessClientFund businessClientFund = new BusinessClientFund();
			businessClientFund.setTeacomputerId(clientFundOutput.getFundId());
			businessClientFund.setFundType(clientFundOutput.getAssetClass());
			businessClientFund.setFundName(clientFundOutput.getCertificateName());
			
			businessClientFund.setCurrencyName(clientFundOutput.getCurrencyName());
			businessClientFund.setCurrencyId(clientFundOutput.getCurrencyId());
			
			if(clientFundOutput.getCurrencyId()!=null&&StringUtility.isStringPopulated(CurrencyType.getById(Long.valueOf(clientFundOutput.getCurrencyId())).getType()))
			{
				businessClientFund.setCurrencyName(CurrencyType.getById((Long.valueOf(clientFundOutput.getCurrencyId()).longValue())).getType());
			}
			else
			{
				businessClientFund.setCurrencyName(clientFundOutput.getCurrencyName());
			}
			
			
			
			
			businessClientFund.setCurrencyRate(clientFundOutput.getCurrencyRate());
			businessClientFund.setTradePrice(clientFundOutput.getTradePrice());
			businessClientFund.setQuantity(clientFundOutput.getQuantity());
			if (clientFundOutput != null && StringUtility.isStringPopulated(clientFundOutput.getTradePrice())
					&& clientFundOutput.getQuantity() != null && clientFundOutput.getCurrencyRate() != null) {
			
				double value=(clientFundOutput.getQuantity().doubleValue()* Double.valueOf(clientFundOutput.getTradePrice()));
								
				businessClientFund.setTotalAmount(NumberUtility.changeFormat(new BigDecimal(value)));
			} else {
				businessClientFund.setTotalAmount(new BigDecimal(0));
			}
			return businessClientFund;
		} else {
			return null;
		}
	}

	@Override
	protected void setConsumer(BusinessClientFund businessClientFund) {
		this.consumer=getClientFundsApiConsumer;
		
	}

}
