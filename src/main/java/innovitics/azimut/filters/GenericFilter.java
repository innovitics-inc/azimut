package innovitics.azimut.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.security.HmacUtil;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

@Component
public class GenericFilter implements Filter {
	protected final Log logger = LogFactory.getLog(getClass());
	private static final String SIGNATURE_HEADER = "Signature";

	@Autowired HmacUtil hmacUtil;
	@Autowired ConfigProperties configProperties;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  {       
        RequestWrapper wrapper;
		try {
			
				wrapper = new RequestWrapper((HttpServletRequest) request);
				String signatureHeader = wrapper.getHeader(SIGNATURE_HEADER);
				this.logger.info("signature header:::"+signatureHeader);
		        byte[] body = StreamUtils.copyToByteArray(wrapper.getInputStream());
		        String jsonRequest =new String(body, 0, body.length, wrapper.getCharacterEncoding());		        
		        logger.info("REQUEST:::"+jsonRequest);
		        if(signatureHeader!=null&&StringUtility.stringsDontMatch(signatureHeader, hmacUtil.generateHmac256(jsonRequest,this.configProperties.getPaytabsServerKey())))
		        {
		        	throw new BusinessException(ErrorCode.INVALID_SIGNATURE);
		        }
		        
		        chain.doFilter(wrapper, response);
		        return;
			} 			
			catch (IOException e) 
			{
				e.printStackTrace();
				setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,(HttpServletResponse)response,ErrorCode.OPERATION_NOT_PERFORMED);
			} 
			catch (BusinessException e) 
			{
				e.printStackTrace();
				setErrorResponse(HttpStatus.BAD_REQUEST,(HttpServletResponse)response,ErrorCode.INVALID_SIGNATURE);
			} 
			catch (ServletException e) 
			{
				e.printStackTrace();
				setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,(HttpServletResponse)response,ErrorCode.OPERATION_NOT_PERFORMED);
			}
    }
 
    public void setErrorResponse(HttpStatus status, HttpServletResponse response,ErrorCode errorCode)
    {
		response.setStatus(status.value());
		response.setContentType("application/json");
        try 
        {
            String json = StringUtility.convertToJson(errorCode);
            response.getWriter().write(json);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    
    
    
    
}
