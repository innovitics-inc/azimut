package innovitics.azimut.models.kyc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedEntityGraph(name = "Question.details",
attributeNodes = { @NamedAttributeNode("answers"),@NamedAttributeNode(value="subQuestions")},
subgraphs = {@NamedSubgraph(name = "Answer.details",
attributeNodes = { @NamedAttributeNode(value="relatedAnswers")})
	}
)

@Entity
@Table(name="questions")

public class Question extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	protected Long id;
	protected String questionText;
	protected String questionTextAr;
	protected String answerType;
	protected Integer questionOrder;
	protected Boolean isAnswerMandatory;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="PAGE_ID")
	protected KYCPage kycPage;
	protected  Date createdAt;
	protected Date updatedAt;
	protected Date deletedAt;
	@OneToMany(mappedBy="question")
	@Fetch(FetchMode.JOIN)
	@OrderBy("answerOrder ASC")
	private Set<Answer> answers;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="parent_question_id")
	private Question parentQuestion;
	
	
	@OneToMany(mappedBy="parentQuestion")
	@Fetch(FetchMode.JOIN)
	@OrderBy("questionOrder ASC")
	private Set<Question> subQuestions;
		
	private String  questionPlaceHolder;
	private String  questionPlaceHolderAr;
	private String  pdfField;
	
	/*
	@OneToMany(mappedBy="question")
	@Fetch(FetchMode.JOIN)
	private Set<UserAnswer> userAnswers;
	*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public int getQuestionOrder() {
		return questionOrder;
	}
	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}
	public Boolean getIsAnswerMandatory() {
		return isAnswerMandatory;
	}
	public void setIsAnswerMandatory(Boolean isAnswerMandatory) {
		this.isAnswerMandatory = isAnswerMandatory;
	}
	public KYCPage getKycPage() {
		return kycPage;
	}
	public void setKycPage(KYCPage kycPage) {
		this.kycPage = kycPage;
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
	public Set<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	public Question getParentQuestion() {
		return parentQuestion;
	}
	public void setParentQuestion(Question parentQuestion) {
		this.parentQuestion = parentQuestion;
	}
	public Set<Question> getSubQuestions() {
		return subQuestions;
	}
	public void setSubQuestions(Set<Question> subQuestions) {
		this.subQuestions = subQuestions;
	}
	/*public Set<UserAnswer> getUserAnswers() {
		return userAnswers;
	}
	public void setUserAnswers(Set<UserAnswer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	*/
	public String getQuestionPlaceHolder() {
		return questionPlaceHolder;
	}
	public void setQuestionPlaceHolder(String questionPlaceHolder) {
		this.questionPlaceHolder = questionPlaceHolder;
	}	
	

	public String getQuestionTextAr() {
		return questionTextAr;
	}
	public void setQuestionTextAr(String questionTextAr) {
		this.questionTextAr = questionTextAr;
	}
	public String getQuestionPlaceHolderAr() {
		return questionPlaceHolderAr;
	}
	public void setQuestionPlaceHolderAr(String questionPlaceHolderAr) {
		this.questionPlaceHolderAr = questionPlaceHolderAr;
	}
	
	
	public String getPdfField() {
		return pdfField;
	}
	public void setPdfField(String pdfField) {
		this.pdfField = pdfField;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", questionText=" + questionText + ", answerType=" + answerType
				+ ", questionOrder=" + questionOrder + ", isAnswerMandatory=" + isAnswerMandatory + ", kycPage="
				+ kycPage + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt
				+ ", answers=" + answers + "]";
	}
		
	

}
