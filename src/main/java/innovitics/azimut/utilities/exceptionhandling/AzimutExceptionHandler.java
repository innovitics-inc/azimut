package innovitics.azimut.utilities.exceptionhandling;

import java.util.List;

import org.springframework.stereotype.Component;

import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.NumberUtility;

@Component
public class AzimutExceptionHandler<T> extends ExceptionHandler{

	
	 public List<T> handleException(Exception exception,List<T> objectList,ErrorCode errorCode) throws BusinessException 
	{
		if(exception instanceof IntegrationException)
			
		    {	
				IntegrationException integrationException=(IntegrationException) exception;
				if(NumberUtility.areIntegerValuesMatching(integrationException.getErrorCode(), errorCode.getCode()))
				{
					return objectList;
				}
				else 
				{
					throw this.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
				}
			}
		
		else		
			{
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
			}
	}
	
	 
	 
}
