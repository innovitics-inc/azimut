package innovitics.azimut.businessservices;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import innovitics.azimut.AzimutParent;
import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessutilities.BusinessSearchCriteria;
import innovitics.azimut.businessutilities.SearchFilter;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.BaseInput;
import innovitics.azimut.rest.entities.BaseOutput;
import innovitics.azimut.rest.mappers.RestMapper;
import innovitics.azimut.security.AES;
import innovitics.azimut.services.kyc.UserTypeService;
import innovitics.azimut.services.payment.PaymentService;
import innovitics.azimut.services.user.UserService;
import innovitics.azimut.utilities.businessutilities.BusinessSearchOperation;
import innovitics.azimut.utilities.businessutilities.PaymentTransactionUtility;
import innovitics.azimut.utilities.businessutilities.PhoneNumberBlockageUtility;
import innovitics.azimut.utilities.businessutilities.UserBlockageUtility;
import innovitics.azimut.utilities.businessutilities.UserUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.dbutilities.DatabaseConditions;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.fileutilities.SecureStorageService;
import innovitics.azimut.utilities.logging.FileUtility;
import innovitics.azimut.utilities.mapping.UserMapper;
import innovitics.azimut.validations.Validation;

@SuppressWarnings("rawtypes")
@Service
public abstract class AbstractBusinessService <T extends BaseBusinessEntity> extends AzimutParent {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractBusinessService.class);
	@Autowired
	protected ExceptionHandler exceptionHandler;

	@Autowired ListUtility<T> listUtility;
	@Autowired Validation<T> validation;
	@Autowired BlobFileUtility blobFileUtility;
	@Autowired SecureStorageService storageService;
	@Autowired protected UserMapper userMapper;
	@Autowired protected FileUtility fileUtility;
	@Autowired protected UserUtility userUtility;
	@Autowired protected PaymentService paymentService;
	@Autowired protected UserTypeService userTypeService;
	@Autowired protected UserBlockageUtility userBlockageUtility;
	@Autowired protected PhoneNumberBlockageUtility phoneNumberBlockageUtility;
	@Autowired protected PaymentTransactionUtility paymentTransactionUtility;
	@Autowired protected UserService userService;
	@Autowired AES aes;
	
	
	protected  BusinessException handleBusinessException(Exception exception,ErrorCode errorCode)
	{
		if (this.exceptionHandler.isNonTechnicalException(exception, errorCode))
			return this.exceptionHandler.handleAsBusinessException(exception, errorCode);
		
		else
		return this.exceptionHandler.handleAsBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
	}
	

	
	public DatabaseConditions generateDatabaseConditions(BusinessSearchCriteria businessSearchCriteria)
	{
		DatabaseConditions databaseConditions=new DatabaseConditions();
		if(businessSearchCriteria!=null)
		{
			if(businessSearchCriteria.getPageNumber()!=null&&businessSearchCriteria.getPageSize()!=null)
			{
				databaseConditions.setPageRequest(PageRequest.of(businessSearchCriteria.getPageNumber()-1,businessSearchCriteria.getPageSize()));
			}
			if(businessSearchCriteria.getPageNumber()!=null&&businessSearchCriteria.getPageSize()!=null
					&&businessSearchCriteria.getAsc()!=null&&
					StringUtility.isStringPopulated(businessSearchCriteria.getSortingParam()))
			{
				if(businessSearchCriteria.getAsc())
				{
					Sort sort = Sort.by(businessSearchCriteria.getSortingParam()).ascending();
					databaseConditions.setPageRequest(PageRequest.of(businessSearchCriteria.getPageNumber()-1,businessSearchCriteria.getPageSize(),sort));					
				}
				else
				{
					Sort sort = Sort.by(businessSearchCriteria.getSortingParam()).descending();
					databaseConditions.setPageRequest(PageRequest.of(businessSearchCriteria.getPageNumber()-1,businessSearchCriteria.getPageSize(),sort));					
				}				
			}
			if(this.arrayUtility.isArrayPopulated(businessSearchCriteria.getSearchesAndFilters()))
			{
				List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
				
				for (int i=0;i<businessSearchCriteria.getSearchesAndFilters().length;i++)
				{
					SearchFilter searchFilter=businessSearchCriteria.getSearchesAndFilters()[i];
					
					if (!StringUtility.isStringPopulated(searchFilter.getParentColumn())) 
					{

						if (searchFilter.getOperation() != null && searchFilter.getOperation()==BusinessSearchOperation.SEARCH.getOperation()) {
							searchCriteriaList.add(new SearchCriteria(searchFilter.getKey(), searchFilter.getValue(),
									SearchOperation.LIKE, null));
						} else if (searchFilter.getOperation() != null && searchFilter.getOperation()==BusinessSearchOperation.FILTER.getOperation()) {
							searchCriteriaList.add(new SearchCriteria(searchFilter.getKey(), this.arrayUtility.generateObjectListFromObjectArray(searchFilter.getValues()),
									SearchOperation.IN, null));
						}
					}
					else
					{

						if (searchFilter.getOperation() != null && searchFilter.getOperation()==BusinessSearchOperation.SEARCH.getOperation()) {
							searchCriteriaList.add(new SearchCriteria(searchFilter.getKey(), searchFilter.getValue(),
									SearchOperation.PARENT_LIKE, searchFilter.getParentColumn()));
						} else if (searchFilter.getOperation() != null && searchFilter.getOperation()==BusinessSearchOperation.FILTER.getOperation()) {
							searchCriteriaList.add(new SearchCriteria(searchFilter.getKey(), this.arrayUtility.generateObjectListFromObjectArray(searchFilter.getValues()),
									SearchOperation.PARENT_IN, searchFilter.getParentColumn()));
						}
					}
					
				}
				databaseConditions.setSearchCriteria(searchCriteriaList);				
			}
		}
		
		
		return databaseConditions;
	}

	
	protected void validate(T businessEntity, Validator validator,String objectName) throws BusinessException {
		this.validation.validate(businessEntity, validator,objectName);	
	}
	
	 protected Long getAzimutUserTypeId(BusinessUser businessUser) throws BusinessException
	  {
		  try 
		  {
			  return businessUser.getAzimutIdTypeId();
		  }
		  catch(Exception exception)
		  {
			  this.exceptionHandler.getNullIfNonExistent(exception);
			  
		  }
		return null;
	  }
	 
		protected BusinessFundPrice prepareBusinessFundPriceInput(Long teacomputerId)
		{
			BusinessFundPrice searchBusinessFundPrice = new BusinessFundPrice();
			searchBusinessFundPrice.setSearchFromDate(DateUtility.getCurrentDayMonthYear());
			searchBusinessFundPrice.setSearchToDate(DateUtility.getCurrentDayMonthYear());
			return searchBusinessFundPrice;
		}
		
		protected void execute(BusinessUser tokenizedBusinessUser,UserMapper userMapper,Object object, String methodName,Object[] parameters,Class<?>[] paramterTypes) throws BusinessException
		{
			{		
				this.userUtility.getValueUsingReflection(object,methodName,parameters,paramterTypes);						
			}			
		}
		
}
