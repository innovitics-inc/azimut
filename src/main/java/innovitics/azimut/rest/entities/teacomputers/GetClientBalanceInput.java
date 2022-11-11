package innovitics.azimut.rest.entities.teacomputers;

public class GetClientBalanceInput extends TeaComputerInput {

	@Override
	public String getUrl() 
	{
		return super.setBaseUrl()+"GetClientCashBalance";		
	}
	
}
