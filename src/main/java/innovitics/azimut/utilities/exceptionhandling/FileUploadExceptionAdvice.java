package innovitics.azimut.utilities.exceptionhandling;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.AbstractBusinessService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.utilities.fileutilities.MyLogger;

@ControllerAdvice
public class FileUploadExceptionAdvice {
	protected static final Logger logger = LoggerFactory.getLogger(FileUploadExceptionAdvice.class);
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<BaseGenericResponse<BusinessUser>> handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request, HttpServletResponse response){
    	MyLogger.info("File was found to be too big:::");
    	BaseGenericResponse<BusinessUser> baseGenericResponse=new BaseGenericResponse<BusinessUser>();
    	baseGenericResponse.setStatus(ErrorCode.FILE_TOO_BIG.getCode());
    	baseGenericResponse.setMessage(ErrorCode.FILE_TOO_BIG.getMessage());
    	return new ResponseEntity<BaseGenericResponse<BusinessUser>>(baseGenericResponse, HttpStatus.BAD_REQUEST);
    }
}