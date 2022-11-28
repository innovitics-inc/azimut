package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.teacomputers.CancelOrderInput;
import innovitics.azimut.rest.entities.teacomputers.CancelOrderOutput;
import innovitics.azimut.rest.models.teacomputers.CancelOrderRequest;
import innovitics.azimut.rest.models.teacomputers.CancelOrderResponse;

public class CancelOrderMapper extends RestMapper<CancelOrderInput, CancelOrderOutput, CancelOrderRequest, CancelOrderResponse, BaseAzimutTrading> {

	@Override
	BaseAzimutTrading consumeRestService(BaseAzimutTrading baseBusinessEntity, String params) throws IntegrationException, HttpClientErrorException, Exception 
	{
		return null;
	}

	@Override
	List<BaseAzimutTrading> consumeListRestService(BaseAzimutTrading baseBusinessEntity, String params)throws IntegrationException, HttpClientErrorException, Exception 
	{
		return null;
	}

	@Override
	CancelOrderInput createInput(BaseAzimutTrading baseBusinessEntity) {
		return null;
	}

	@Override
	BaseAzimutTrading createBusinessEntityFromOutput(CancelOrderOutput BaseOutput) {
		return null;
	}

	@Override
	protected List<BaseAzimutTrading> createListBusinessEntityFromOutput(CancelOrderOutput BaseOutput) {
		return null;
	}

	@Override
	protected void setConsumer(BaseAzimutTrading baseBusinessEntity) {
	}

}
