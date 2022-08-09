package innovitics.azimut.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import innovitics.azimut.utilities.datautilities.DateUtility;

public class TemporaryBlobDeletionJob {

	public final static Logger logger = LogManager.getLogger(TemporaryBlobDeletionJob.class.getName());
	@Scheduled(fixedDelayString = "blob.temp.deletion.hours")
	public void scheduleFixedDelayTask() {
	    this.logger.info("Fixed delay task - " + DateUtility.getCurrentTimeStamp());	      
	}
}
