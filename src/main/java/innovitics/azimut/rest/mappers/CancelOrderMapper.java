package innovitics.azimut.rest.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.rest.apis.teacomputers.CancelOrderApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderInput;
import innovitics.azimut.rest.entities.teacomputers.PlaceOrderOutput;

@Component
public class CancelOrderMapper extends PlaceOrderMapper {

	@Autowired CancelOrderApiConsumer cancelOrderApiConsumer;
	@Override
	PlaceOrderInput createInput(BaseAzimutTrading baseAzimutTrading) {
		PlaceOrderInput input=new PlaceOrderInput();
		
		input.setTransactionId(String.valueOf(baseAzimutTrading.getTransactionId()));
		input.setIdTypeId(baseAzimutTrading.getAzIdType());
		input.setIdNumber(baseAzimutTrading.getAzId());
		
		return input;
	}
	
	@Override
	BaseAzimutTrading createBusinessEntityFromOutput(PlaceOrderOutput placeOrderOutput) 
	{
		BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
		
		return baseAzimutTrading;
	}

	
	@Override
	protected void setConsumer(BaseAzimutTrading baseAzimutTrading) {
		this.consumer=cancelOrderApiConsumer;
		
	}


}
