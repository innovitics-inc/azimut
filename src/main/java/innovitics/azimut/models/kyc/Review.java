package innovitics.azimut.models.kyc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@NamedEntityGraph(name = "Review.details",attributeNodes = {@NamedAttributeNode("id"),@NamedAttributeNode("result"),@NamedAttributeNode("actionMaker"),@NamedAttributeNode("comment"),@NamedAttributeNode("userId")}
)

@Entity
@Table(name="reviews")
public class Review extends BaseEntity{	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	
	protected Long questionId;
	
	protected Long pageId;
	
	protected Integer pageOrder;
	
	protected Long result;
	
	protected Long actionMaker;
	
	protected String comment;
	
	protected Long userId;
	
	protected String commentAr;
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
	public String getCommentAr() {
		return commentAr;
	}
	public void setCommentAr(String commentAr) {
		this.commentAr = commentAr;
	}
	

	
	

}
