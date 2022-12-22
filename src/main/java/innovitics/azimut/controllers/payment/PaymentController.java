package innovitics.azimut.controllers.payment;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.businessservices.BusinessPaymentService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;

@RestController
@RequestMapping("/api/payment")
public class PaymentController extends BaseGenericRestController<BusinessPayment> {

	@Autowired BusinessPaymentService businessPaymentService;
	
	
	@PostMapping(value="/initiatePayment",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessPayment>> initiatePayment(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessPayment businessPayment) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessPayment.class,this.businessPaymentService.initiatePayment(businessPayment,this.getCurrentRequestHolder(token),language,BooleanUtility.getValue(businessPayment.getIsMobile())),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	@PostMapping(value="/queryPayment",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessPayment>> queryPayment(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BusinessPayment businessPayment) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessPayment.class,this.businessPaymentService.queryPayment(businessPayment,this.getCurrentRequestHolder(token),language,BooleanUtility.getValue(businessPayment.getIsMobile())),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	
}
