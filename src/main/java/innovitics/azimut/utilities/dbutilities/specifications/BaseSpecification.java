package innovitics.azimut.utilities.dbutilities.specifications;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.utilities.fileutilities.MyLogger;


public class BaseSpecification {
	public final static Logger logger = LogManager.getLogger(BaseSpecification.class.getName());
	protected java.sql.Date getComparingDates(String date, int order) {
	    java.sql.Date result = null;
	    SimpleDateFormat localeIta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    try {
	        if (order == 0) {
	            java.util.Date from = localeIta.parse(date.toString());
	            result = new java.sql.Date(from.getTime());
	        } else {
	            java.util.Date to = localeIta.parse(date.toString());
	            result = new java.sql.Date(to.getTime());
	            
	        }
	    } catch (ParseException e) {
	        MyLogger.info("Stack Trace");
	        e.printStackTrace();
	    }
	    MyLogger.info("SQL Date Result:::::"+result.toString());
	    return result;
	}
}
