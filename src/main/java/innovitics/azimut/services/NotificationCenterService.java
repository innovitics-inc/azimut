package innovitics.azimut.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.Notification;
import innovitics.azimut.repositories.notification.NotificationRepository;
import innovitics.azimut.utilities.fileutilities.MyLogger;

@Service
public class NotificationCenterService {

	@Autowired NotificationRepository notificationRepository;
	
	public List<Notification> findAll()
	{
		List<Notification> notifications=new ArrayList<Notification>();
		notifications=this.notificationRepository.findAll();
		MyLogger.info("notifications::" + notifications.toString());
		
		return notifications;
	}
}
