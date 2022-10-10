package innovitics.azimut.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import innovitics.azimut.utilities.datautilities.DateUtility;

public abstract class ParentJob {
	
	public abstract String getName();
	
	public final static Logger logger = LogManager.getLogger(ParentJob.class.getName());
	
	public void scheduleFixedDelayTask() {
	    this.logger.info("Time stamp for the job "+ this.getName()+" starting at "+ DateUtility.getCurrentTimeStamp());	      
	}
	
	
	
	
}
