package innovitics.azimut.businessmodels.kyc;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "pages", singular = "page")
public class BusinessKYCPage extends BaseBusinessEntity{

	private Long id;
	private UserType userType;
	private String title;
	private int pageOrder;
	private String pageDetails;
	private String pageDisclaimer;
	private int noOfQuestions;
	private List<BusinessQuestion> questions;
	private Long previousId;
	private Long nextId;
	private Boolean isAnswered;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPageOrder() {
		return pageOrder;
	}
	public void setPageOrder(int pageOrder) {
		this.pageOrder = pageOrder;
	}
	public String getPageDetails() {
		return pageDetails;
	}
	public void setPageDetails(String pageDetails) {
		this.pageDetails = pageDetails;
	}
	public String getPageDisclaimer() {
		return pageDisclaimer;
	}
	public void setPageDisclaimer(String pageDisclaimer) {
		this.pageDisclaimer = pageDisclaimer;
	}
	public int getNoOfQuestions() {
		return noOfQuestions;
	}
	public void setNoOfQuestions(int noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}
	public List<BusinessQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<BusinessQuestion> questions) {
		this.questions = questions;
	}
		
	public Long getPreviousId() {
		return previousId;
	}
	public void setPreviousId(Long previousId) {
		this.previousId = previousId;
	}
	public Long getNextId() {
		return nextId;
	}
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

	public Boolean getIsAnswered() {
		return isAnswered;
	}
	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	@Override
	public String toString() {
		return "BusinessKYCPage [id=" + id + ", userType=" + userType + ", title=" + title + ", pageOrder=" + pageOrder
				+ ", pageDetails=" + pageDetails + ", pageDisclaimer=" + pageDisclaimer + ", noOfQuestions="
				+ noOfQuestions + ", questions=" + questions + ", previousId=" + previousId + ", nextId=" + nextId
				+ "]";
	}


	
	
	
}
