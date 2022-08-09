package innovitics.azimut.controllers.funds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.businessservices.BusinessFundsService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;

@RestController
@RequestMapping("/api/fund")
public class FundController extends BaseGenericRestController<BusinessFundPrice, String> {

	@Autowired BusinessFundsService businessFundsService;
	
	@PostMapping(value="/updateFundPrices",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessFundPrice>> updateFundPrices(@RequestBody BusinessFundPrice[] businessFundPrices) throws BusinessException, IOException, IntegrationException {
		try
		{
			List<BusinessFundPrice> business=new ArrayList<BusinessFundPrice>();
			 Collections.addAll(business, businessFundPrices);
			return this.generateBaseGenericResponse(BusinessFundPrice.class, null, this.businessFundsService.updateFundPrices(business), null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}

	
	
	
	
}
