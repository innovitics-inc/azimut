package innovitics.azimut.utilities.crosslayerenums;

public enum PaymentTransactionStatus {

	
	PENDING(1,"Transaction Pending","العملية معلقة"),
	PENDING_AT_GATEWAY(2,"Transaction Successful at the Gateway","تم بدأ العملية بنجاح"),
	SUCCESS(3,"Payment made successfully","تمت العملية بنجاح"),
	FAILED(4,"Transaction failed","لم تنجح العملية"),
	CANCELED(5,"Transaction cancelled","الغاء"),
	OTHER(6,"OTHER","أخرى")
	;

	PaymentTransactionStatus(int statusId,String status,String statusAr) {
		this.statusId=statusId;
		this.status=status;
		this.statusAr = statusAr;
	}

	private final int statusId;
	private final String status;
	private final String statusAr;
	
	public int getStatusId() {
		return statusId;
	}
	public String getStatus() {
		return status;
	}
	
	public String getStatusAr() {
		return statusAr;
	}
	
	public static PaymentTransactionStatus getById(int id) 
	{
		 for(PaymentTransactionStatus paymentTransactionStatus : values()) 
		    {
		        if(paymentTransactionStatus.statusId==id)
		        {
		        	return paymentTransactionStatus;
		        }
		    }
		    return PaymentTransactionStatus.OTHER;
	}


}
