package innovitics.azimut.models;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.user.User;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="notifications")
public class Notification extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	private String notificationText;
	private String notificationSource;
	private String notificationHeader;

	@ManyToOne
	@JoinColumn(name="notification_user_id",foreignKey = @javax.persistence.ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
	private User user;
	
	private Boolean isRead;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNotificationText() {
		return notificationText;
	}
	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}
	public String getNotificationSource() {
		return notificationSource;
	}
	public void setNotificationSource(String notificationSource) {
		this.notificationSource = notificationSource;
	}
	public String getNotificationHeader() {
		return notificationHeader;
	}
	public void setNotificationHeader(String notificationHeader) {
		this.notificationHeader = notificationHeader;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	

}
