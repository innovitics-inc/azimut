package innovitics.azimut.rest.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessClientCashBalance;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetClientCashBalanceApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.ClientBalanceOutput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceInput;
import innovitics.azimut.rest.entities.teacomputers.GetClientBalanceOutput;
import innovitics.azimut.rest.entities.teacomputers.TransactionOutput;
import innovitics.azimut.rest.models.teacomputers.ClientCashBalanceResponse;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceRequest;
import innovitics.azimut.rest.models.teacomputers.GetClientCashBalanceResponse;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Service
public class GetClientBalanceMapper extends RestMapper<GetClientBalanceInput, GetClientBalanceOutput,GetClientCashBalanceRequest,ClientCashBalanceResponse[], BusinessClientCashBalance>{

	@Autowired GetClientCashBalanceApiConsumer getClientBalanceApiConsumer ;
	@Autowired  ListUtility<ClientBalanceOutput> listUtility;
	@Override
	BusinessClientCashBalance consumeRestService(BusinessClientCashBalance businessClientBalance, String params)
			throws HttpClientErrorException, Exception {
		return null;
	}

	@Override
	List<BusinessClientCashBalance> consumeListRestService(BusinessClientCashBalance businessClientBalance, String params)throws IntegrationException, HttpClientErrorException, Exception 
	{
		return  this.createListBusinessEntityFromOutput(this.getClientBalanceApiConsumer.invoke(this.createInput(businessClientBalance),ClientCashBalanceResponse[].class, params));
	}

	@Override
	GetClientBalanceInput createInput(BusinessClientCashBalance businessClientBalance) {
		GetClientBalanceInput input=new GetClientBalanceInput();
		input.setIdTypeId(businessClientBalance.getAzIdType());
		input.setIdNumber(businessClientBalance.getAzId());
		return input;
	}

	@Override
	BusinessClientCashBalance createBusinessEntityFromOutput(GetClientBalanceOutput getClientBalanceOutput) {
		BusinessClientCashBalance businessClientBalance=new BusinessClientCashBalance();
		businessClientBalance.setBalance(getClientBalanceOutput.getBalance());
		businessClientBalance.setCurrencyID(getClientBalanceOutput.getCurrencyID());
		businessClientBalance.setCurrencyName(getClientBalanceOutput.getCurrencyName());
		businessClientBalance.setPendingTransfer(getClientBalanceOutput.getPendingTransfer());
		
		return businessClientBalance;
	}

	@Override
	protected List<BusinessClientCashBalance> createListBusinessEntityFromOutput(GetClientBalanceOutput getClientBalanceOutput) {
		
		List<BusinessClientCashBalance> businessClientCashBalances=new ArrayList<BusinessClientCashBalance>();
		if(getClientBalanceOutput!=null&&this.listUtility.isListPopulated(getClientBalanceOutput.getClientBalanceOutputs()))

		{
			for(ClientBalanceOutput clientBalanceOutput:getClientBalanceOutput.getClientBalanceOutputs())
			{
				if(clientBalanceOutput!=null)
				{
					BusinessClientCashBalance businessClientCashBalance=new BusinessClientCashBalance();
				
				if(clientBalanceOutput.getBalance()!=null)
				{
					businessClientCashBalance.setBalance(clientBalanceOutput.getBalance());
					businessClientCashBalance.setBalanceFormatted(NumberUtility.changeFormat(clientBalanceOutput.getBalance()));
				}
				else 
				{
					businessClientCashBalance.setBalance(0D);
					businessClientCashBalance.setBalanceFormatted("0");
				}
				
				
				businessClientCashBalance.setCurrencyID(clientBalanceOutput.getCurrencyID());
				if(clientBalanceOutput.getCurrencyID()!=null&&StringUtility.isStringPopulated(CurrencyType.getById(Long.valueOf(clientBalanceOutput.getCurrencyID())).getType()))
				{
					businessClientCashBalance.setCurrencyName(CurrencyType.getById(clientBalanceOutput.getCurrencyID()).getType());
				}
				else
				{
					businessClientCashBalance.setCurrencyName(clientBalanceOutput.getCurrencyName());
				}
				
				businessClientCashBalance.setCurrencyRate(clientBalanceOutput.getCurrencyRate());
				if(clientBalanceOutput.getOutPendingTrans()!=null&&clientBalanceOutput.getInPendingTrans()!=null)
				{
					double value=clientBalanceOutput.getInPendingTrans().doubleValue()-clientBalanceOutput.getOutPendingTrans().doubleValue();
					businessClientCashBalance.setPendingTransfer(value);
					businessClientCashBalance.setPendingTransferFormatted(value!=0D?NumberUtility.changeFormat(value):"0");
				}
				else
				{
					businessClientCashBalance.setPendingTransfer(0D);
					businessClientCashBalance.setPendingTransferFormatted("0");
				}
				
				businessClientCashBalance.setTotalBuyValue(clientBalanceOutput.getTotalBuyValue());
				
				
				businessClientCashBalances.add(businessClientCashBalance);
				}
			}
		}
		
		
		return businessClientCashBalances; 
		
		
	}

	@Override
	protected void setConsumer(BusinessClientCashBalance businessClientBalance) {
		this.consumer=getClientBalanceApiConsumer;
		
	}

}
