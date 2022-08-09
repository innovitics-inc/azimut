package innovitics.azimut.businessutilities;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.mappers.ValifyAccessTokenMapper;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;

@Component
public class ValifyUtility {
	
	@Autowired  ValifyAccessTokenMapper valifyAccessTokenMapper;
	
	@Autowired ExceptionHandler exceptionHandler;
	
	
	public String getToken(Long id)throws BusinessException,IntegrationException
	{
		String token="";
		BusinessValify businessValify=new BusinessValify();
		try 
		{
			businessValify= this.valifyAccessTokenMapper.consumeRestService(null, null);
			token=businessValify.getToken();
			return token;
			
		} 
		catch (Exception exception) 
		{
			if(exception instanceof IntegrationException)
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
				else		
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
		}
	}
	

	protected  BusinessException handleBusinessException(Exception exception,ErrorCode errorCode)
	{
		if (this.exceptionHandler.isNonTechnicalException(exception, errorCode))
			return this.exceptionHandler.handleAsBusinessException(exception, errorCode);
		
		else
		return this.exceptionHandler.handleAsBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
	}
	
	
	public  String encodeImageToBase64(MultipartFile file)
	{
		 try{
		        byte[] image = Base64.encodeBase64(file.getBytes());
		        return  new String(image);  
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
		return null;
	}

}
