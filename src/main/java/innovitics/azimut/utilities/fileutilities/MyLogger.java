package innovitics.azimut.utilities.fileutilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MyLogger {
	protected static final Logger logger = LoggerFactory.getLogger(MyLogger.class);

	
	public static void info(String value)
	{
		logger.info("Trx Id:"+Thread.currentThread().getId()+"::"+value);
	}

	public static void error(String value)
	{
		logger.error("Error Trx Id::"+Thread.currentThread().getId()+"::"+value);
	}
	
	
}
