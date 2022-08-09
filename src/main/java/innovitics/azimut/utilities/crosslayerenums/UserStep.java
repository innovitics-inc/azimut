package innovitics.azimut.utilities.crosslayerenums;

public enum UserStep {

	POPUP(1,"Pop up"),
	ID_IMAGES(2,"Id Images"),
	CLIENT_DATA(3,"Client Data"),
	STRAIGHT_AND_SMILE(4,"Straight and Smiling Faces"),
	LEFT_AND_RIGHT(5,"Left and Right Sides"),
	BANK_REFERENCES_IGNORE(6,"Ignore Bank References"),
	BANK_REFERENCES_SHOW(7,"Show Bank References"),
	KYC(8,"KYC"),
	CONTRACT_MAP(9,"Contract Map"),
	FINISH(10,"Contract Map Chosen")
	;

	UserStep(int stepId, String step) {
		this.stepId=stepId;
		this.step=step;
	}

	private final int stepId;
	private final String step;
	
	public int getStepId() {
		return stepId;
	}
	public String getStep() {
		return step;
	}
	
	

	
	
}
