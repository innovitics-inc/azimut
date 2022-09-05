package innovitics.azimut.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
import innovitics.azimut.utilities.dbutilities.SearchOperation;

public abstract class AbstractRepository<T extends BaseEntity>{
	protected static final Logger logger = LogManager.getLogger(AbstractRepository.class);	
	@PersistenceContext
	public EntityManager entityManager;
	@Autowired ListUtility<T> listUtility;
	@Autowired ArrayUtility arrayUtility;
	@Autowired protected ConfigProperties configProperties;
	protected Query generateQuery(String queryString,Class<T> clazz,String... params)
	{
		logger.info("Query String:::"+queryString);
		logger.info("Parameters:::");
	
		
		Query query=entityManager.createNativeQuery(queryString,clazz);
		if(arrayUtility.isArrayPopulated(params))
		{
			for (int i=0;i<params.length;i++)
			{
				logger.info(params[i]);
				query.setParameter(i+1, params[i]);
			}
		}
		
	
		return query;		
	}
	
	String addPagination(Integer pageSize,Integer pageNumber)
	{
		StringBuffer paginationString=new StringBuffer("");
		if(pageSize!=null)
		{
			paginationString.append(" LIMIT "+String.valueOf(pageSize));
		}
		if(pageNumber!=null&&pageSize!=null)
		{
			paginationString.append(" OFFSET "+String.valueOf((pageNumber-1)*pageSize));
		}
		
		return paginationString.toString();
		
	}
	

	PaginatedEntity<T> createPaginatedEntity(Query query,Integer pageNumber, Integer pageSize)
	{
		List<T> dataList=new ArrayList<T>();
		
		PagedListHolder<T> pages = new PagedListHolder<T>(dataList);
		pages.setPage(pageNumber); //set current page number
		pages.setPageSize(pageSize); // set the size of page
		pages.getPageList();
		//return pages.getPageList();

		dataList=query.getResultList();
		
		PaginatedEntity<T> paginatedEntity=new PaginatedEntity<T>();
		
		Integer itemCount=null;
		Integer pageCount=null;
		boolean isLastPage=false;
		boolean isFirstpage=false;
		
		
		if(listUtility.isListPopulated(dataList))
			{
				itemCount=dataList.size();
				if(pageSize!=null&&pageSize.intValue()!=0)
				{
					pageCount=itemCount/pageSize;
				}
				
				if(pageCount!=null&&pageCount>0)
				{
					
				}
				
				
			}

		
		
		
		
		paginatedEntity.setCurrentPage(pageNumber);
		paginatedEntity.setPageSize(pageSize);
		
		//paginatedEntity.setNumberOfItems(itemCount);
		
		paginatedEntity.setNumberOfPages(pageCount);		
	
		
	
		paginatedEntity.setHasNext(false);
		
		paginatedEntity.setHasPrevious(false);
		
		paginatedEntity.setDataList(dataList);
		
		return paginatedEntity;
		
	}
	
	protected List<Object[]>  grouping (Class<T> clazz,String searchKey,String searchValue,String aggregatedKey,String aggregatorField,String aggregatorFieldValue)
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();  
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);  
		Root<? extends BaseEntity> root = criteriaQuery.from(clazz);
		
		criteriaQuery.multiselect(root.get(aggregatedKey),criteriaBuilder.max(root.get(aggregatedKey))).groupBy(root.get(aggregatorField));
		
		criteriaQuery.where(criteriaBuilder.equal(root.get(searchKey), searchValue));
		
		List<Object[]> list = this.entityManager.createQuery(criteriaQuery).getResultList();
		for(Object[] objectArray:list)
		{
			this.logger.info("Value1::"+objectArray[0].toString());
			this.logger.info("Value2::"+objectArray[1].toString());
		}
		
		return list;  
	}
	protected List<Object[]>  grouping (Class<T> clazz,String searchKey,String searchValue,String aggregatedKey,String aggregatorField,String aggregatorFieldValue,String joiningColumn,String[] selectedValues)
	{
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();  
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);  
		Root<? extends BaseEntity> root = criteriaQuery.from(clazz);
		Join<T, T> join = root.join(joiningColumn);
		criteriaBuilder.equal(join.get(searchKey),searchValue);
		this.setSelectedValues(criteriaBuilder,criteriaQuery);
		this.setWhereConditions(criteriaBuilder, criteriaQuery,searchKey,searchValue);
		List<Object[]> list = this.entityManager.createQuery(criteriaQuery).getResultList();
		
		return list;  
	}

	protected void setSelectedValues(CriteriaBuilder criteriaBuilder,CriteriaQuery<Object[]> criteriaQuery) {
		
	}
	protected void setWhereConditions(CriteriaBuilder criteriaBuilder,CriteriaQuery<Object[]> criteriaQuery,String searchKey,String searchValue)
	{
		
	}
	

}
