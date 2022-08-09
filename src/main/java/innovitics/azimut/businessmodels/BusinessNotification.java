package innovitics.azimut.businessmodels;

import innovitics.azimut.businessmodels.user.BusinessUser;

public class BusinessNotification extends BaseBusinessEntity{
	
	protected Long id;
	private String notificationText;
	private String notificationSource;
	private String notificationHeader;
	private BusinessUser user;	
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
	public BusinessUser getUser() {
		return user;
	}
	public void setUser(BusinessUser user) {
		this.user = user;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	
	
	
}
