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
import innovitics.azimut.businessutilities.BusinessSearchCriteria;
import innovitics.azimut.businessutilities.SearchFilter;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.services.kyc.UserTypeService;
import innovitics.azimut.utilities.businessutilities.BusinessSearchOperation;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.datautilities.UserUtility;
import innovitics.azimut.utilities.dbutilities.DatabaseConditions;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.fileutilities.FileUtility;
import innovitics.azimut.validations.Validation;

@Service
public abstract class AbstractBusinessService <T extends BaseBusinessEntity> extends AzimutParent {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractBusinessService.class);
	@Autowired
	protected ExceptionHandler exceptionHandler;

	@Autowired ListUtility<T> listUtility;
	@Autowired Validation<T> validation;
	@Autowired BlobFileUtility blobFileUtility;
	@Autowired protected FileUtility fileUtility;
	@Autowired protected UserUtility userUtility;
	@Autowired protected UserTypeService userTypeService;


	protected  BusinessException handleBusinessException(Exception exception,ErrorCode errorCode)
	{
		if (this.exceptionHandler.isNonTechnicalException(exception, errorCode))
			return this.exceptionHandler.handleAsBusinessException(exception, errorCode);
		
		else
		return this.exceptionHandler.handleAsBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
	}
	

	
	protected  BusinessException handleIntegrationException(IntegrationException integrationException,ErrorCode errorCode)
	{
		this.logger.info("Handling Exception as an Integration Exception::::"+integrationException.getMessage());
		return this.exceptionHandler.handleIntegrationExceptionAsBusinessException(integrationException, errorCode);
	}
	
	protected  BusinessException handleBusinessExceptionAsIs(Exception exception,ErrorCode errorCode)
	{
			this.logger.info("Handling Exception as Is"+exception.getMessage());
			return this.exceptionHandler.handleAsBusinessException(exception, errorCode);
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

}
