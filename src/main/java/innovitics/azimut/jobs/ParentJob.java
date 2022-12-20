package innovitics.azimut.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.MyLogger;

public abstract class ParentJob {
	
	public abstract String getName();
	@Autowired protected ConfigProperties configProperties;
	
	public final static Logger logger = LogManager.getLogger(ParentJob.class.getName());
	
	public void scheduleFixedDelayTask() {
		MyLogger.info("Time stamp for the job "+ this.getName()+" starting at "+ DateUtility.getCurrentTimeStamp());	      
	}
	
	
	
	
}
