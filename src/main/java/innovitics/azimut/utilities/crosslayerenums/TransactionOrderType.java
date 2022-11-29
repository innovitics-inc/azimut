package innovitics.azimut.utilities.crosslayerenums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TransactionOrderType {

	WITHDRAW(1,new ArrayList<Integer>(Arrays.asList(6,62)),"Withdraw","سحب"),
	INJECT(2,new ArrayList<Integer>(Arrays.asList(5,61)),"Inject","إيداع"),
	OTHER(3,new ArrayList<Integer>(Arrays.asList(0)),"Other","أخرى")
	;
	
	private TransactionOrderType(int typeId,List <Integer> teacomputerIds, String type,  String typeAr) 
	{
		this.typeId = typeId;
		this.type = type;
		this.teacomputerIds = teacomputerIds;
		this.typeAr = typeAr;
	}

	private final int typeId;
	private final String type;
	private final List <Integer> teacomputerIds;
	private final String typeAr;
	
	
	public int getTypeId() {
		return typeId;
	}
	public String getType() {
		return type;
	}
	public List <Integer> getTeacomputerIds() {
		return teacomputerIds;
	}
	public String getTypeAr() {
		return typeAr;
	}

	
	public static TransactionOrderType getById(int id) 
	{
	    for(TransactionOrderType transactionOrderType : values()) 
	    {
	        if(transactionOrderType.teacomputerIds.contains(id))
	        {
	        	return transactionOrderType;
	        }
	    }
	    return TransactionOrderType.OTHER;
	}
	
	public static TransactionOrderType getByOwnId(int id) 
	{
	    for(TransactionOrderType transactionOrderType : values()) 
	    {
	        if(transactionOrderType.getTypeId()==id)
	        {
	        	return transactionOrderType;
	        }
	    }
	    return TransactionOrderType.OTHER;
	}

}
