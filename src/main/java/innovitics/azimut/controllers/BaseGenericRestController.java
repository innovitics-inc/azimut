package innovitics.azimut.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.lang.IllegalStateException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.AbstractBusinessService;
import innovitics.azimut.businessutilities.BusinessSearchCriteria;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.GeneralException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.security.JwtUtil;
import innovitics.azimut.utilities.CustomJsonRootName;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.threading.CurrentRequestHolder;
import innovitics.azimut.validations.Validation;


public abstract class BaseGenericRestController<T extends BaseBusinessEntity>{

	@Autowired ListUtility<T> listUtility;
	protected @Autowired Validation<T> validation;
	protected @Autowired JwtUtil jwtUtil;
	protected @Autowired ConfigProperties configProperties;

	private HttpStatus validateGenericResponseSuccess(BaseGenericResponse<T> baseGenericResponse) {
		if (baseGenericResponse != null
				&& checkIfErrorCodeInBaseGenericResponseIsSuccessful(baseGenericResponse.getStatus())) {
			return HttpStatus.OK;
		} else
			return HttpStatus.INTERNAL_SERVER_ERROR;

	}

	private BaseGenericResponse<T> constructGenericBaseResponseCodeAndMessage(BaseGenericResponse<T> baseResponse) {
		baseResponse.setMessage(StringUtility.SUCCESS);
		baseResponse.setStatus(StringUtility.SUCCESS_CODE);
		baseResponse.setTransactionId(CurrentRequestHolder.get().getSystemTrx());
		MyLogger.info(baseResponse.toString());
		return baseResponse;
	}

	private boolean checkIfErrorCodeInBaseGenericResponseIsSuccessful(Integer errorCode) {
		return (errorCode != null && errorCode.intValue() == 0);
	}

	protected ResponseEntity<BaseGenericResponse<T>> handleBaseGenericResponseException(BusinessException businessException) {
		MyLogger.info("Exception Caught::::");
		return this.generateFailureBaseGenericResponseEntity(businessException, this.determineErrorType(businessException.getErrorCode()));
	}
	protected ResponseEntity<BaseGenericResponse<T>> handleBaseGenericResponseException(BusinessException businessException,String locale) {
		MyLogger.info("Exception Caught::::");
		return this.generateFailureBaseGenericResponseEntity(businessException, this.determineErrorType(businessException.getErrorCode()),locale);
	}

