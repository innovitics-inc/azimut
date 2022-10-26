package innovitics.azimut.utilities.crosslayerenums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TransactionStatus {

	PENDING(1,new ArrayList<Integer>(Arrays.asList(610,592)),"PENDING","معلق"),
	TIKCET(2,new ArrayList<Integer>(Arrays.asList(593)),"POSTED","تنفيذ"),
	CANCELED(3,new ArrayList<Integer>(Arrays.asList(595)),"CANCELED","الغاء"),
	OTHER(4,new ArrayList<Integer>(Arrays.asList(0)),"OTHER","أخرى")
	;

	TransactionStatus(int statusId,List <Integer> teacomputerIds,String status,String statusAr) {
		this.statusId=statusId;
		this.status=status;
		this.teacomputerTypeIds = teacomputerIds;
		this.statusAr = statusAr;
	}

	private final int statusId;
	private final String status;
	private final List <Integer> teacomputerTypeIds;
	private final String statusAr;
	
	public int getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	public List<Integer> getTeacomputerTypeIds() {
		return teacomputerTypeIds;
	}
	public String getStatusAr() {
		return statusAr;
	}
	
	public static TransactionStatus getById(int id) 
	{
	    for(TransactionStatus transactionStatus : values()) 
	    {
	        if(transactionStatus.teacomputerTypeIds.contains(id))
	        {
	        	return transactionStatus;
	        }
	    }
	    return TransactionStatus.OTHER;
	}

	
}
