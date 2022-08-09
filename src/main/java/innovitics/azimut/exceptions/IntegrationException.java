package innovitics.azimut.exceptions;

import java.util.Arrays;
import java.util.Date;

import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public class IntegrationException extends GeneralException {

	/**
	 * 
	 */
	
	private String transactionId;
	
	
	private static final long serialVersionUID = 1L;

	public IntegrationException(int statusCode, Date timestamp, String message, String description, StackTraceElement[] stackTrace) {
		super(statusCode, timestamp, message, description, stackTrace);
	}

	
	public IntegrationException(ErrorCode errorCode) 
	{
		super(errorCode);
		
	}


	public IntegrationException() {
		// TODO Auto-generated constructor stub
	}


	public IntegrationException(Integer errorCode, Date timestamp, String message, String description, StackTraceElement[] stackTrace) 
	{
		super(errorCode, timestamp, message, description, stackTrace);
		
	}
	
	

	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	@Override
	public String toString() {
		return "IntegrationException [transactionId=" + transactionId + ", error=" + error + ", errorCode=" + errorCode
				+ ", timestamp=" + timestamp + ", errorMessage=" + errorMessage + ", description=" + description
				+ ", stackTrace=" + Arrays.toString(stackTrace) + "]";
	}


	
	

	
	
	
	
}
