package innovitics.azimut.models;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BaseEntity {

	protected Long pageId;
	protected Long appUserId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private  Date createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date updatedAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date deletedAt;
	protected String privateUrl;
	protected MultipartFile file;
	protected Boolean draw;
	protected Integer userStep;
	protected Integer minimumInitialUnits;
	protected Integer minimumOwnedUnits;
	protected Double minimumInitialAmount;
	protected Double minimumOwnedAmount;
	protected Boolean block;

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
	public Long getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public String getPrivateUrl() {
		return privateUrl;
	}
	public void setPrivateUrl(String privateUrl) {
		this.privateUrl = privateUrl;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Boolean getDraw() {
		return draw;
	}
	public void setDraw(Boolean draw) {
		this.draw = draw;
	}
	public Integer getUserStep() {
		return userStep;
	}
	public void setUserStep(Integer userStep) {
		this.userStep = userStep;
	}
	public Integer getMinimumInitialUnits() {
		return minimumInitialUnits;
	}
	public void setMinimumInitialUnits(Integer minimumInitialUnits) {
		this.minimumInitialUnits = minimumInitialUnits;
	}
	public Integer getMinimumOwnedUnits() {
		return minimumOwnedUnits;
	}
	public void setMinimumOwnedUnits(Integer minimumOwnedUnits) {
		this.minimumOwnedUnits = minimumOwnedUnits;
	}
	public Double getMinimumInitialAmount() {
		return minimumInitialAmount;
	}
	public void setMinimumInitialAmount(Double minimumInitialAmount) {
		this.minimumInitialAmount = minimumInitialAmount;
	}
	public Double getMinimumOwnedAmount() {
		return minimumOwnedAmount;
	}
	public void setMinimumOwnedAmount(Double minimumOwnedAmount) {
		this.minimumOwnedAmount = minimumOwnedAmount;
	}
	public Boolean getBlock() {
		return block;
	}
	public void setBlock(Boolean block) {
		this.block = block;
	}
	
	
}
