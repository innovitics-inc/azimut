package innovitics.azimut.businessmodels.kyc;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;

@CustomJsonRootName(plural = "data", singular = "data")
public class BusinessReview extends BaseBusinessEntity{
	
	protected Long id;
	
	protected Long questionId;
	
	protected Long pageId;
	
	protected Integer pageOrder;
	
	protected Long status;
	
	protected Long actionMaker;
	
	protected String reason;
	
	protected Long userId;
	
	protected List<Long> pageIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Integer getPageOrder() {
		return pageOrder;
	}

	public void setPageOrder(Integer pageOrder) {
		this.pageOrder = pageOrder;
	}

	public Long getActionMaker() {
		return actionMaker;
	}

	public void setActionMaker(Long actionMaker) {
		this.actionMaker = actionMaker;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getPageIds() {
		return pageIds;
	}

	public void setPageIds(List<Long> pageIds) {
		this.pageIds = pageIds;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
	

}
