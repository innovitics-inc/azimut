package innovitics.azimut.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="navs")
public class Nav extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long fundId;
	private Double nav;
	private  Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	private Long teacomputerId;
	private Date date;
	
	public Nav(Long id, Long fundId, Double nav, Date createdAt, Date updatedAt, Date deletedAt, Long teacomputerId,
			Date date) {
		super();
		this.id = id;
		this.fundId = fundId;
		this.nav = nav;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.teacomputerId = teacomputerId;
		this.date = date;
	}
	
	public Nav()
	{
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public Double getNav() {
		return nav;
	}
	public void setNav(Double nav) {
		this.nav = nav;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public Long getTeacomputerId() {
		return teacomputerId;
	}
	public void setTeacomputerId(Long teacomputerId) {
		this.teacomputerId = teacomputerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Nav [id=" + id + ", fundId=" + fundId + ", nav=" + nav + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", deletedAt=" + deletedAt + ", teacomputerId=" + teacomputerId + ", date=" + date + "]";
	}


	
	
	
}
