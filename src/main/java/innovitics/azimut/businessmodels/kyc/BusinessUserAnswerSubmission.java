package innovitics.azimut.businessmodels.kyc;

import java.util.Arrays;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessUserAnswerSubmission extends BaseBusinessEntity{
	
	private Long id;
	private Long pageId;
	private Long nextPageId;
	private BusinessUserSubmittedAnswer[] userAnswers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public Long getNextPageId() {
		return nextPageId;
	}
	public void setNextPageId(Long nextPageId) {
		this.nextPageId = nextPageId;
	}
	public BusinessUserSubmittedAnswer[] getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(BusinessUserSubmittedAnswer[] userAnswers) {
		this.userAnswers = userAnswers;
	}
	@Override
	public String toString() {
		return "BusinessUserAnswerSubmission [id=" + id + ", pageId=" + pageId + ", userAnswers="
				+ Arrays.toString(userAnswers) + "]";
	} 
	
	
}
