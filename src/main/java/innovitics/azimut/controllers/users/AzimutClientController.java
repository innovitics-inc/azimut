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
import org.springframework.web.bind.annotation.RestController;

import innovitics.azimut.businessmodels.user.AzimutAccount;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessAzimutDataLookup;
import innovitics.azimut.businessmodels.user.BusinessClientBankAccountDetails;
import innovitics.azimut.businessservices.BusinessClientDetailsService;
import innovitics.azimut.controllers.BaseGenericResponse;
import innovitics.azimut.controllers.BaseGenericRestController;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.utilities.datautilities.StringUtility;
@RestController
@RequestMapping("/api/azimut/user")
public class AzimutClientController extends BaseGenericRestController<BusinessAzimutClient ,String>{

	@Autowired BusinessClientDetailsService businessClientDetailsService;

	@PostMapping(value="/getAzimutClientBalanceAndTransactions",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> getAzimutClientBalanceAndTransactions(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessAzimutClient searchBusinessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
			this.logger.info("SearchBusinessAzmiutClient::"+searchBusinessAzimutClient);
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.getBalanceAndTransactions(searchBusinessAzimutClient,this.getCurrentRequestHolder(token)),null, null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@GetMapping(value="/getTemporaryAzimutClientBankAccounts",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> getTemporaryAzimutClientBankAccounts(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.getTemporaryClientBankAccountDetails(this.getCurrentRequestHolder(token)),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	
	@PostMapping(value="/getAzimutClientBankAccounts",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> getAzimutClientBankAccounts(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessAzimutClient searchBusinessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
			this.logger.info("SearchBusinessAzmiutClient::"+searchBusinessAzimutClient);
			Long accountId=(searchBusinessAzimutClient!=null&&searchBusinessAzimutClient.getAccountId()!=null)?searchBusinessAzimutClient.getAccountId().longValue():null;
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.getBankAccountsWithDetails(searchBusinessAzimutClient,this.getCurrentRequestHolder(token),accountId==null),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/checkAccount",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> checkAzimutAccount(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessAzimutClient searchBusinessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.checkAccountAtTeaComputers(searchBusinessAzimutClient,this.getCurrentRequestHolder(token)),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/saveTeacomputersAccountData",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> saveTeaComputersAccountData(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody AzimutAccount azimutAccount) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.saveTeaComputersAccountData(azimutAccount,this.getCurrentRequestHolder(token)),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/addAccount",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> addAccountAtTeaComputers(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody AzimutAccount azimutAccount) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.addAccountAtTeaComputers(azimutAccount,this.getCurrentRequestHolder(token)),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	@PostMapping(value="/getAzimutLookUpData",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> getAzimutLookupData(@RequestBody BusinessAzimutDataLookup businessAzimutDataLookup) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.getAzimutLookupData(businessAzimutDataLookup,null),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/synchronizeTeaComputersData",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> getTeaComputersLookupData(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessAzimutClient businessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.synchronizeTeaComputersLookupData(businessAzimutClient, this.getCurrentRequestHolder(token)),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/saveClientBankAccounts",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> saveClientBankAccounts(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessAzimutClient businessAzimutClient) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.saveClientBankAccounts(businessAzimutClient, this.getCurrentRequestHolder(token)),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
	
	@PostMapping(value="/removeClientBankAccount",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	protected ResponseEntity<BaseGenericResponse<BusinessAzimutClient>> removeClientBankAccount(@RequestHeader(StringUtility.AUTHORIZATION_HEADER) String  token,@RequestBody BusinessClientBankAccountDetails businessClientBankAccountDetails) throws BusinessException, IOException, IntegrationException {
		try
		{
			return this.generateBaseGenericResponse(BusinessAzimutClient.class,this.businessClientDetailsService.removeClientBankAccount(businessClientBankAccountDetails),null,null);
		}		
		catch(BusinessException businessException)
		{
			return this.handleBaseGenericResponseException(businessException);
		}
		
	}
}
