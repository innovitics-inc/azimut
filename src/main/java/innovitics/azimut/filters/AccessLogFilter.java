package innovitics.azimut.filters;
import javax.servlet.Filter;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.azure.core.implementation.UnixTime;

import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.logging.FileUtility;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.threading.CurrentRequestHolder;
@Component
public class AccessLogFilter implements Filter {

	protected static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

	@Autowired FileUtility fileUtility;
  	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

  		//ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest)request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse)response);

		
		String transaction=CurrentRequestHolder.get()!=null&&StringUtility.isStringPopulated(CurrentRequestHolder.get().getSystemTrx())?CurrentRequestHolder.get().getSystemTrx():"";

		
		
		RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) request);
		byte[] body = StreamUtils.copyToByteArray(wrapper.getInputStream());
		String requestBody =new String(body, 0, body.length, wrapper.getCharacterEncoding());	
		
		
		MyLogger.info("IP Address::"+wrapper.getRemoteAddr());
		MyLogger.info("REQUEST::"+transaction+"::"+requestBody);
		
		
		this.fileUtility.write("IP Address::"+wrapper.getRemoteAddr());
		this.fileUtility.write("REQUEST::"+transaction+"::"+requestBody);
		
		
		chain.doFilter(wrapper, responseWrapper);
        
        // Get Cache

	    /*String requestBody = StringUtility.getStringValue(requestWrapper.getContentAsByteArray(),requestWrapper.getCharacterEncoding());
		MyLogger.info("REQUEST:::"+requestBody);*/

		String responseBody = StringUtility.getStringValue(responseWrapper.getContentAsByteArray(),response.getCharacterEncoding()); 
		  
		MyLogger.info("RESPONSE::"+transaction+"::"+responseBody);
		responseWrapper.copyBodyToResponse();
		
		
		this.fileUtility.write("RESPONSE::"+transaction+"::"+responseBody);
		 
	}

  	
  	
}