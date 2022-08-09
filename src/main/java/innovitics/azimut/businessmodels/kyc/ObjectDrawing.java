package innovitics.azimut.businessmodels.kyc;

import innovitics.azimut.utilities.crosslayerenums.ObjectWidthType;

public class ObjectDrawing {

	private Integer objectId;
	private ObjectWidthType objectWidthType;
	private Boolean passedValue;
	private Integer width;
	private Long questionId;
	private Integer widthId;
	
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	public ObjectWidthType getObjectWidthType() {
		return objectWidthType;
	}
	public void setObjectWidthType(ObjectWidthType objectWidthType) {
		this.objectWidthType = objectWidthType;
	}
	public Boolean getPassedValue() {
		return passedValue;
	}
	public void setPassedValue(Boolean passedValue) {
		this.passedValue = passedValue;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Integer getWidthId() {
		return widthId;
	}
	public void setWidthId(Integer widthId) {
		this.widthId = widthId;
	}
	@Override
	public String toString() {
		return "ObjectDrawing [objectId=" + objectId + ", objectWidthType=" + objectWidthType + ", passedValue="
				+ passedValue + ", width=" + width + ", questionId=" + questionId + ", widthId=" + widthId + "]";
	}
	
	
	
	
}
