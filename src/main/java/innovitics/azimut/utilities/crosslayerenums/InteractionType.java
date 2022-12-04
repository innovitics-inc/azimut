package innovitics.azimut.utilities.crosslayerenums;

import java.util.List;

public enum InteractionType {

	ASK_QUESTION(1,"Ask a question"),
	REPORT_PROBLEM(2,"Report a problem"),
	GIVE_SUGGESTION(3,"Give a suggestion"),;

	private int id;
	private String type;

	InteractionType(int id, String type) {
		this.id=id;
		this.type=type;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}
	
	
	
	
	
}
