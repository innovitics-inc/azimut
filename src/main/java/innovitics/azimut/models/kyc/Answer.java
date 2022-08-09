package innovitics.azimut.models.kyc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Entity
@NamedEntityGraph(name = "Answer.details",
attributeNodes = { @NamedAttributeNode(value="relatedAnswers")})

@Table(name="answers")
public class Answer extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

private Long id;
@ManyToOne(cascade={CascadeType.ALL})
@JoinColumn(name="question_id")
private Question question;
private String answerType;
private String answerOrder;
private String answerOption;
private String answerPlaceHolder;
private Boolean isRelatedAnswerMandatory;
private String relatedQuestionText;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="parent_answer_id")
private Answer parentAnswer;

@OneToMany(mappedBy="parentAnswer")
@Fetch(FetchMode.JOIN)
private Set<Answer> relatedAnswers;

/*
@OneToMany(mappedBy="answer")
@Fetch(FetchMode.JOIN)
private Set<UserAnswer> userAnswers;
*/
private  Date createdAt;
private Date updatedAt;
private Date deletedAt;

private Boolean isAnswerMandatory;


public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Question getQuestion() {
	return question;
}
public void setQuestion(Question question) {
	this.question = question;
}
public String getAnswerType() {
	return answerType;
}
public void setAnswerType(String answerType) {
	this.answerType = answerType;
}
public String getAnswerOrder() {
	return answerOrder;
}
public void setAnswerOrder(String answerOrder) {
	this.answerOrder = answerOrder;
}
public String getAnswerOption() {
	return answerOption;
}
public void setAnswerOption(String answerOption) {
	this.answerOption = answerOption;
}

public String getAnswerPlaceHolder() {
	return answerPlaceHolder;
}
public void setAnswerPlaceHolder(String answerPlaceHolder) {
	this.answerPlaceHolder = answerPlaceHolder;
}
public Boolean getIsRelatedAnswerMandatory() {
	return isRelatedAnswerMandatory;
}
public void setIsRelatedAnswerMandatory(Boolean isRelatedAnswerMandatory) {
	this.isRelatedAnswerMandatory = isRelatedAnswerMandatory;
}
public Answer getParentAnswer() {
	return parentAnswer;
}
public void setParentAnswer(Answer parentAnswer) {
	this.parentAnswer = parentAnswer;
}
public Set<Answer> getRelatedAnswers() {
	return relatedAnswers;
}
public void setRelatedAnswers(Set<Answer> relatedAnswers) {
	this.relatedAnswers = relatedAnswers;
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
public String getRelatedQuestionText() {
	return relatedQuestionText;
}
public void setRelatedQuestionText(String relatedQuestionText) {
	this.relatedQuestionText = relatedQuestionText;
}
/*
public Set<UserAnswer> getUserAnswers() {
	return userAnswers;
}
public void setUserAnswers(Set<UserAnswer> userAnswers) {
	this.userAnswers = userAnswers;
}
*/
public Boolean getIsAnswerMandatory() {
	return isAnswerMandatory;
}
public void setIsAnswerMandatory(Boolean isAnswerMandatory) {
	this.isAnswerMandatory = isAnswerMandatory;
}




}
