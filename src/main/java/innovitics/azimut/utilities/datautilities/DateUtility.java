package innovitics.azimut.utilities.datautilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import innovitics.azimut.businessservices.AbstractBusinessService;
import innovitics.azimut.utilities.ParentUtility;

public final class DateUtility extends ParentUtility{

	protected static final Logger logger = LoggerFactory.getLogger(DateUtility.class);
		
	public static String getCurrentNanoSecond()
	{
		//return String.valueOf(Instant.now().getEpochSecond());
		return String.valueOf(Math.abs(System.nanoTime()));
	}
	public static Date getCurrentDate()
	{
		return new Date();
	}
	
	public static String changeStringDateFormat(String inputDateString,SimpleDateFormat sourceFormat,SimpleDateFormat destinationFormat)
	{
	   if(StringUtility.isStringPopulated(inputDateString))
		{
		   String output="";
		   try {
			   Date inputDate=sourceFormat.parse(inputDateString);
			   output = destinationFormat.format(inputDate);
			   } 
		   catch (ParseException e) 
		   {
			   	e.printStackTrace();
				return null;
		   }  	    
		   return output;
		}
	   else
		   return null;
	}
	
	
	
	public static Date getCurrentDateWithOutTimeStamp()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime=new Date();
		try 
		{
			dateWithoutTime = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return dateWithoutTime;
	}
	public static Date getCurrentDateWithOutTimeStamp(String date)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime=new Date();
		try 
		{
			dateWithoutTime = simpleDateFormat.parse(simpleDateFormat.format(new Date(date)));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return dateWithoutTime;
	}
	
	
	public static Integer getCurrentYear()
	{
		return ((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYear();		
	}
	public static Integer getCurrentMonth()
	{
		return ((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getMonthValue();
	}
	public static Integer getCurrentDay()
	{
		return ((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getDayOfMonth();
	}

	public static String getCurrentYearMonthDay()
	{
		return getCurrentYear().toString()+"-"+getCurrentMonth().toString()+"-"+getCurrentDay().toString();
	}
	public static String getCurrentDayMonthYear()
	{
		return getCurrentDay().toString()+"-"+getCurrentMonth().toString()+"-"+getCurrentYear().toString();
	}
	
	public static String getCurrentYearMonth()
	{
		return getCurrentYear().toString()+"-"+getCurrentMonth().toString();
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
	    Date now = new Date();
	    String stringDate = simpleDateFormat.format(now);
	    return stringDate;
	}
	
	public static long getCurrentTimeInMilliSeconds()
	{
		return new Date().getTime();
	}
	
	public static boolean isFromDateBeforeToDate(String fromDate,String toDate)
	{
		boolean result=true;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			
			if(StringUtility.isStringPopulated(fromDate)&&StringUtility.isStringPopulated(toDate))
			{
				result= simpleDateFormat.parse(fromDate).before(simpleDateFormat.parse(toDate));
			}
			
		} catch (ParseException exception) {
				logger.info("Could not parse the date string");
				exception.printStackTrace();
		}
		logger.info("isFromDateBeforeToDate??:::"+result);
		return result;
	}
	public static Date changeStringDateToDate(String date)
	{
		  if(StringUtility.isStringPopulated(date))
		  {
			  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");  
		    try 
		    {
				return simpleDateFormat.parse(date);
			} 
		    catch (ParseException e) 
		    {
				logger.info("Could not parse the date string");
				e.printStackTrace();
			}
		  }
		   return null;
	}
	
	public static boolean areDatesDifferent(Date date1,Date date2)
	{
		logger.info("Date1:::"+date1);
		logger.info("Date2:::"+date2);
		boolean result=false;
		if(date1!=null&&date2!=null)
		{
			if(date1.compareTo(date2) > 0) {		
				result= true;
	      } else if(date1.compareTo(date2) < 0) {
	    	  result= true;
	      } else if(date1.compareTo(date2) == 0) {
	    	  result= false;
	      }
		}
		
		logger.info("Are dates different?::"+result);
		return result;
	}
	public static String changeDatetoStringDate(Date date,String destinationFormat) 
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(destinationFormat);
		return simpleDateFormat.format(date);
	}
}
