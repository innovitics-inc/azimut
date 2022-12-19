package innovitics.azimut.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class ResponseLoggingFilter extends GenericFilterBean {

	public void doFilter(ServletRequest request, final ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    final CopyPrintWriter writer = new CopyPrintWriter(response.getWriter());
	    chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) 
	    {	
	    	@Override public PrintWriter getWriter() 
	        {
	            return writer;
	        }
	    });
	    this.logger.info("Response::::"+writer.getCopy());
	} 
}