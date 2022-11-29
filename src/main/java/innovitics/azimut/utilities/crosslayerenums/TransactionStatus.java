package innovitics.azimut.utilities.crosslayerenums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import innovitics.azimut.utilities.datautilities.StringUtility;

public enum TransactionStatus {

	PENDING(1,new ArrayList<Integer>(Arrays.asList(610,592)),"PENDING","معلق",new ArrayList<String>(Arrays.asList(""))),
	TIKCET(2,new ArrayList<Integer>(Arrays.asList(593)),"POSTED","تنفيذ",new ArrayList<String>(Arrays.asList(""))),
	CANCELED(3,new ArrayList<Integer>(Arrays.asList(595)),"CANCELED","الغاء",new ArrayList<String>(Arrays.asList(""))),
	OTHER(4,new ArrayList<Integer>(Arrays.asList(0)),"OTHER","أخرى",new ArrayList<String>(Arrays.asList(""))),
	PAYMENT_SUCCESSFUL(5,new ArrayList<Integer>(Arrays.asList(0)),"PAYMENT_SUCCESSFUL","أخرى",new ArrayList<String>(Arrays.asList("A"))),
	UNPAID(6,new ArrayList<Integer>(Arrays.asList(0)),"PAYMENT_PENDING","أخرى",new ArrayList<String>(Arrays.asList("I","PG","H","P"))),
	PAYMENT_FAILED(7,new ArrayList<Integer>(Arrays.asList(0)),"PAYMENT_FAILED","أخرى",new ArrayList<String>(Arrays.asList("E","D"))),
	PAYMENT_CANCELED(8,new ArrayList<Integer>(Arrays.asList(0)),"PAYMENT_CANCELED","أخرى",new ArrayList<String>(Arrays.asList("V")))
	;

	TransactionStatus(int statusId,List <Integer> teacomputerIds,String status,String statusAr,List <String> paymentStatuses) {
		this.statusId=statusId;
		this.status=status;
		this.teacomputerTypeIds = teacomputerIds;
		this.statusAr = statusAr;
		this.paymentStatuses=paymentStatuses;
	}

	private final int statusId;
	private final String status;
	private final List <Integer> teacomputerTypeIds;
	private final String statusAr;
	private final List <String> paymentStatuses;
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
	public List<String> getPaymentStatuses() {
		return paymentStatuses;
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

	public static TransactionStatus getByPaymentStatusId(String status) 
	{
	    for(TransactionStatus transactionStatus : values()) 
	    {
	        for(String paymentTransactionStatus:transactionStatus.paymentStatuses)
	        {
	        	if(StringUtility.stringsMatch(paymentTransactionStatus, status))
	        	return transactionStatus;
	        }
	    }
	    return TransactionStatus.OTHER;
	}

}
