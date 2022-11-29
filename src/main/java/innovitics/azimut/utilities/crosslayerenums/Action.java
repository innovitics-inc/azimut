package innovitics.azimut.utilities.crosslayerenums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Action {

	BUY(1,"buy","شراء"),
	INJECT(2,"inject","إيداع"),
	OTHER(3,"OTHER","أخرى")
	;

	Action(int actionId,String action,String actionAr) {
		this.actionId=actionId;
		this.action=action;
		this.actionAr = actionAr;
	}

	private final int actionId;
	private final String action;
	private final String actionAr;
	
	public int getActionId() {
		return actionId;
	}
	public String getAction() {
		return action;
	}
	public String getActionAr() {
		return actionAr;
	}

	public static Action getById(int id) 
	{
	    for(Action action : values()) 
	    {
	        if(action.actionId==id)
	        {
	        	return action;
	        }
	    }
	    return Action.OTHER;
	}
	
}
