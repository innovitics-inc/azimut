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
import innovitics.azimut.utilities.datautilities.StringUtility;

@RestController
@RequestMapping("/api/kyc/pages")
public class KYCController extends BaseGenericRestController<BusinessKYCPage, String> {

	@Autowired BusinessKYCPageService businessKYCPageService;
	@Autowired BusinessUserAnswerSubmissionService businessUserAnswerSubmissionService;
	@Autowired BusinessClientDetailsService businessClientDetailsService;

	
	@PostMapping(value="/getPagesByUserType",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> getPageByUserTypeId(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessUserType businessUserType) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,this.businessKYCPageService.getAllPagesByUserTypeId(this.getCurrentRequestHolder(token)), null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}

	@PostMapping(value="/getPageById",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> getPageById(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessKYCPage businessKYCPage) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessKYCPage.class,this.businessKYCPageService.getKycPagebyId(this.getCurrentRequestHolder(token).getId(),businessKYCPage.getId(),businessKYCPage.getDraw()),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/submitAnswers",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> submitAnswer(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException, IOException, IntegrationException {
	
		BusinessKYCPage businessKYCPage=new BusinessKYCPage();
		
		try
		{
			businessKYCPage=this.businessUserAnswerSubmissionService.submitAnswers(this.getCurrentRequestHolder(token), businessUserAnswerSubmission);
			
			if(businessUserAnswerSubmission.getNextPageId()!=null)
			{
			  businessKYCPage=this.businessKYCPageService.getKycPagebyId(this.getCurrentRequestHolder(token).getId(),businessUserAnswerSubmission.getNextPageId(),businessKYCPage.getDraw());
			}
		  	
			businessKYCPage.setVerificationPercentage(this.businessKYCPageService.adjustProgress(businessKYCPage,this.getCurrentRequestHolder(token)));
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		return this.generateBaseGenericResponse(BusinessKYCPage.class,businessKYCPage,null, null);
		
	}
	
	
	
	@PostMapping(value="/saveClientBankAccountDetailsTemporarily",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> saveClientBankAccounts(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessAzimutClient businessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
		
			this.businessClientDetailsService.saveClientBankAccounts(businessAzimutClient, this.getCurrentRequestHolder(token));
			BusinessUser businessUser=this.getCurrentRequestHolder(token);
			
			BusinessKYCPage	businessKYCPage=this.businessKYCPageService.getKycPagebyId(businessUser.getId(),businessUser.getFirstPageId(),false);
			businessKYCPage.setVerificationPercentage(this.businessKYCPageService.adjustProgress(businessKYCPage, businessUser));
			return this.generateBaseGenericResponse(BusinessKYCPage.class,businessKYCPage,null, null);

		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
	}

	
	
	
	
	
	
	
	@PostMapping(value="/uploadFile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> uploadDocument(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,Long answerId,MultipartFile file) throws BusinessException, IOException, IntegrationException {
		try
		{
			this.logger.info("answerId:::"+answerId);
			return this.generateBaseGenericResponse(BusinessKYCPage.class,this.businessUserAnswerSubmissionService.uploadDocument(this.getCurrentRequestHolder(token),answerId,file),null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/deleteFile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> deleteDocument(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException, IOException, IntegrationException {
		try
		{
			this.businessUserAnswerSubmissionService.deleteDocument(businessUserAnswerSubmission.getDocumentSubDirectory(), businessUserAnswerSubmission.getDocumentName());
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/readFile",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessKYCPage>> readDocument(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException, IOException, IntegrationException {
		try
		{
			//this.businessUserAnswerSubmissionService.readDocument(businessUserAnswerSubmission.getDocumentSubDirectory(), businessUserAnswerSubmission.getDocumentName());
			return this.generateBaseGenericResponse(BusinessKYCPage.class,null,null, null);			
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
}
