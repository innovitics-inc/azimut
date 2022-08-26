package innovitics.azimut.utilities.crosslayerenums;

public enum UserStep {

	POPUP(1,"Pop up",0),
	ID_IMAGES(2,"Id Images",0),
	CLIENT_DATA(3,"Client Data",0),
	STRAIGHT_AND_SMILE(4,"Straight and Smiling Faces",0),
	LEFT_AND_RIGHT(5,"Left and Right Sides",0),
	BANK_REFERENCES_IGNORE(6,"Ignore Bank References",0),
	BANK_REFERENCES_SHOW(7,"Show Bank References",0),
	KYC(8,"KYC",0),
	CONTRACT_MAP(9,"Contract Map",0),
	CHOOSE_CONTRACT_MAP(10,"Contract Map Chosen",0),
	FINISHED(11,"Finished",0),
	;

	UserStep(int stepId, String step,int weight) {
		this.stepId=stepId;
		this.step=step;
		this.weight=weight;
	}

	private final int stepId;
	private final String step;
	private final int weight;
	public int getStepId() {
		return stepId;
	}
	public String getStep() {
		return step;
	}
	public int getWeight() {
		return weight;
	}
	
	  public static int findWeightById(int id) 
	  {  
	      for(UserStep userStep : values()) 
	      {
	         if(userStep.getStepId()==id) 
	         {
	            return userStep.getWeight(); 
	         }
	      }
		return 0;              
	  }
	
	
}
