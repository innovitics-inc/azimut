package innovitics.azimut.repositories.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import innovitics.azimut.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
