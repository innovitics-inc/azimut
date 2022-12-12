package innovitics.azimut.businessmodels;

import java.util.List;

import innovitics.azimut.models.azimutdetails.AzimutInformation;

public class BusinessAzimutInformation extends BaseBusinessEntity{
	
	Integer type;
	private Long id;
	private String question;
	private String answer;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
	

}
