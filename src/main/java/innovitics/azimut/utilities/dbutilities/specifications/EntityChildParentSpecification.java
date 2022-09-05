package innovitics.azimut.utilities.dbutilities.specifications;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.rest.BaseRestConsumer;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class EntityChildParentSpecification <T extends BaseEntity,P extends BaseEntity> extends BaseSpecification implements Specification<T>  {
	public final static Logger logger = LogManager.getLogger(EntityChildParentSpecification.class.getName());
	
	private static final long serialVersionUID = 1L;

	public EntityChildParentSpecification<T,P> findByCriteria(List<SearchCriteria> searchCriteriaList) {
		return new EntityChildParentSpecification<T,P>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				for (SearchCriteria searchCriteria : searchCriteriaList) {
					
					switch (searchCriteria.getOperation()) {
					case GREATER_THAN:
						predicates.add(criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case LESS_THAN:
						predicates.add(criteriaBuilder.lessThan(root.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case GREATER_THAN_EQUAL:
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case LESS_THAN_EQUAL:
						predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case NOT_EQUAL:
						predicates.add(
								criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
						break;
					case EQUAL:
						predicates.add(
								criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
						break;
					case LIKE:
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
								"%" + searchCriteria.getValue().toString().toLowerCase() + "%"));
						break;
					case LIKE_END:
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
								searchCriteria.getValue().toString().toLowerCase() + "%"));
						break;
					case LIKE_START:
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
								"%" + searchCriteria.getValue().toString().toLowerCase()));
						break;
					case IN:
						predicates.add(
								criteriaBuilder.in(root.get(searchCriteria.getKey())).value(searchCriteria.getValueList()));
						break;
					case NOT_IN:
						predicates.add(
								criteriaBuilder.not(root.get(searchCriteria.getKey())).in(searchCriteria.getValueList()));
						break;
					case IS_NULL:
						predicates.add(
								criteriaBuilder.isNull(root.get(searchCriteria.getKey())));
						break;
					case IS_NOT_NULL:
						predicates.add(
								criteriaBuilder.isNotNull(root.get(searchCriteria.getKey())));
						break;
						
					 case BETWEEN: 
					       Path<Date> entityDate = root.get(searchCriteria.getKey());
					       predicates.add(criteriaBuilder.between(entityDate, getComparingDates(searchCriteria.getRangeFrom(), 0), getComparingDates(searchCriteria.getRangeTo(), 1)));
						break;
					 case BEFORE: 
					       Path<Date> beforeEntityDate = root.get(searchCriteria.getKey());
					       predicates.add(criteriaBuilder.lessThan(beforeEntityDate, getComparingDates((String)searchCriteria.getValue(), 0)));
						break;
					 case AFTER: 
					       Path<Date> afterEntityDate = root.get(searchCriteria.getKey());
					       predicates.add(criteriaBuilder.greaterThan(afterEntityDate, getComparingDates((String)searchCriteria.getValue(), 0)));
						break;
					case PARENT_GREATER_THAN:
						Join<T, P> joinParentGreaterThan = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.greaterThan(joinParentGreaterThan.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case PARENT_LESS_THAN:
						Join<T, P> joinParentLessThan = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.lessThan(joinParentLessThan.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case PARENT_GREATER_THAN_EQUAL:
						Join<T, P> joinParentGreaterThanEqual = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(joinParentGreaterThanEqual.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case PARENT_LESS_THAN_EQUAL:
						Join<T, P> joinParentLessThanEqual = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.lessThanOrEqualTo(joinParentLessThanEqual.get(searchCriteria.getKey()),
								searchCriteria.getValue().toString()));
						break;
					case PARENT_NOT_EQUAL:
						Join<T, P> joinParentNotEqual = root.join(searchCriteria.getJoiningColumn());
						predicates.add(
								criteriaBuilder.notEqual(joinParentNotEqual.get(searchCriteria.getKey()), searchCriteria.getValue()));
						break;
					case PARENT_EQUAL:
						Join<T, P> joinParentEqual = root.join(searchCriteria.getJoiningColumn());
						predicates.add(
								criteriaBuilder.equal(joinParentEqual.get(searchCriteria.getKey()), searchCriteria.getValue()));
						break;
					case PARENT_LIKE:
						Join<T, P> joinParentLike = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(joinParentLike.get(searchCriteria.getKey())),
								"%" + searchCriteria.getValue().toString().toLowerCase() + "%"));
						break;
					case PARENT_LIKE_END:
						Join<T, P> joinParentLikeEnd = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(joinParentLikeEnd.get(searchCriteria.getKey())),
								searchCriteria.getValue().toString().toLowerCase() + "%"));
						break;
					case PARENT_LIKE_START:
						Join<T, P> joinParentLikeStart = root.join(searchCriteria.getJoiningColumn());
						predicates.add(criteriaBuilder.like(criteriaBuilder.lower(joinParentLikeStart.get(searchCriteria.getKey())),
								"%" + searchCriteria.getValue().toString().toLowerCase()));
						break;
					case PARENT_IN:
						Join<T, P> joinParentIn= root.join(searchCriteria.getJoiningColumn());
						predicates.add(
								criteriaBuilder.in(joinParentIn.get(searchCriteria.getKey())).value(searchCriteria.getValueList()));
						break;
					case PARENT_NOT_IN:
						Join<T, P> joinParentNotIn= root.join(searchCriteria.getJoiningColumn());
						predicates.add(
								criteriaBuilder.not(joinParentNotIn.get(searchCriteria.getKey())).in(searchCriteria.getValueList()));
						break;
					case PARENT_IS_NULL:
						Join<T, P> joinParentIsNull= root.join(searchCriteria.getJoiningColumn());
						predicates.add(
								criteriaBuilder.isNull(joinParentIsNull.get(searchCriteria.getKey())));
						break;
					case PARENT_IS_NOT_NULL:
						Join<T, P> joinParentIsNotNull= root.join(searchCriteria.getJoiningColumn());
						predicates.add(
								criteriaBuilder.isNotNull(joinParentIsNotNull.get(searchCriteria.getKey())));
						break;
					 case PARENT_BETWEEN: 
						   Join<T, P> joinParentIsBetween= root.join(searchCriteria.getJoiningColumn());
					       Path<Date> parentEntityDate = root.get(searchCriteria.getKey());
					       predicates.add(criteriaBuilder.between(parentEntityDate, getComparingDates(searchCriteria.getRangeFrom(), 0), getComparingDates(searchCriteria.getRangeTo(), 1)));
						break;
					 case PARENT_BEFORE: 
					       Path<Date> beforeParentEntityDate = root.get(searchCriteria.getKey());
					       predicates.add(criteriaBuilder.lessThan(beforeParentEntityDate, getComparingDates((String)searchCriteria.getValue(), 0)));
						break;
					 case PARENT_AFTER: 
					       Path<Date> afterParentEntityDate = root.get(searchCriteria.getKey());
					       predicates.add(criteriaBuilder.greaterThan(afterParentEntityDate, getComparingDates((String)searchCriteria.getValue(), 0)));
						break;

					default:
						break;
					}
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}
}
