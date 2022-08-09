package innovitics.azimut.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.Notification;
import innovitics.azimut.repositories.notification.NotificationRepository;

@Service
public class NotificationCenterService {

	public final static Logger logger = LogManager.getLogger(NotificationCenterService.class.getName());
	@Autowired NotificationRepository notificationRepository;
	
	public List<Notification> findAll()
	{
		List<Notification> notifications=new ArrayList<Notification>();
		notifications=this.notificationRepository.findAll();
		this.logger.info("notifications::" + notifications.toString());
		
		return notifications;
	}
}
