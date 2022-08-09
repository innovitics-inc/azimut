package innovitics.azimut.models.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.kyc.KYCPage;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="user_types")
public class UserType extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String idType;
	private int noOfKYCPages;
	private int valifyImageCount;
	private  Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	private Long firstPageId;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public int getNoOfKYCPages() {
		return noOfKYCPages;
	}
	public void setNoOfKYCPages(int noOfKYCPages) {
		this.noOfKYCPages = noOfKYCPages;
	}
	public int getValifyImageCount() {
		return valifyImageCount;
	}
	public void setValifyImageCount(int valifyImageCount) {
		this.valifyImageCount = valifyImageCount;
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

	public Long getFirstPageId() {
		return firstPageId;
	}
	public void setFirstPageId(Long firstPageId) {
		this.firstPageId = firstPageId;
	}
	@Override
	public String toString() {
		return "UserType [id=" + id + ", idType=" + idType + ", noOfKYCPages=" + noOfKYCPages + ", valifyImageCount="
				+ valifyImageCount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt="
				+ deletedAt + "]";
	}
	
	
	
	
}
