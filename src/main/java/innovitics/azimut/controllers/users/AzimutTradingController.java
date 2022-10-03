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
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessservices.BusinessAzimutTradingService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.StringUtility;

@RestController
@RequestMapping("/api/azimut/trading")
public class AzimutTradingController extends BaseGenericRestController<BaseAzimutTrading, String> {

	
	@Autowired BusinessAzimutTradingService businessAzimutTradingService;
	
	@PostMapping(value="/placeOrder",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BaseAzimutTrading>> placeOrder(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BaseAzimutTrading baseAzimutTrading) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BaseAzimutTrading.class,this.businessAzimutTradingService.placeOrder(this.getCurrentRequestHolder(token),baseAzimutTrading),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
	}
	

	@PostMapping(value="/inject",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BaseAzimutTrading>> inject(
			@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestParam ("orderValue") Double orderValue,
			@RequestParam ("currencyId") Long currencyId,
			@RequestParam ("accountNo") String accountNo,
			@RequestParam (name="file",required=false) MultipartFile file) throws BusinessException,MaxUploadSizeExceededException,IllegalStateException, IOException, IntegrationException {
		try
		{
			BaseAzimutTrading inputBaseAzimutTrading=new BaseAzimutTrading();
			inputBaseAzimutTrading.setOrderValue(orderValue);
			inputBaseAzimutTrading.setCurrencyId(currencyId);
			inputBaseAzimutTrading.setAccountNo(accountNo);
			inputBaseAzimutTrading.setInjectionDocument(file);
			
			return this.generateBaseGenericResponse(BaseAzimutTrading.class,this.businessAzimutTradingService.inject(this.getCurrentRequestHolder(token),inputBaseAzimutTrading),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		catch(MaxUploadSizeExceededException maxUploadSizeExceededException)
		{
			return this.handleBaseGenericResponseException(maxUploadSizeExceededException,language);
		}
		catch(IllegalStateException illegalStateException)
		{
			return this.handleBaseGenericResponseException(illegalStateException,language);
		}
		catch(MultipartException multipartException)
		{
			return this.handleBaseGenericResponseException(multipartException,language);
		}

		
	}
	
	
	
	
	
	
	@PostMapping(value="/withdraw",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BaseAzimutTrading>> withdraw(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
			@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language,
			@RequestBody BaseAzimutTrading baseAzimutTrading) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BaseAzimutTrading.class,this.businessAzimutTradingService.withdraw(this.getCurrentRequestHolder(token),baseAzimutTrading),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
	}
	
	
	@GetMapping(value="/incrementUserBlockage",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BaseAzimutTrading>> incrementUserBlockage(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		try
		{
			return this.generateBaseGenericResponse(BaseAzimutTrading.class,this.businessAzimutTradingService.incrementUserBlockage(this.getCurrentRequestHolder(token)),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
	
	@GetMapping(value="/getUserBlockage",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BaseAzimutTrading>> getUserBlockage(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestHeader(name=StringUtility.LANGUAGE,required=false) String  language) throws IOException, BusinessException {
		try
		{
			return this.generateBaseGenericResponse(BaseAzimutTrading.class,this.businessAzimutTradingService.getUserBlockage(this.getCurrentRequestHolder(token)),null,null);
		}
		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException,language);
		}
		
	}
}
