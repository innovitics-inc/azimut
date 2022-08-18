package innovitics.azimut.utilities.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;

@Component
public abstract class ParentMapper <S extends BaseEntity, T extends BaseBusinessEntity>{
	@Autowired protected NumberUtility longUtility;
	protected static final Logger logger = LoggerFactory.getLogger(ParentMapper.class);

	protected  abstract S convertBusinessUnitToBasicUnit(T t , boolean save);
	
	protected  abstract T convertBasicUnitToBusinessUnit(S s);
	protected  abstract T convertBasicUnitToBusinessUnit(S s,String language);

	public T convertBusinessDatesToBasicDates(S baseEntity,T baseBusinessEntity,boolean save)
	{
		baseEntity.setCreatedAt(baseBusinessEntity.getCreatedAt());
		baseEntity.setDeletedAt(baseBusinessEntity.getDeletedAt());
		baseEntity.setUpdatedAt(save?new Date():baseBusinessEntity.getUpdatedAt());
		return baseBusinessEntity;
		
	}
	
	public S convertBasicDatesToBusinessDates(S baseEntity,T baseBusinessEntity)
	{
		baseBusinessEntity.setCreatedAt(baseEntity.getCreatedAt());
		baseBusinessEntity.setDeletedAt(baseEntity.getDeletedAt());
		baseBusinessEntity.setUpdatedAt(baseEntity.getUpdatedAt());
		return baseEntity;
		
	}
	
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

	
	public List<T> convertBasicSetToBusinessList(Set<S> baseEntities) {
		List<T> businessEntities = new ArrayList<T>();

		if (checkIfBasicSetIsPopulated(baseEntities)) {
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
	
	
	public boolean checkIfBasicSetIsPopulated(Set<S> entities) {
		return (entities != null && !entities.isEmpty());
	}

	public boolean checkIfBusinessBasicSetIsPopulated(Set<T> businessEntities) {
		return (businessEntities != null && !businessEntities.isEmpty());
	}
}
