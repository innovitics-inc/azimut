package innovitics.azimut.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

import innovitics.azimut.utilities.exceptionhandling.ErrorCode;

public class BusinessException extends GeneralException {

	private static final long serialVersionUID = 1L;

	public BusinessException(Integer errorCode, Date timestamp, String message,String messageAr ,String description, StackTraceElement[] stackTrace) 
	{
		super(errorCode, timestamp, message,messageAr,description, stackTrace);
		
	}
	
	public BusinessException(ErrorCode errorCode) 
	{
		super(errorCode);
		
	}
	public BusinessException(ErrorCode errorCode,HttpStatus httpStatus)
	{
		super(errorCode,httpStatus);
	}

	@Override
	public String toString() {
		return "BusinessException [error=" + error + ", errorCode=" + errorCode + ", timestamp=" + timestamp
				+ ", errorMessage=" + errorMessage + ", description=" + description + "]";
	}



	/*
	 * *
	 * 
	 * 
	 */
	

}
