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
		@SuppressWarnings("unchecked")
		BaseAzimutTrading responseBaseAzimutTrading=		
	/*	 (BaseAzimutTrading)(this.userBlockageUtility.
		 checkUserBlockage(Integer.valueOf(this.configProperties.getBlockageNumberOfTrials()),this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,this.restManager.placeOrderMapper,"consumeRestService",
				 new Object[]{this.prepareOrderPlacingInputs(tokenizedBusinessUser,baseAzimutTrading),null},
				 new Class<?>[]{BaseAzimutTrading.class,String.class},
				 ErrorCode.OPERATION_FAILURE));*/
				(BaseAzimutTrading)(this.userBlockageUtility.
						 checkUserBlockage(Integer.valueOf(this.configProperties.getBlockageNumberOfTrials()),this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,
								 this,"placeOrderRest",
								 new Object[]{tokenizedBusinessUser,baseAzimutTrading},
								 new Class<?>[]{BusinessUser.class,BaseAzimutTrading.class},
								 ErrorCode.OPERATION_FAILURE));
		return responseBaseAzimutTrading;
		 
	}
	
	public BaseAzimutTrading inject(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException, IOException
	{		
		@SuppressWarnings("unchecked")
		BaseAzimutTrading responseBaseAzimutTrading=		
		/* (BaseAzimutTrading)(this.userBlockageUtility.
				 checkUserBlockage(Integer.valueOf(this.configProperties.getBlockageNumberOfTrials()),this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,this.restManager.injectWithdrawMapper,"placeOrderRest",
						 new Object[]{this.prepareInjectWithdrawInputs(tokenizedBusinessUser,baseAzimutTrading),StringUtility.INFORM_DEPOSIT},
						 new Class<?>[]{BaseAzimutTrading.class,String.class},ErrorCode.OPERATION_FAILURE));*/
			(BaseAzimutTrading)(this.userBlockageUtility.
						 checkUserBlockage(Integer.valueOf(this.configProperties.getBlockageNumberOfTrials()),this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,
								 this,"injectRest",
								 new Object[]{tokenizedBusinessUser,baseAzimutTrading},
								 new Class<?>[]{BusinessUser.class,BaseAzimutTrading.class},
								 ErrorCode.OPERATION_FAILURE));
		return responseBaseAzimutTrading;
	
	}
	
	@SuppressWarnings("unchecked")
	public BaseAzimutTrading withdraw(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException
	{
		BaseAzimutTrading responseBaseAzimutTrading=				
			/*   (BaseAzimutTrading)(this.userBlockageUtility.
				 checkUserBlockage(Integer.valueOf(this.configProperties.getBlockageNumberOfTrials()),this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,this.restManager.injectWithdrawMapper,"consumeRestService",
						 new Object[]{this.prepareInjectWithdrawInputs(tokenizedBusinessUser,baseAzimutTrading),StringUtility.INFORM_WITHDRAW},
						 new Class<?>[]{BaseAzimutTrading.class,String.class},ErrorCode.OPERATION_FAILURE));
		*/
		(BaseAzimutTrading)(this.userBlockageUtility.
				 checkUserBlockage(Integer.valueOf(this.configProperties.getBlockageNumberOfTrials()),this.configProperties.getBlockageDurationInMinutes(),tokenizedBusinessUser,userMapper,
						 this,"withdrawRest",
						 new Object[]{tokenizedBusinessUser,baseAzimutTrading},
						 new Class<?>[]{BusinessUser.class,BaseAzimutTrading.class},
						 ErrorCode.OPERATION_FAILURE));
		return responseBaseAzimutTrading;
	}
	
	public BaseAzimutTrading placeOrderRest(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException,Exception
	{
		BaseAzimutTrading responseBaseAzimutTrading=(BaseAzimutTrading)this.restContract.getData(this.restContract.placeOrderMapper, this.prepareOrderPlacingInputs(tokenizedBusinessUser, baseAzimutTrading), null);
		return responseBaseAzimutTrading;
	}
	
	public BaseAzimutTrading injectRest(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws Exception
	{		
		BaseAzimutTrading responseBaseAzimutTrading=		
		 (BaseAzimutTrading)this.restContract.getData(this.restContract.injectWithdrawMapper, this.prepareInjectWithdrawInputs(tokenizedBusinessUser, baseAzimutTrading), StringUtility.INFORM_DEPOSIT);
		
		return responseBaseAzimutTrading;
	
	}
	
	public BaseAzimutTrading withdrawRest(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws IntegrationException, BusinessException,Exception
	{
		BaseAzimutTrading responseBaseAzimutTrading=				
				 (BaseAzimutTrading)this.restContract.getData(this.restContract.injectWithdrawMapper, this.prepareInjectWithdrawInputs(tokenizedBusinessUser, baseAzimutTrading), StringUtility.INFORM_WITHDRAW);
		
		return responseBaseAzimutTrading;
	}
	
	

	public BaseAzimutTrading getUserBlockage(BusinessUser tokenizedBusinessUser,String userPhone) throws BusinessException
	{
		BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
		
		try 
		{
			if(!StringUtility.isStringPopulated(userPhone))
			baseAzimutTrading.setUserBlockage(this.userBlockageUtility.getUserBlockage(tokenizedBusinessUser.getId(),true));
			else
			baseAzimutTrading.setUserBlockage(this.phoneNumberBlockageUtility.getUserBlockage(userPhone,true));
		}
		catch(Exception exception)
		{	
			this.exceptionHandler.getNullIfNonExistent(exception);
		}
		this.populateThreshold(baseAzimutTrading);
		return  baseAzimutTrading;
	}
	
	
	
	
	
	public BaseAzimutTrading incrementUserBlockage(BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		return null;
	}
	
	public BaseAzimutTrading incrementUserBlockage(BusinessUser tokenizedBusinessUser,String userPhone) throws BusinessException
	{
		boolean hasToken=tokenizedBusinessUser!=null&&!StringUtility.isStringPopulated(userPhone);
		
		BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
		UserBlockage userBlockage=new UserBlockage();
		try
		{						
			if(hasToken)
			{
				userBlockage=this.userBlockageUtility.getUserBlockage(tokenizedBusinessUser.getId(),false);	
			}
			else
			{
				userBlockage=this.phoneNumberBlockageUtility.getUserBlockage(userPhone,false);	
			}
			
			
			if(userBlockage==null)
			{
				
				this.logger.info("User Blockage none existent::");
				UserBlockage addedUserBlockage=new UserBlockage();
				
				if(hasToken)
				{
					addedUserBlockage=this.userBlockageUtility.addUserBlockage(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));					
				}
				else
				{
					addedUserBlockage=this.phoneNumberBlockageUtility.addUserBlockage(userPhone);					
				}
				
				addedUserBlockage.setUser(null);
				baseAzimutTrading.setUserBlockage(addedUserBlockage);
			}
			else
			{
				this.logger.info("User Blockage::" +userBlockage.toString());
				if(this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt()))
				{	
					
					if(userBlockage.getErrorCount()!=null&&(userBlockage.getErrorCount()<this.configProperties.getBlockageNumberOfTrials()))
					{
						int oldUserCount= userBlockage.getErrorCount().intValue();
						userBlockage.setErrorCount(oldUserCount+1);				
					}
					else if(NumberUtility.areIntegerValuesMatching(this.configProperties.getBlockageNumberOfTrials(), userBlockage.getErrorCount()))
					{
						userBlockage.setErrorCount(this.configProperties.getBlockageNumberOfTrials());
					}				
				}
				else if(!this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt()))
				{
					userBlockage.setErrorCount(1);
				}
				
				if(hasToken)
				{
					userBlockage.setUser(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));
				}

				this.userBlockageUtility.updateUserBlockage(userBlockage);
				userBlockage.setUser(null);
				
				
				baseAzimutTrading.setUserBlockage(userBlockage);
			}
			
			this.populateThreshold(baseAzimutTrading);
			return baseAzimutTrading;
		}
		catch (Exception exception)
		{
			throw this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}
		
	}
	
	
	
	public BaseAzimutTrading incrementUserBlockageUsingPhoneNumber(String userPhone) throws BusinessException
	{
		BaseAzimutTrading baseAzimutTrading=new BaseAzimutTrading();
		try
		{						
			UserBlockage userBlockage=this.phoneNumberBlockageUtility.getUserBlockage(userPhone,false);	
			if(userBlockage==null)
			{
				this.logger.info("User Blockage none existent::");
				UserBlockage addedUserBlockage=this.phoneNumberBlockageUtility.addUserBlockage(userPhone);
				addedUserBlockage.setUser(null);
				baseAzimutTrading.setUserBlockage(addedUserBlockage);
			}
			else
			{
				this.logger.info("User Blockage::" +userBlockage.toString());
				if(this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt()))
				{	
					
					if(userBlockage.getErrorCount()!=null&&(userBlockage.getErrorCount()<this.configProperties.getBlockageNumberOfTrials()))
					{
						int oldUserCount= userBlockage.getErrorCount().intValue();
						userBlockage.setErrorCount(oldUserCount+1);				
					}
					else if(NumberUtility.areIntegerValuesMatching(this.configProperties.getBlockageNumberOfTrials(), userBlockage.getErrorCount()))
					{
						userBlockage.setErrorCount(this.configProperties.getBlockageNumberOfTrials());
					}				
				}
				else if(!this.getMinutesBefore(this.configProperties.getBlockageDurationInMinutes()).before(userBlockage.getUpdatedAt()))
				{
					userBlockage.setErrorCount(1);
				}
				this.phoneNumberBlockageUtility.updateUserBlockage(userBlockage);
				baseAzimutTrading.setUserBlockage(userBlockage);
			}
			this.populateThreshold(baseAzimutTrading);
			return baseAzimutTrading;
		}
		catch (Exception exception)
		{
			throw this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}
		
	}
	
	
	
	private BaseAzimutTrading prepareOrderPlacingInputs(BusinessUser tokenizedBusinessUser, BaseAzimutTrading baseAzimutTrading) throws Exception {
		
		BaseAzimutTrading addBaseAzimutTrading=new BaseAzimutTrading();
		addBaseAzimutTrading.setAzId(tokenizedBusinessUser.getUserId());
		addBaseAzimutTrading.setAzIdType(this.getAzimutUserTypeId(tokenizedBusinessUser));
		addBaseAzimutTrading.setOrderTypeId(baseAzimutTrading.getOrderTypeId());
		addBaseAzimutTrading.setOrderValue(baseAzimutTrading.getOrderValue());
		addBaseAzimutTrading.setQuantity(baseAzimutTrading.getQuantity());
		addBaseAzimutTrading.setFundId(baseAzimutTrading.getTeacomputerId());
		addBaseAzimutTrading.setTransactionId(baseAzimutTrading.getTransactionId());
		return addBaseAzimutTrading;
	}
	
