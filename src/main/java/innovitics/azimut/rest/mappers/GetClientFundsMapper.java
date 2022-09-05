package innovitics.azimut.rest.mappers;

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
import innovitics.azimut.rest.models.teacomputers.GetClientFundsResponse;
import innovitics.azimut.utilities.datautilities.StringUtility;

@Component
public class GetClientFundsMapper extends RestMapper<GetClientFundsInput, GetClientFundsOutput, GetClientFundsResponse, BusinessClientFund> 
{

	@Autowired GetClientFundsApiConsumer getClientFundsApiConsumer;

	@Override
	BusinessClientFund consumeRestService(BusinessClientFund businessClientFund, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return null;
	}

	@Override
	List<BusinessClientFund> consumeListRestService(BusinessClientFund businessClientFund, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return  this.createListBusinessEntityFromOutput(this.getClientFundsApiConsumer.invoke(this.createInput(businessClientFund),ClientFundResponse[].class, params));
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
		// TODO Auto-generated method stub
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
					BusinessClientFund  businessClientFund=new BusinessClientFund();
					businessClientFund.setFundType(clientFundOutput.getAssetClass());
					businessClientFund.setFundName(clientFundOutput.getCertificateName());
					businessClientFund.setCurrencyName(clientFundOutput.getCurrencyName());
					businessClientFund.setCurrencyRate(clientFundOutput.getCurrencyRate());
					businessClientFund.setTradePrice(clientFundOutput.getTradePrice());
					businessClientFund.setQuantity(clientFundOutput.getQuantity());
					if(clientFundOutput!=null&&StringUtility.isStringPopulated(clientFundOutput.getTradePrice())&&clientFundOutput.getQuantity()!=null)
					{	
						double totalAmount=clientFundOutput.getQuantity().doubleValue()*Double.valueOf(clientFundOutput.getTradePrice());
						businessClientFund.setTotalAmount(totalAmount);
					}
					else
					{
						businessClientFund.setTotalAmount(0d);
					}
					
					businessClientFunds.add(businessClientFund);
					
				}
			return businessClientFunds;
		}		
				return null;
	}

}
