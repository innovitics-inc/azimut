package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.PlaceOrderApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderInput;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderOutput;
import innovitics.azimut.rest.models.teacomputers.PlaceOrderResponse;
@Component
public class PlaceOrderMapper extends RestMapper<PlaceOrderInput, PlaceOrderOutput, PlaceOrderResponse, BaseAzimutTrading>{

	@Autowired PlaceOrderApiConsumer placeOrderApiConsumer;
	
	@Override
	BaseAzimutTrading consumeRestService(BaseAzimutTrading baseAzimutTrading, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return this.createBusinessEntityFromOutput(this.placeOrderApiConsumer.invoke(this.createInput(baseAzimutTrading), PlaceOrderResponse.class, params));
		
	}

	@Override
	List<BaseAzimutTrading> consumeListRestService(BaseAzimutTrading baseAzimutTrading, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	PlaceOrderInput createInput(BaseAzimutTrading baseAzimutTrading) {
		PlaceOrderInput input=new PlaceOrderInput();
		
		input.setFundId(baseAzimutTrading.getFundId());
		input.setOrderTypeId(baseAzimutTrading.getOrderTypeId());
		input.setOrderValue(baseAzimutTrading.getOrderValue());
		input.setIdTypeId(baseAzimutTrading.getAzIdType());
		input.setIdNumber(baseAzimutTrading.getAzId());
		input.setQuantity(baseAzimutTrading.getQuantity());
		
		return input;
	}

	@Override
	BaseAzimutTrading createBusinessEntityFromOutput(PlaceOrderOutput placeOrderOutput) {
		
		return new BaseAzimutTrading();
	}

	@Override
	protected List<BaseAzimutTrading> createListBusinessEntityFromOutput(PlaceOrderOutput placeOrderOutput) {
		// TODO Auto-generated method stub
		return null;
	}

}