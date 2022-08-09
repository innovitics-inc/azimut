package innovitics.azimut.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.PropertyTagFinder;

@Component
public class TeaComputersFundPriceJob {
	public final static Logger logger = LogManager.getLogger(TeaComputersFundPriceJob.class.getName());
	/*@Scheduled(fixedDelayString = "${tea.computers.job.delay.milliseconds}")
	public void scheduleFixedDelayTask() {
	    this.logger.info("Fixed delay task - " + DateUtility.getCurrentTimeStamp());	      
	}*/
	
}
