package innovitics.azimut.utilities.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessutilities.BusinessSearchCriteria;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;

@Component
public abstract class Mapper <S extends BaseEntity, T extends BaseBusinessEntity> extends ParentUtility
{
	
	@Autowired protected ListUtility<S> baseListUtility;
	@Autowired protected ListUtility<T> businessListUtility;
	protected static final Logger logger = LoggerFactory.getLogger(Mapper.class);

	protected  abstract S convertBusinessUnitToBasicUnit(T t , boolean save);
	
	protected  abstract T convertBasicUnitToBusinessUnit(S s);
	protected  abstract T convertBasicUnitToBusinessUnit(S s,String language);

	
	/*
	public T convertBusinessDatesToBasicDates(S baseEntity,T baseBusinessEntity,boolean save)
	{
		Date creationDate=baseEntity.getCreatedAt();
		baseEntity.setCreatedAt(save?new Date():creationDate);
		baseEntity.setUpdatededAt(new Date());
		return baseBusinessEntity;
		
	}
	
	public S convertBasicDatesToBusinessDates(S baseEntity,T baseBusinessEntity)
	{
		baseBusinessEntity.setCreatedAt(baseEntity.getCreatedAt());
		baseBusinessEntity.setUpdatededAt(baseEntity.getUpdatededAt());
		return baseEntity;
		
	}
	
	public T convertBusinessDatesToBusinessDates(T businessEntity,T oldBusinessEntity,boolean save)
	{
		if(businessEntity!=null)
		{
		if(businessEntity.getCreatedAt()!=null)
		oldBusinessEntity.setCreatedAt(businessEntity.getCreatedAt());
		if(businessEntity.getDeletedAt()!=null)
		oldBusinessEntity.setDeletedAt(businessEntity.getDeletedAt());
		if(businessEntity.getUpdatededAt()!=null)
		oldBusinessEntity.setUpdatededAt(new Date());
		}
		return oldBusinessEntity;
		
	}
	*/
	public List<S> convertBusinessListToBasicList(List<T> businessEntities, boolean save) {
		List<S> entities = new ArrayList<S>();

		if (checkIfBusinessBasicListIsPopulated(businessEntities)) {
			for (T businessEntity : businessEntities) {
				entities.add(convertBusinessUnitToBasicUnit(businessEntity, save));
			}
		}

		return entities;
	}

	public List<T> convertBasicListToBusinessList(List<S> baseEntities) {
		List<T> businessEntities = new ArrayList<T>();

		if (checkIfBasicListIsPopulated(baseEntities)) {
			for (S baseEntity : baseEntities) {
				businessEntities.add(convertBasicUnitToBusinessUnit(baseEntity));
			}
		}

		return businessEntities;
	}

	public boolean checkIfBasicListIsPopulated(List<S> entities) {
		return (entities != null && !entities.isEmpty());
	}

	public boolean checkIfBusinessBasicListIsPopulated(List<T> businessEntities) {
		return (businessEntities != null && !businessEntities.isEmpty());
	}


	public PaginatedEntity<T> convertBasicPageToBusinessPage(Page<S> baseEntities,BusinessSearchCriteria searchCriteria) {
		
		PaginatedEntity<T> paginatedEntity=new PaginatedEntity<T>();
		
		paginatedEntity.setCurrentPage(searchCriteria.getPageNumber());
		paginatedEntity.setPageSize(searchCriteria.getPageSize());
		paginatedEntity.setNumberOfPages(baseEntities.getTotalPages());
		paginatedEntity.setNumberOfItems(baseEntities.getTotalElements());
		paginatedEntity.setHasNext(!baseEntities.isLast());
		paginatedEntity.setHasPrevious(!baseEntities.isFirst());
		paginatedEntity.setDataList(this.convertBasicListToBusinessList(baseEntities.getContent()));
		
		return paginatedEntity;
	
	}


}