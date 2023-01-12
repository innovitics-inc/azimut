package innovitics.azimut.utilities.logging;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.threading.CurrentRequestHolder;

public final class MyLogger {
	protected static final Logger logger = LoggerFactory.getLogger(MyLogger.class);

	
	private  static String idgen()
	{
		//return CurrentRequestHolder.get()!=null&&StringUtility.isStringPopulated(CurrentRequestHolder.get().getSystemTrx())?CurrentRequestHolder.get().getSystemTrx():"";
		return Thread.currentThread().getName();
	}
	
	public static void info(String value)
	{
		logger.info("Trx Id: "+idgen()+" : "+value);
		
	}

	public static void error(String value)
	{
		logger.error("Error Trx Id: "+idgen()+" : "+value);
	}
	
	
}


