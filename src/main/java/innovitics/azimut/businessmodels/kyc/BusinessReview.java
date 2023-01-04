package innovitics.azimut.businessmodels.kyc;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessReview extends BaseBusinessEntity{
	
	protected Long id;
	
	protected Long questionId;
	
	protected Long pageId;
	
	protected Integer pageOrder;
	
	protected Long result;
	
	protected Long actionMaker;
	
	protected String comment;
	
	protected Long userId;

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

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public Long getActionMaker() {
		return actionMaker;
	}

	public void setActionMaker(Long actionMaker) {
		this.actionMaker = actionMaker;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
	

}
