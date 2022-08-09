package innovitics.azimut.exceptions;

import java.util.Arrays;
import java.util.Date;

import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public class GeneralException  extends Exception{

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	  protected ErrorCode error;
	  protected Integer errorCode;
	  protected Date timestamp;
	  protected String errorMessage;
	  protected String description;
	  protected StackTraceElement [] stackTrace;
	  
	 
	  public GeneralException(ErrorCode error) 
	  {
		  super();
		  this.error=error;
		  this.errorCode = error.getCode();
		  this.errorMessage = error.getMessage();
	  }

	public GeneralException()
	  {
		  
	  }
	  
	public GeneralException(int errorCode, Date timestamp, String errorMessage, String description,
			StackTraceElement[] stackTrace) {
		super();
		this.errorCode = errorCode;
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
		this.description = description;
		this.stackTrace = stackTrace;
	}
	
	
	public ErrorCode getError() {
		return error;
	}

	public void setError(ErrorCode error) {
		this.error = error;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}
	@Override
	public String toString() {
		return "GeneralException [errorCode=" + errorCode + ", timestamp=" + timestamp + ", errorMessage="
				+ errorMessage + ", description=" + description + ", stackTrace=" + Arrays.toString(stackTrace) + "]";
	}
	
	 
	
	
	

}
