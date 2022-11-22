package innovitics.azimut.utilities.crosslayerenums;

import innovitics.azimut.utilities.datautilities.StringUtility;

public enum PaymentTransactionStatus {

	
	I("I","Transaction Pending at Azimut","العملية معلقة"),
	PG("PG","Transaction Pending Paytabs","العملية معلقة"),
	A("A","Payment Authorized","تمت العملية بنجاح"),
	H("H","Transaction on Hold (Authorised but on hold for further anti-fraud review)",""),
	P("P","Transaction Pending (for refunds)",""),
	V("V","Transaction Voided",""),
	E("E","An Error has occured",""),
	D("D","Transaction Declined",""),
	OTHER("O","OTHER","أخرى"),
	;

	PaymentTransactionStatus(String statusId,String status,String statusAr) {
		this.statusId=statusId;
		this.status=status;
		this.statusAr = statusAr;
	}

	private final String statusId;
	private final String status;
	private final String statusAr;
	
	public String getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	
	public String getStatusAr() {
		return statusAr;
	}
	
	public static PaymentTransactionStatus getById(String id) 
	{
		 for(PaymentTransactionStatus paymentTransactionStatus : values()) 
		    {
		        if(StringUtility.stringsMatch(paymentTransactionStatus.statusId, id))
		        {
		        	return paymentTransactionStatus;
		        }
		    }
		    return PaymentTransactionStatus.OTHER;
	}


}