private BaseAzimutTrading prepareInjectWithdrawInputs(BusinessUser tokenizedBusinessUser, BaseAzimutTrading baseAzimutTrading) throws Exception {
		
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
			addBaseAzimutTrading.setReferenceNo(baseAzimutTrading.getReferenceNo());
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

	void populateThreshold(BaseAzimutTrading baseAzimutTrading)
	{
		baseAzimutTrading.setThreshold(this.configProperties.getBlockageNumberOfTrials());
	}

	public String getConcatenatedValue(String countryPhoneCode,String phoneNumber)
	{
		String userPhone=StringUtility.isStringPopulated(countryPhoneCode)&&StringUtility.isStringPopulated(phoneNumber)?countryPhoneCode+phoneNumber:null;
		if(StringUtility.isStringPopulated(userPhone))
		{
			String withPlus ="+"+userPhone;
			return withPlus;
		}
		return userPhone;
	}
	
	public BaseAzimutTrading cancelOrderRest(BusinessUser tokenizedBusinessUser,BaseAzimutTrading baseAzimutTrading) throws Exception
	{
		BaseAzimutTrading responseBaseAzimutTrading=new BaseAzimutTrading();
		try {
			responseBaseAzimutTrading = (BaseAzimutTrading)this.restContract.getData(this.restContract.cancelOrderMapper, this.prepareOrderPlacingInputs(tokenizedBusinessUser, baseAzimutTrading), null);
		} 
		catch (Exception exception) 
		{
			exception.printStackTrace();
			if(exception instanceof IntegrationException)
			throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.OPERATION_FAILURE);
			else
			throw new BusinessException(ErrorCode.OPERATION_FAILURE);
		}
		return responseBaseAzimutTrading;
	}

}
