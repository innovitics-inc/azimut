package innovitics.azimut.controllers.users;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
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
	
	@PostMapping(value="/PlaceOrder",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BaseAzimutTrading>> removeClientBankAccount(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,
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
}
