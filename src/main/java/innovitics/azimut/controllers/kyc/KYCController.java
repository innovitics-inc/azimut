package innovitics.azimut.controllers.kyc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessUserAnswerSubmission;
import innovitics.azimut.businessmodels.kyc.BusinessUserType;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessClientDetailsService;
import innovitics.azimut.businessservices.BusinessKYCPageService;
import innovitics.azimut.businessservices.BusinessUserAnswerSubmissionService;
import innovitics.azimut.businessservices.BusinessUserService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.logging.MyLogger;

@RestController
@RequestMapping("/api/kyc/pages")
public class KYCController extends BaseGenericRestController<BusinessKYCPage> {

	@Autowired BusinessKYCPageService businessKYCPageService;
	@Autowired BusinessUserAnswerSubmissionService businessUserAnswerSubmissionService;
	@Autowired BusinessClientDetailsService businessClientDetailsService;

	
	@PostMapping(value="/getPagesByUserType",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> getPageByUserTypeId(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUserType businessUserType) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,this.businessKYCPageService.getAllPagesByUserTypeId(this.getCurrentRequestHolder(token)), null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}

	@PostMapping(value="/getPageById",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> getPageById(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessKYCPage businessKYCPage) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessKYCPage.class,this.businessKYCPageService.getKycPagebyId(this.getCurrentRequestHolder(token),businessKYCPage.getId(),businessKYCPage.getIsWeb(),language),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	@PostMapping(value="/getPagesByUserId",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> getPagesByUserId(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessKYCPage businessKYCPage) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,this.businessKYCPageService.getUserKycPages(this.getCurrentRequestHolder(token),businessKYCPage.getIsWeb(),language), null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	@PostMapping(value="/submitAnswers",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> submitAnswer(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException, IOException, IntegrationException {
	
		BusinessKYCPage businessKYCPage=new BusinessKYCPage();
		
		try
		{
			businessKYCPage=this.businessUserAnswerSubmissionService.submitAnswers(this.getCurrentRequestHolder(token), businessUserAnswerSubmission);
			
			if(!NumberUtility.areLongValuesMatching(businessUserAnswerSubmission.getNextPageId(),businessUserAnswerSubmission.getPageId())&&BooleanUtility.isTrue(businessUserAnswerSubmission.getIsMobile()))
			{
				
			  int verificationPercentage=businessKYCPage!=null&&businessKYCPage.getVerificationPercentage()!=null?businessKYCPage.getVerificationPercentage().intValue():0;
			  businessKYCPage=this.businessKYCPageService.getKycPagebyId(this.getCurrentRequestHolder(token),businessUserAnswerSubmission.getNextPageId(),false,language);
			  businessKYCPage.setVerificationPercentage(verificationPercentage);
			}
			if(NumberUtility.areLongValuesMatching(businessUserAnswerSubmission.getNextPageId(),businessUserAnswerSubmission.getPageId())&&BooleanUtility.isTrue(businessUserAnswerSubmission.getIsMobile()))
			{
				businessKYCPage.setNextId(businessUserAnswerSubmission.getNextPageId());
				businessKYCPage.setId(businessUserAnswerSubmission.getNextPageId());
			}
			
			return this.generateBaseGenericResponse(BusinessKYCPage.class,businessKYCPage,null, null);
			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	
	
	
	@PostMapping(value="/saveClientBankAccountDetailsTemporarily",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> saveClientBankAccounts(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessAzimutClient businessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
			BusinessUser businessUser=this.getCurrentRequestHolder(token);
			BusinessKYCPage	businessKYCPage=new BusinessKYCPage();

			this.businessClientDetailsService.saveClientBankAccounts(businessAzimutClient,businessUser);
			
			if(BooleanUtility.isTrue(businessAzimutClient.getIsMobile()))
			{
				businessKYCPage=this.businessKYCPageService.getKycPagebyId(businessUser,businessUser.getFirstPageId(),false,language);
				
			}
			businessKYCPage.setVerificationPercentage(this.businessKYCPageService.adjustProgress(null, businessUser));
			
			return this.generateBaseGenericResponse(BusinessKYCPage.class,businessKYCPage,null, null);

		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
	}

	
	@PostMapping(value="/uploadFile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> uploadDocument(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			Long answerId,MultipartFile file) throws BusinessException, IOException, IntegrationException {
		try
		{
			MyLogger.info("answerId:::"+answerId);
			return this.generateBaseGenericResponse(BusinessKYCPage.class,this.businessUserAnswerSubmissionService.uploadDocument(this.getCurrentRequestHolder(token),answerId,file),null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	
	@PostMapping(value="/deleteFile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> deleteDocument(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException, IOException, IntegrationException {
		try
		{
			this.businessUserAnswerSubmissionService.deleteDocument(businessUserAnswerSubmission.getDocumentSubDirectory(), businessUserAnswerSubmission.getDocumentName());
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	
	@PostMapping(value="/readFile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> readDocument(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException, IOException, IntegrationException {
		try
		{
			//this.businessUserAnswerSubmissionService.readDocument(businessUserAnswerSubmission.getDocumentSubDirectory(), businessUserAnswerSubmission.getDocumentName());
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	
}
