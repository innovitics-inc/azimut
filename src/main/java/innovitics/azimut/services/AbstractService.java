package innovitics.azimut.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import innovitics.azimut.AzimutParent;
import innovitics.azimut.businessutilities.BusinessSearchCriteria;
import innovitics.azimut.businessutilities.SearchFilter;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.businessutilities.BusinessSearchOperation;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.dbutilities.DatabaseConditions;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.UserSpecification;
	@Service
	public abstract class AbstractService <T extends BaseEntity,S> extends AzimutParent {
		
		protected static final Logger logger = LogManager.getLogger(AbstractService.class);
		PaginatedEntity<T> createPaginatedEntity(List<T> dataList,Integer pageNumber, Integer pageSize)
		{
			PagedListHolder<T> pages = new PagedListHolder<T>(dataList);
			pages.setPage(pageNumber); //set current page number
			pages.setPageSize(pageSize); // set the size of page
			pages.getPageList();		
			PaginatedEntity<T> paginatedEntity=new PaginatedEntity<T>();
			
			paginatedEntity.setCurrentPage(pageNumber);
			paginatedEntity.setPageSize(pageSize);
			paginatedEntity.setNumberOfPages(pages.getPageCount());	
			paginatedEntity.setHasNext(!pages.isLastPage());
			paginatedEntity.setHasPrevious(!pages.isFirstPage());
			paginatedEntity.setDataList(dataList);
			
			return paginatedEntity;
			
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
		
		
		public DatabaseConditions generateDatabaseConditions(SearchFilter[] searchesAndFilters)
		{
			DatabaseConditions databaseConditions=new DatabaseConditions();

				if(this.arrayUtility.isArrayPopulated(searchesAndFilters))
				{
					List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
					
					for (int i=0;i<searchesAndFilters.length;i++)
					{
						SearchFilter searchFilter=searchesAndFilters[i];
						
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
			
			return databaseConditions;
		}
		

}
