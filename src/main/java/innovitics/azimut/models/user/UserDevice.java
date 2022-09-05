package innovitics.azimut.models.user;

import java.util.Date;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="user_devices")
public class UserDevice extends BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String userId;
	private String 	userPhone;
	private String deviceId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date	createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date	updatedAt;
	
	@ManyToOne
	@JoinColumn(name="app_user_id",foreignKey = @javax.persistence.ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
	
	private User user;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "UserDevice [id=" + id + ", userId=" + userId + ", userPhone=" + userPhone + ", deviceId=" + deviceId
				+ ", createdAt=" + createdAt + ", user=" + user + "]";
	}
	
	
	
	
	
}
