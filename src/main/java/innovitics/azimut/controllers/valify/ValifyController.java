package innovitics.azimut.controllers.valify;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.businessservices.BusinessValifyService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.StringUtility;


@RestController
@RequestMapping("/api/valify")

public class ValifyController extends BaseGenericRestController<BusinessValify,String>{

	@Autowired 
	BusinessValifyService businessValifyService;
	
	@PostMapping(value="/valifyFacial",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessValify>> valifyFacial(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessValify businessValify) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessValify.class,businessValifyService.valifyFacial(this.getCurrentRequestHolder(token), businessValify,null,null,null,null,null,null),null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/valifyId",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessValify>> valifyId(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessValify businessValify) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessValify.class,businessValifyService.valifyId(this.getCurrentRequestHolder(token), businessValify,null,null,null,null,null,null,null),null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	
	@PostMapping(value="/valifyFacialForm",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessValify>> valifyFacialFormData(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestParam (name="straightFace",required=false) MultipartFile straightFace,
			@RequestParam (name="smilingFace",required=false)MultipartFile smilingFace,
			@RequestParam (name="leftSide",required=false) MultipartFile leftSide,
			@RequestParam (name="rightSide",required=false)MultipartFile rightSide,
			Integer userStep,String language) throws 
	MaxUploadSizeExceededException,IllegalStateException,BusinessException, IOException, IntegrationException  {
		try
		{
			return this.generateBaseGenericResponse(BusinessValify.class,businessValifyService.valifyFacial(this.getCurrentRequestHolder(token), null,straightFace,smilingFace,leftSide,rightSide,userStep,language),null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/valifyIdForm",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessValify>> valifyIdFormData(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestParam (name="frontImage",required=false) MultipartFile frontImage,
			@RequestParam (name="backImage",required=false)MultipartFile backImage,
			@RequestParam (name="passportImage",required=false) MultipartFile passportImage,
			@RequestParam (name="userStep",required=false)Integer userStep,
			@RequestParam (name="language",required=false)String language,
			@RequestParam (name="documentType",required=false)String documentType,
			@RequestParam (name="isWeb",required=false) Boolean isWeb
			) throws 
	MaxUploadSizeExceededException,IllegalStateException,BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessValify.class,businessValifyService.valifyId(this.getCurrentRequestHolder(token), null,frontImage,backImage,passportImage,userStep,language,documentType,isWeb),null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
}
