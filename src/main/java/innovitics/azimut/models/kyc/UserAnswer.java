package innovitics.azimut.models.kyc;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.user.User;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="user_answers")
@NamedEntityGraph(name = "UserAnswer.details")
public class UserAnswer  extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	

	private Long id;
	private Long userId;
	private Long questionId;
	private Long answerId;
	private Long relatedAnswerId;  
	private String countryPhoneCode;
	private String answerValue;
	private String answerType;
	private String documentName;
	private String documentSize;
	private String documentUrl;
	private String documentSubDirectory;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date updatedAt;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private Date deletedAt;
	private String countryCode;
	private Long pageId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	/*
	 public Question getQuestion() 
	  { 
	 	return question; 
	  } 
	 public void setQuestion(Question question) 
	 { 
	 	this.question = question; 
	 }
	 */
	/*
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	*/
	
	public Long getRelatedAnswerId() {
		return relatedAnswerId;
	}
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public void setRelatedAnswerId(Long relatedAnswerId) {
		this.relatedAnswerId = relatedAnswerId;
	}
	public String getCountryPhoneCode() {
		return countryPhoneCode;
	}
	public void setCountryPhoneCode(String countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}
	public String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentSize() {
		return documentSize;
	}
	public void setDocumentSize(String documentSize) {
		this.documentSize = documentSize;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	
	public String getDocumentSubDirectory() {
		return documentSubDirectory;
	}
	public void setDocumentSubDirectory(String documentSubDirectory) {
		this.documentSubDirectory = documentSubDirectory;
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
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	@Override
	public String toString() {
		return "UserAnswer [id=" + id + ", userId=" + userId + ", questionId=" + questionId + ", relatedAnswerId="
				+ relatedAnswerId + ", countryPhoneCode=" + countryPhoneCode + ", answerValue=" + answerValue
				+ ", answerType=" + answerType + ", documentName=" + documentName + ", documentSize=" + documentSize
				+ ", documentUrl=" + documentUrl + ", documentSubDirectory=" + documentSubDirectory + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", countryCode=" + countryCode
				+ ", pageId=" + pageId + "]";
	}
	
	
	
	
		
	
	
}
