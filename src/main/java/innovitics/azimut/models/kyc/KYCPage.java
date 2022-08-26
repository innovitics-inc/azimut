package innovitics.azimut.models.kyc;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.user.UserType;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@NamedEntityGraph(name = "KYCPage.details",attributeNodes = @NamedAttributeNode(value="questions",subgraph = "Question.details"),

	subgraphs = 		
		{
				@NamedSubgraph(name = "Question.details",attributeNodes ={ @NamedAttributeNode(value="answers",subgraph = "Answer.details"),
							@NamedAttributeNode(value="subQuestions")}),
				@NamedSubgraph(name = "Answer.details",attributeNodes = { @NamedAttributeNode(value="relatedAnswers")})
				
		}
)
@NamedEntityGraph(name = "KYCPage.compact"
,attributeNodes = {
		@NamedAttributeNode(value="title"),
		@NamedAttributeNode(value="pageOrder"),
		@NamedAttributeNode(value="pageDetails"),
		@NamedAttributeNode(value="pageDisclaimer"),
		}

)
@Table(name="kyc_pages")
public class KYCPage  extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -5692495211725567689L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	@ManyToOne
	@JoinColumn(name="USER_ID_TYPE")
	private UserType userType;
	protected String title;
	protected Integer pageOrder;
	protected String pageDetails;
	protected String pageDisclaimer;
	protected int noOfQuestions;
	protected  Date createdAt;
	protected Date updatedAt;
	protected Date deletedAt;
		
	@OneToMany(mappedBy="kycPage",fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@OrderBy("questionOrder ASC")
	private Set<Question> questions;
	
	

	
	
	
	 //ADDED FIELD
    @Column(name = "next_id")
    private Long nextPageId;

    //ADDED FIELD
    @Column(name = "previous_id")
    private Long previousPageId;

    //CUSTOM SETTER


	private Integer weight;
	protected String titleAr;
	protected String pageDetailsAr;
	protected String pageDisclaimerAr;
	
	
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
public Integer getPageOrder() {
	return pageOrder;
}
public void setPageOrder(Integer pageOrder) {
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
public Set<Question> getQuestions() {
	return questions;
}
public void setQuestions(Set<Question> questions) {
	this.questions = questions;
}

public Long getNextPageId() {
	return nextPageId;
}
public void setNextPageId(Long nextPageId) {
	this.nextPageId = nextPageId;
}
public Long getPreviousPageId() {
	return previousPageId;
}
public void setPreviousPageId(Long previousPageId) {
	this.previousPageId = previousPageId;
}
public Integer getWeight() {
	return weight;
}
public void setWeight(Integer weight) {
	this.weight = weight;
}
public String getTitleAr() {
	return titleAr;
}
public void setTitleAr(String titleAr) {
	this.titleAr = titleAr;
}
public String getPageDetailsAr() {
	return pageDetailsAr;
}
public void setPageDetailsAr(String pageDetailsAr) {
	this.pageDetailsAr = pageDetailsAr;
}
public String getPageDisclaimerAr() {
	return pageDisclaimerAr;
}
public void setPageDisclaimerAr(String pageDisclaimerAr) {
	this.pageDisclaimerAr = pageDisclaimerAr;
}


}
