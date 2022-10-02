package innovitics.azimut.rest.mappers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.InjectWithdrawApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.InjectWithdrawInput;
import innovitics.azimut.rest.entities.teacomputers.InjectWithdrawOutput;
import innovitics.azimut.rest.models.teacomputers.InjectWithdrawResponse;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;

@Component
public class InjectWithdrawMapper extends RestMapper<InjectWithdrawInput, InjectWithdrawOutput, InjectWithdrawResponse, BaseAzimutTrading> {

	@Autowired BlobFileUtility blobFileUtility;
	@Autowired InjectWithdrawApiConsumer injectWithdrawApiConsumer;
	
	@Override
	BaseAzimutTrading consumeRestService(BaseAzimutTrading baseAzimutTrading, String params) throws IntegrationException, HttpClientErrorException, Exception {
		
		if(baseAzimutTrading!=null&&StringUtility.stringsMatch(params, StringUtility.INFORM_WITHDRAW))
		{
			this.uploadFile(baseAzimutTrading);
		}
		
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
	
	protected void uploadFile(BaseAzimutTrading baseAzimutTrading) throws BusinessException, IOException
	{
		this.blobFileUtility.uploadFileToBlob(baseAzimutTrading.getInjectionDocument(), true, this.configProperties.getBlobSignedPdfPath(), "injections/"+baseAzimutTrading.getUserId()+"/"+DateUtility.getCurrentDayMonthYear(), true);

	}

}
