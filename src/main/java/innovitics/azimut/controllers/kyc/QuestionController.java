package innovitics.azimut.controllers.kyc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessUserType;
import innovitics.azimut.businessservices.BusinessQuestionService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;

@RestController
@RequestMapping("/api/kyc/question")

public class QuestionController extends BaseGenericRestController<BusinessQuestion, String>{

@Autowired BusinessQuestionService businessQuestionService;
	
	@PostMapping(value="/getQuestionById",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessQuestion>> getQuestionById(@RequestBody BusinessQuestion businessQuestion) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessQuestion.class,this.businessQuestionService.getById(businessQuestion.getId()),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/getQuestionByPageId",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessQuestion>> getQuestionByPageId(@RequestBody BusinessKYCPage businessKYCPage) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessQuestion.class,null,this.businessQuestionService.getByPageId(businessKYCPage.getId()), null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
}
