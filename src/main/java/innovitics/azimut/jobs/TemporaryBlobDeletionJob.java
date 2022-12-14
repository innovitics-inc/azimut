package innovitics.azimut.jobs;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import innovitics.azimut.businessservices.BusinessFundsService;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;

public class TemporaryBlobDeletionJob extends ParentJob{
	
@Autowired BlobFileUtility blobFileUtility;

	
	@Override
	@Scheduled(fixedDelayString = "${temp.file.delete.delay.hours}", timeUnit = TimeUnit.HOURS)
	public void scheduleFixedDelayTask() 
	{
		super.scheduleFixedDelayTask();
		try 
		{	
			this.blobFileUtility.deleteAllFilesFromBlob(this.configProperties.getBlobKYCDocumentsContainer(),"Users");
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
	
	
}
