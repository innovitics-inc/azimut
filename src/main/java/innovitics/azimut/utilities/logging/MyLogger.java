package innovitics.azimut.utilities.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MyLogger {
	protected static final Logger logger = LoggerFactory.getLogger(MyLogger.class);

	
	private  static String idgen()
	{
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
