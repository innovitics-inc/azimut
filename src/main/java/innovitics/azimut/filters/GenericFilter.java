package innovitics.azimut.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public abstract class GenericFilter extends ParentUtility{

	@Autowired protected ConfigProperties configProperties;
	abstract List<String> getFilterablePaths();
	protected boolean applyFilterOnPath(List<String> filterablePaths,String actualPath) 
	 {
		 return (filterablePaths.contains(actualPath));
		 		 
		 /*		 
		 	boolean result=false;
			for(String filterablePath:filterablePaths)
			{	
				if(filterablePath.contains(actualPath))
					{
						result=true;
						break;
					}
			}
			return result;
		  */
	 }
	
	
	 protected void setErrorResponse(HttpStatus status, HttpServletResponse response,ErrorCode errorCode)
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
