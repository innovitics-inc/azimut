package innovitics.azimut.businessservices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.WrapperBusinessEntity;
import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.rest.mappers.InjectWithdrawMapper;
import innovitics.azimut.rest.mappers.PlaceOrderMapper;
import innovitics.azimut.utilities.businessutilities.UserBlockageUtility;
import innovitics.azimut.utilities.crosslayerenums.ModuleType;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.UserMapper;

@Service
public class BusinessAzimutTradingService extends AbstractBusinessService<BaseAzimutTrading> {

	@Autowired UserMapper userMapper;

	public BaseAzimutTrading placeOrder(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException
	{				
		Integer numberOfTrials=Integer.valueOf(this.configProperties.getBlockageNumberOfTrials());
		@SuppressWarnings("unchecked")
		BaseAzimutTrading responseBaseAzimutTrading=
		
		 (BaseAzimutTrading)(this.userBlockageUtility.
		 checkUserBlockage(numberOfTrials,this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,this.restManager.placeOrderMapper,"consumeRestService",
				 new Object[]{this.prepareOrderPlacingInputs(tokenizedBusinessUser,baseAzimutTrading),null},
				 new Class<?>[]{BaseAzimutTrading.class,String.class},
				 ErrorCode.OPERATION_FAILURE));
		return responseBaseAzimutTrading;
		 
	}
	
	public BaseAzimutTrading inject(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException, IOException
	{		
		Integer numberOfTrials=Integer.valueOf(this.configProperties.getBlockageNumberOfTrials());
		@SuppressWarnings("unchecked")
		BaseAzimutTrading responseBaseAzimutTrading=
		
		 (BaseAzimutTrading)(this.userBlockageUtility.
				 checkUserBlockage(numberOfTrials,this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,this.restManager.injectWithdrawMapper,"consumeRestService",
						 new Object[]{this.prepareInjectWithdrawInputs(tokenizedBusinessUser,baseAzimutTrading),StringUtility.INFORM_DEPOSIT},
						 new Class<?>[]{BaseAzimutTrading.class,String.class},ErrorCode.OPERATION_FAILURE));
		
		return responseBaseAzimutTrading;
	
	}
	
	@SuppressWarnings("unchecked")
	public BaseAzimutTrading withdraw(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException
	{
		Integer numberOfTrials=Integer.valueOf(this.configProperties.getBlockageNumberOfTrials());
		BaseAzimutTrading responseBaseAzimutTrading=
				
				 (BaseAzimutTrading)(this.userBlockageUtility.
				 checkUserBlockage(numberOfTrials,this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,this.restManager.injectWithdrawMapper,"consumeRestService",
						 new Object[]{this.prepareInjectWithdrawInputs(tokenizedBusinessUser,baseAzimutTrading),StringUtility.INFORM_WITHDRAW},
						 new Class<?>[]{BaseAzimutTrading.class,String.class},ErrorCode.OPERATION_FAILURE));
		
		return responseBaseAzimutTrading;
	}
	
	

	public BaseAzimutTrading getUserBlockage(BusinessUser tokenizedBusinessUser)
	{
		BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
		
		try 
		{
			baseAzimutTrading.setUserBlockage(this.userBlockageUtility.getUserBlockage(tokenizedBusinessUser.getId()));
		}
		catch(Exception exception)
		{	
			this.exceptionHandler.getNullIfNonExistent(exception);
		}
		
		return  baseAzimutTrading;
	}
	
	public BaseAzimutTrading incrementUserBlockage(BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
		try
		{
			
			baseAzimutTrading=this.getUserBlockage(tokenizedBusinessUser);
			UserBlockage userBlockage=baseAzimutTrading.getUserBlockage();
			
			if(userBlockage==null)
			{
				UserBlockage addedUserBlockage=this.userBlockageUtility.addUserBlockage(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));
				addedUserBlockage.setUser(null);
				baseAzimutTrading.setUserBlockage(addedUserBlockage);
			}
			else
			{
				if(userBlockage.getErrorCount()!=null)
				{	
					int oldUserCount= userBlockage.getErrorCount().intValue();
					userBlockage.setErrorCount(oldUserCount+1);
					userBlockage.setUser(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));
					this.userBlockageUtility.updateUserBlockage(userBlockage);
					userBlockage.setUser(null);
				}
			}
			return baseAzimutTrading;
		}
		catch (Exception exception)
		{
			throw this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}
		
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
		
		if(baseAzimutTrading!=null)
		{
			addBaseAzimutTrading.setAzId(tokenizedBusinessUser.getUserId());
			addBaseAzimutTrading.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
			addBaseAzimutTrading.setOrderValue(baseAzimutTrading.getOrderValue());
			addBaseAzimutTrading.setAccountId(baseAzimutTrading.getAccountId());
			addBaseAzimutTrading.setBankId(baseAzimutTrading.getBankId());
			addBaseAzimutTrading.setCurrencyId(baseAzimutTrading.getCurrencyId());
			addBaseAzimutTrading.setUserId(tokenizedBusinessUser.getUserId());		
			addBaseAzimutTrading.setModuleTypeId(ModuleType.CASH.getTypeId());
			if(baseAzimutTrading.getInjectionDocument()!=null)
			try 
			{
				addBaseAzimutTrading.setFileBytes(baseAzimutTrading.getInjectionDocument().getBytes().toString());
			} 
			catch (IOException ioException) 
			{
				this.logger.info("Could not extract the file bytes");
				ioException.printStackTrace();
			}
		 }
		
		return addBaseAzimutTrading;
	}

}
