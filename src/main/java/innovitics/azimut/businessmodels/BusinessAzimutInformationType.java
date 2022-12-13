package innovitics.azimut.businessmodels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.utilities.CustomJsonRootName;
@CustomJsonRootName(plural = "data", singular = "data")
public class BusinessAzimutInformationType  extends  BaseBusinessEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6327924498322143931L;
	Integer type;
	String typeName;
	private Long id;
	private String question;
	private String answer;
	@JsonProperty("dataList")
	List<BusinessAzimutInformation> businessAzimutInformationList;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public List<BusinessAzimutInformation> getBusinessAzimutInformationList() {
		return businessAzimutInformationList;
	}
	public void setBusinessAzimutInformationList(List<BusinessAzimutInformation> businessAzimutInformationList) {
		this.businessAzimutInformationList = businessAzimutInformationList;
	}
	
	
}
