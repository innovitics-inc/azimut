package innovitics.azimut.utilities.exceptionhandling;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.apache.http.conn.HttpHostConnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;


@Service
public class ExceptionHandler{

	protected static final Logger logger = LogManager.getLogger(ExceptionHandler.class);

	//protected static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	public BusinessException handleAsBusinessException(Exception exception,ErrorCode errorCode)
	  
	{
		  	logger.error("Exception Caught");
			logger.error("Exception Cause:"+exception.getCause());
			logger.error("Exception Message:"+exception.getMessage());
			logger.error("Exception Details:",exception);
			BusinessException businessException=new  BusinessException(errorCode.getCode(),DateUtility.getCurrentDate(),errorCode.getMessage(),errorCode.getMessageAr() ,exception.getMessage(), exception.getStackTrace());
			
			logger.error("Exxception:::"+businessException.toString());
			
			return businessException;
	  }
	public BusinessException handleIntegrationExceptionAsBusinessException(IntegrationException integrationException,ErrorCode errorCode)
	  
	{
			logger.info("Handling Exception as an Integration Exception in the Exception Handler::::");
		  	logger.error("Exception Caught");
		  	logger.error("Exception Error Code:"+integrationException.getErrorCode());
			logger.error("Exception Cause:"+integrationException.getCause());
			logger.error("Exception Message:"+integrationException.getErrorMessage());
			logger.error("Exception Details:",integrationException.toString());
			BusinessException businessException=new  BusinessException(integrationException.getErrorCode(),DateUtility.getCurrentDate(),integrationException.getErrorMessage(),integrationException.getErrorMessageAr(), integrationException.getDescription(), integrationException.getStackTrace());
			
			logger.error("Exxception:::"+businessException.toString());
			
			return businessException;
	  }
	public IntegrationException handleAsIntegrationException(Exception exception,ErrorCode errorCode)
	  
	{
		  	logger.error("Exception Caught");
			logger.error("Exception Cause:"+exception.getCause());
			logger.error("Exception Message:"+exception.getMessage());
			
			IntegrationException integrationException=new   IntegrationException(errorCode.getCode(),DateUtility.getCurrentDate(),errorCode.getMessage(),errorCode.getMessageAr(), exception.getMessage(), exception.getStackTrace());
			
			logger.error("Exxception:::"+integrationException.toString());
			
			return integrationException;	  
		}
	
	
	
	
	
	
	
	public boolean isExceptionOfTypeEntityNotFoundException(Exception exception)
	{
		boolean result=exception instanceof EntityNotFoundException||(exception.getMessage()!=null&&exception.getMessage().contains("EntityNotFoundException"));
		this.logger.info("exception instanceof EntityNotFoundException:::"+result);
		return result;
	}
	
	public boolean isExceptionOfTypeNoResultException(Exception exception)
	{
		boolean result=exception instanceof EntityNotFoundException||(exception.getMessage()!=null&&exception.getMessage().contains("NoResultException"));
		this.logger.info("exception instanceof NoResultException:::"+result);
		return (result);
	}
	
	public boolean isExceptionOfTypeNoSuchElementException(Exception exception)
	{
		boolean result=exception instanceof NoSuchElementException||(exception.getMessage()!=null&&exception.getMessage().contains("NoSuchElementException"));
		this.logger.info("exception instanceof NoSuchElementException:::"+result);
		return (result);
	}
	
	public boolean isExceptionValidationException(ErrorCode errorCode)
	{
		boolean result=errorCode!=null&&errorCode.getCode()==ErrorCode.INVALID_FIELD_VALUE.getCode();
		this.logger.info("exception instanceof ValidationException:::"+result);
		return (result);
		
		
	}
	
	public boolean isABusinessException(Exception exception)
	{
		boolean result=false;
		
		result= this.isExceptionOfTypeEntityNotFoundException(exception)||this.isExceptionOfTypeNoResultException(exception)||this.isExceptionOfTypeNoSuchElementException(exception);
		this.logger.info("No data was found");
		
		return result;
	}
	
	public boolean isNonTechnicalException(Exception exception,ErrorCode errorCode)
	{
		return this.isExceptionOfTypeEntityNotFoundException(exception)||this.isExceptionOfTypeNoResultException(exception)||this.isExceptionOfTypeNoSuchElementException(exception)||this.isExceptionValidationException(errorCode);
	}
	
	public boolean isConnectionTimeOutException(Exception exception)
	{
		boolean result=false;
		
		if(exception instanceof ResourceAccessException&&exception.getCause() instanceof HttpHostConnectException)
		{
			result=true;
		}
		else
		{
			result=false;
		}
		this.logger.info("Did the connection timeout?::"+result);
		return result;
	}
	public  BusinessException handleBusinessException(Exception exception,ErrorCode errorCode)
	{
		if (this.isNonTechnicalException(exception, errorCode))
			return this.handleAsBusinessException(exception, errorCode);
		
		else
		return this.handleAsBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
	}
	

	
	public  BusinessException handleIntegrationException(IntegrationException integrationException,ErrorCode errorCode)
	{
		this.logger.info("Handling Exception as an Integration Exception::::"+integrationException.getMessage());
		return this.handleIntegrationExceptionAsBusinessException(integrationException, errorCode);
	}
	
	public  BusinessException handleBusinessExceptionAsIs(Exception exception,ErrorCode errorCode)
	{
			this.logger.info("Handling Exception as Is"+exception.getMessage());
			return this.handleAsBusinessException(exception, errorCode);
	}
	
	public boolean checkIfIntegrationExceptinWithSpecificErrorCode(Exception exception,ErrorCode errorCode)
	{	
			if((exception instanceof IntegrationException)&&NumberUtility.areIntegerValuesMatching(((IntegrationException)exception).getErrorCode().intValue(),errorCode.getCode()))
			{
				return true;
			}
			return false;
	}
	 public BusinessException handleException(Exception exception) 
		{
			if(exception instanceof IntegrationException)
				return this.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else if(exception instanceof BusinessException)
				return (BusinessException)exception;		
			else
				return this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}
	
	public Object getNullIfNonExistent(Exception exception)
	 {
		 exception.printStackTrace();
			if(this.isABusinessException(exception))
			{
				return null;
			}
			else
			return null;
	 }
	
	public boolean isInvocationException(Exception exception)
	{
		boolean result=true;
		if(exception instanceof NoSuchMethodException || 
		   exception instanceof SecurityException || 
		   exception instanceof IllegalAccessException ||
		   exception instanceof IllegalArgumentException)
			{
			result= false;
			}
		else if(exception instanceof InvocationTargetException)		
			{
			result= true;
			}
	
		return result;
	}
	
}