	private HttpStatus determineErrorType(int errorCode) {
		MyLogger.info("Determine the Error Code:::"+errorCode);
		HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
		if(errorCode==ErrorCode.NO_DATA_FOUND.getCode())
		{
			httpStatus= HttpStatus.BAD_REQUEST;
		}
		else if(errorCode==ErrorCode.OPERATION_NOT_PERFORMED.getCode())
		{
			httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return httpStatus;
	}

	protected ResponseEntity<BaseGenericResponse<T>> generateFailureBaseGenericResponseEntity(
			GeneralException exception, HttpStatus httpStatus) {
		
		ResponseEntity<BaseGenericResponse<T>> responseEntity = new ResponseEntity<BaseGenericResponse<T>>(
				this.generateBaseGenericResponseFailure(exception.getErrorCode(), exception.getErrorMessage()),
				exception.getHttpStatus()!=null?exception.getHttpStatus():httpStatus);
		return responseEntity;
	}

	protected ResponseEntity<BaseGenericResponse<T>> generateFailureBaseGenericResponseEntity(
			GeneralException exception, HttpStatus httpStatus,String locale) {
		String errorMessage="";
		if(StringUtility.isStringPopulated(locale))
		{
			if(StringUtility.stringsMatch(locale, StringUtility.ENGLISH))
			{
				errorMessage=exception.getErrorMessage();
			}
			else if(StringUtility.stringsMatch(locale, StringUtility.ARABIC))
			{
				errorMessage=exception.getErrorMessageAr();
			}
		}
		else
		{
			errorMessage=exception.getErrorMessage();
		}
		ResponseEntity<BaseGenericResponse<T>> responseEntity = new ResponseEntity<BaseGenericResponse<T>>(
				this.generateBaseGenericResponseFailure(exception.getErrorCode(),errorMessage),exception.getHttpStatus()!=null?exception.getHttpStatus():httpStatus);
		return responseEntity;
	}

	public BaseGenericResponse<T> generateBaseGenericResponseFailure(int errorCode, String errorMessage) {
		MyLogger.info("Generating the Base Response Failure using :::"+errorCode+" and " +errorMessage);
		BaseGenericResponse<T> baseGenericResponse = new BaseGenericResponse<T>();
		baseGenericResponse.setMessage(errorMessage);
		baseGenericResponse.setStatus(errorCode);
		baseGenericResponse.setTransactionId(CurrentRequestHolder.get().getSystemTrx());
		return baseGenericResponse;

	}

	protected ResponseEntity<BaseGenericResponse<T>> generateBaseGenericResponse(Class<T> clazz, T data,
			List<T> dataList,PaginatedEntity<T> paginatedList) throws BusinessException {
		
		BaseGenericResponse<T> baseGenericResponse = new BaseGenericResponse<T>();
		String singleAnnotation=clazz.getAnnotation(CustomJsonRootName.class).singular();
		String pluralAnnotation=clazz.getAnnotation(CustomJsonRootName.class).plural();
		
		if (data != null) {
			Map<String, T> object=new HashMap<String, T>();
			object.put(singleAnnotation, data);
			//baseGenericResponse.setObject(object);
			baseGenericResponse.setResponse(object);
			MyLogger.info("Response"+object.toString());
			
		} else if (listUtility.isListPopulated(dataList)) {
			Map<String, List<T>> result = new HashMap<String, List<T>>();
			result.put(pluralAnnotation, dataList);
			//baseGenericResponse.setResult(result);
			baseGenericResponse.setResponse(result);
			MyLogger.info("Response"+result.toString());
		}
		else if (this.listUtility.isPaginatedListPopulated(paginatedList)) 
		{
			
			Map<String, PaginatedEntity<T>> page=new HashMap<String, PaginatedEntity<T>>();
			page.put(pluralAnnotation, paginatedList);
			baseGenericResponse.setResponse(page);
			MyLogger.info("Response"+page.toString());
		}
		
		
		else if(data==null&&(listUtility.isListEmptyOrNull(dataList))&&(listUtility.isPaginatedListEmpty(paginatedList)))
		{
			MyLogger.info("No data found:::" + baseGenericResponse.toString());
			MyLogger.info("Base Response:::" + baseGenericResponse.toString());
			throw new BusinessException(ErrorCode.NO_DATA_FOUND);
		}
		
		MyLogger.info("Base Response:::" + baseGenericResponse.toString());

		MyLogger.info("Data found:::" );
		return new ResponseEntity<BaseGenericResponse<T>>(
				this.constructGenericBaseResponseCodeAndMessage(baseGenericResponse),
				this.validateGenericResponseSuccess(baseGenericResponse));

	}
	
	
	protected BusinessUser getCurrentRequestHolder(String token) throws BusinessException
	{
		BusinessUser businessUser =CurrentRequestHolder.get();
				//this.jwtUtil.getBusinessUserFromToken(StringUtility.generateSubStringStartingFromCertainIndex(token,' '));
		if(businessUser!=null)
		{
			MyLogger.info("Current Request Holder::"+businessUser.getUserPhone());
		}
		
		return businessUser;		
	}
	
	
	protected ResponseEntity<BaseGenericResponse<T>> handleBaseGenericResponseException(Exception exception) {
		MyLogger.info("Exception Caught::::");
		if(exception instanceof MultipartException ||exception instanceof MaxUploadSizeExceededException|| exception instanceof SizeLimitExceededException || exception instanceof IllegalStateException)
		return this.generateFailureBaseGenericResponseEntity(new BusinessException(ErrorCode.FILE_TOO_BIG),HttpStatus.BAD_REQUEST);
		else 
			return null;
	} 

	protected ResponseEntity<BaseGenericResponse<T>> handleBaseGenericResponseException(Exception exception,String locale) {
		MyLogger.info("Exception Caught::::");
		if(exception instanceof MultipartException ||exception instanceof MaxUploadSizeExceededException|| exception instanceof SizeLimitExceededException || exception instanceof IllegalStateException)
		return this.generateFailureBaseGenericResponseEntity(new BusinessException(ErrorCode.FILE_TOO_BIG),HttpStatus.BAD_REQUEST,locale);
		else 
			return null;
	} 
}
