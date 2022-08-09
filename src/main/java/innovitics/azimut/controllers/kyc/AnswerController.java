package innovitics.azimut.controllers.kyc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.kyc.BusinessAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessAnswerService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;

@RestController
@RequestMapping("/api/kyc/answer")
public class AnswerController extends BaseGenericRestController<BusinessAnswer, String>{

	
	@Autowired BusinessAnswerService businessAnswerService;
	
	@PostMapping(value="/getAnswerById",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAnswer>> getAnswerById(@RequestBody BusinessAnswer businessAnswer) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAnswer.class,this.businessAnswerService.getById(businessAnswer.getId()),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	
	@PostMapping(value="/getAnswersByQuestionId",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAnswer>> getAnswersByQuestionId(@RequestBody BusinessQuestion businessQuestion) throws BusinessException, IOException, IntegrationException {
		try
		{
			this.logger.info("Enter here");
			return this.generateBaseGenericResponse(BusinessAnswer.class,null,this.businessAnswerService.getAnswersByQuestion(businessQuestion.getId()), null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}

	
	
}
