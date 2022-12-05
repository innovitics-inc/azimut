package innovitics.azimut.jobs;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessservices.BusinessFundsService;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.PropertyTagFinder;

@Component
public class TeaComputersFundPriceJob/* extends ParentJob*/{
	/*@Autowired BusinessFundsService businessFundsService;
	
	@Override
	@Scheduled(fixedDelayString = "${tea.computers.job.delay.seconds}", timeUnit = TimeUnit.SECONDS)
	public void scheduleFixedDelayTask() 
	{
		super.scheduleFixedDelayTask();
		try 
		{	
			this.businessFundsService.updateFundPrices();
		} 
		catch (Exception exception) 
		{
			this.logger.info("Could not update the fund prices");
			exception.printStackTrace();
		} 
	}


	@Override
	public String getName() {
		return this.getClass().getName();
	}
	*/
}
