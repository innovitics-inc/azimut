package innovitics.azimut.controllers.users;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.lang.IllegalStateException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;

import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.businessutilities.BusinessSearchCriteria;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.services.FundService;
import innovitics.azimut.services.user.UserDeviceService;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import org.springframework.web.multipart.MultipartException;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseGenericRestController<BusinessUser,String> {

	@Autowired BusinessUserService businessUserService;
	
	@Autowired BlobFileUtility blobFileUtility;
	@Autowired FundService fundService;
	@Autowired UserDeviceService userDeviceService;

	@PostMapping(value="/getById",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> getById(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			
			return this.generateBaseGenericResponse(BusinessUser.class, this.businessUserService.beautifyUser(businessUserService.getById(businessUser.getId(),this.getCurrentRequestHolder(token))), null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/getByUserPhone",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> getByUserPhone(@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,@RequestBody BusinessUser businessUser) throws BusinessException, IOException, IntegrationException {
		try
		{
			
			return this.generateBaseGenericResponse(BusinessUser.class, this.businessUserService.hideUserDetails(this.businessUserService.verifyUserExistence(businessUser)), null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}


	@PostMapping(value="/uploadSignedPdf",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> uploadSignedPdf(@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,@RequestParam ("id") Long id,@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestParam (name="file",required=false) MultipartFile file,
			@RequestParam ("newCountryPhoneCode") String newCountryPhoneCode,@RequestParam ("newPhoneNumber") String newPhoneNumber) throws BusinessException, IOException,MaxUploadSizeExceededException {
		try
		{
			this.logger.info("newCountryPhoneCode"+newCountryPhoneCode);
			this.logger.info("newPhoneNumber"+newPhoneNumber);
			return this.generateBaseGenericResponse(BusinessUser.class, 
					this.businessUserService.beautifyUser(this.businessUserService.uploadSignedPdf(id,newCountryPhoneCode,newPhoneNumber,file,this.getCurrentRequestHolder(token))), null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		catch(MaxUploadSizeExceededException maxUploadSizeExceededException)
		{
			return this.handleBaseGenericResponseException(maxUploadSizeExceededException);
		}
		catch(IllegalStateException illegalStateException)
		{
			return this.handleBaseGenericResponseException(illegalStateException);
		}
		
	}


	@PostMapping(value="/updateUserProfile",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> editUserProfile(@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,@RequestParam ("id") Long id,@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestParam (name="file",required=false) MultipartFile file,
			@RequestParam ("nickName") String nickName,@RequestParam  ("emailAddress")  String emailAddress) throws BusinessException,MaxUploadSizeExceededException,IllegalStateException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,
					this.businessUserService.beautifyUser (this.businessUserService.editUserProfile(id,file,nickName,emailAddress,this.getCurrentRequestHolder(token))),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		catch(MaxUploadSizeExceededException maxUploadSizeExceededException)
		{
			return this.handleBaseGenericResponseException(maxUploadSizeExceededException);
		}
		catch(IllegalStateException illegalStateException)
		{
			return this.handleBaseGenericResponseException(illegalStateException);
		}
		catch(MultipartException multipartException)
		{
			return this.handleBaseGenericResponseException(multipartException);
		}

		
	}


	@PostMapping(value="/updatePassword",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> editUserPassword(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.beautifyUser(this.businessUserService.editUserPassword(businessUser,this.getCurrentRequestHolder(token))),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}


	@PostMapping(value="/forgotPassword",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> forgotPassword(@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,@RequestBody BusinessUser businessUser) throws BusinessException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.beautifyUser(this.businessUserService.forgotUserPassword(businessUser)),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/downloadUnsignedPDF",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> downloadUnsignedPDF(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,
					this.businessUserService.beautifyUser (this.businessUserService.getUnsignedPDF(businessUser,this.getCurrentRequestHolder(token))),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}

	@PostMapping(value="/logUserStep",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> logUserStep(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.updateUserStep(this.getCurrentRequestHolder(token),businessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/getUserImages",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> getUserImages(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.getUserImages(this.getCurrentRequestHolder(token),businessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/saveClientDetails",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> saveClientDetails(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.updateUserDetails(this.getCurrentRequestHolder(token),businessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/saveContractMapChoice",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> saveContractMapChoice(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.updateUserDetails(this.getCurrentRequestHolder(token),businessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/saveUserTemporarily",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> saveUserTemporarily(@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,@RequestBody BusinessUser businessUser) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.saveUser(businessUser),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/saveUserLocation",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> saveUserLocation(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody UserLocation userLocation) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.addUserLocation(userLocation,this.getCurrentRequestHolder(token)),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@GetMapping(value="/getUserLocation",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> getUserLocation(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws BusinessException, IOException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.getUserLocation(this.getCurrentRequestHolder(token)),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@GetMapping(value="/downloadContract",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> downloadContract(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		try
		{
			return this.generateBaseGenericResponse(BusinessUser.class,this.businessUserService.downloadUserContract(this.getCurrentRequestHolder(token)),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@GetMapping(value="/execute",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> execute(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException {
		//this.executor.execute();
		return null;
		
	}
	@GetMapping(value="/download",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> download(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		//this.executor.downloadLoop();
		return null;
		
	}
	@GetMapping(value="/deleteAll",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> deleteAll(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		this.blobFileUtility.deleteAllFilesFromBlob(this.configProperties.getBlobKYCDocumentsContainer(),"Users");
		return null;
		
	}

	@GetMapping(value="/listBlobs",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessUser>> listBlobs(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		//this.executor.listBlobs();
		return null;
		
	}
	
	

	@GetMapping(value="/getPrices",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
		protected ResponseEntity<BaseGenericResponse<BusinessUser>> getFundPrices(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		
		BusinessUser businessUser=new BusinessUser();
		
		//businessUser.setFundPrices(this.fundService.getAllFundPrices());
		
		return this.generateBaseGenericResponse(BusinessUser.class, businessUser,null, null);
		
		
	}
	
}

