package innovitics.azimut.businessservices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.mappers.InjectWithdrawMapper;
import innovitics.azimut.rest.mappers.PlaceOrderMapper;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Service
public class BusinessAzimutTradingService extends AbstractBusinessService<BaseAzimutTrading> {

	@Autowired PlaceOrderMapper placeOrderMapper;
	@Autowired InjectWithdrawMapper injectWithdrawMapper;
	
	public BaseAzimutTrading placeOrder(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException
	{		
		try 
		{
		
			return this.placeOrderMapper.wrapBaseBusinessEntity(false, this.prepareOrderPlacingInputs(tokenizedBusinessUser,baseAzimutTrading), null).getData();
		}
		
		catch(Exception exception)
		{

			if(exception instanceof IntegrationException)
			throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else		
			throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}
	
	
	}
	
	public BaseAzimutTrading inject(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException, IOException
	{
		try 
		{
			this.blobFileUtility.uploadFileToBlob(baseAzimutTrading.getInjectionDocument(), true, this.configProperties.getBlobSignedPdfPath(), "injections/"+tokenizedBusinessUser.getUserId()+"/"+DateUtility.getCurrentDayMonthYear(), true);
			return this.injectWithdrawMapper.wrapBaseBusinessEntity(false, this.prepareInjectWithdrawInputs(tokenizedBusinessUser, baseAzimutTrading), StringUtility.INFORM_DEPOST).getData();
		}
		catch(Exception exception)
		{
			if(exception instanceof IntegrationException)
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else		
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}
	}
	
	public BaseAzimutTrading withdraw(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException
	{
		return this.injectWithdrawMapper.wrapBaseBusinessEntity(false,  this.prepareInjectWithdrawInputs(tokenizedBusinessUser, baseAzimutTrading), StringUtility.INFORM_WITHDRAW).getData();
	}

	
	private BaseAzimutTrading prepareOrderPlacingInputs(BusinessUser tokenizedBusinessUser, BaseAzimutTrading baseAzimutTrading) {
		
		BaseAzimutTrading addBaseAzimutTrading=new BaseAzimutTrading();
		addBaseAzimutTrading.setAzId(tokenizedBusinessUser.getUserId());
		addBaseAzimutTrading.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		addBaseAzimutTrading.setOrderTypeId(baseAzimutTrading.getOrderTypeId());
		addBaseAzimutTrading.setOrderValue(baseAzimutTrading.getOrderValue());
		addBaseAzimutTrading.setQuantity(baseAzimutTrading.getQuantity());
		addBaseAzimutTrading.setFundId(baseAzimutTrading.getTeacomputerId());
		return addBaseAzimutTrading;
	}
	
private BaseAzimutTrading prepareInjectWithdrawInputs(BusinessUser tokenizedBusinessUser, BaseAzimutTrading baseAzimutTrading) {
		
		BaseAzimutTrading addBaseAzimutTrading=new BaseAzimutTrading();
		addBaseAzimutTrading.setAzId(tokenizedBusinessUser.getUserId());
		addBaseAzimutTrading.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		addBaseAzimutTrading.setOrderValue(baseAzimutTrading.getOrderValue());
		addBaseAzimutTrading.setAccountNo(baseAzimutTrading.getAccountNo());
		addBaseAzimutTrading.setCurrencyId(baseAzimutTrading.getCurrencyId());
		return addBaseAzimutTrading;
	}
	
	
}
