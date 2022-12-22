package innovitics.azimut.controllers.payment;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import innovitics.azimut.businessmodels.payment.PaytabsCallbackRequest;
import innovitics.azimut.businessservices.BusinessPaymentService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.logging.MyLogger;

@Controller
@RequestMapping("/api/paytabs")
public class PaymentCallbackController extends BaseGenericRestController<PaytabsCallbackRequest> {
	@Autowired BusinessPaymentService businessPaymentService;

	@PostMapping(value="/callback",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, 
						MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<PaytabsCallbackRequest>> callback(
			@RequestHeader(name="Signature",required=false) String  signature,
			@RequestParam(StringUtility.TRANSACTION_SERIAL_PARAM_NAME) String serial,
			@RequestBody PaytabsCallbackRequest paytabsCallbackRequest
			) throws BusinessException {
		try
		{
			return this.generateBaseGenericResponse(PaytabsCallbackRequest.class,this.businessPaymentService.updateTransactionAfterGatewayCallback(paytabsCallbackRequest,serial),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}

	@PostMapping(value="/instantCallback",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, 
						MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<PaytabsCallbackRequest>> instantCallback(
			@RequestHeader(name="Signature",required=false) String  signature,
			@RequestBody PaytabsCallbackRequest paytabsCallbackRequest
			) throws BusinessException {
		try
		{
			MyLogger.info("Signature:::"+signature);
			return this.generateBaseGenericResponse(PaytabsCallbackRequest.class,this.businessPaymentService.updateTransactionAfterGatewayCallback(paytabsCallbackRequest,null),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}

}
