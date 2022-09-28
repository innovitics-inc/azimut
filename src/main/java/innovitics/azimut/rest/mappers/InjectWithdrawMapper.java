package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.InjectWithdrawApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.InjectWithdrawInput;
import innovitics.azimut.rest.entities.teacomputers.InjectWithdrawOutput;
import innovitics.azimut.rest.models.teacomputers.InjectWithdrawResponse;

@Component
public class InjectWithdrawMapper extends RestMapper<InjectWithdrawInput, InjectWithdrawOutput, InjectWithdrawResponse, BaseAzimutTrading> {

	
	@Autowired InjectWithdrawApiConsumer injectWithdrawApiConsumer;
	
	@Override
	BaseAzimutTrading consumeRestService(BaseAzimutTrading baseAzimutTrading, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return this.createBusinessEntityFromOutput(this.injectWithdrawApiConsumer.invoke(this.createInput(baseAzimutTrading),InjectWithdrawResponse.class, params));
	}

	@Override
	List<BaseAzimutTrading> consumeListRestService(BaseAzimutTrading baseAzimutTrading, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return null;
	}

	@Override
	InjectWithdrawInput createInput(BaseAzimutTrading baseAzimutTrading) {
		InjectWithdrawInput input=new InjectWithdrawInput();
		input.setIdTypeId(baseAzimutTrading.getAzIdType());
		input.setIdNumber(baseAzimutTrading.getAzId());
		input.setAccountNo(baseAzimutTrading.getAccountNo());
		input.setCurrencyId(baseAzimutTrading.getCurrencyId());
		input.setOrderValue(baseAzimutTrading.getOrderValue());		
		return input;
	}

	@Override
	BaseAzimutTrading createBusinessEntityFromOutput(InjectWithdrawOutput BaseOutput) {
		return new BaseAzimutTrading();
	}

	@Override
	protected List<BaseAzimutTrading> createListBusinessEntityFromOutput(InjectWithdrawOutput BaseOutput) {
		return null;
	}

}
