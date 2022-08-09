package innovitics.azimut.utilities.dbutilities.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;

@Component
public class EntityChildSpecification  <T extends BaseEntity> implements Specification<T> {

	private static final long serialVersionUID = 1L;

	public EntityChildSpecification<T> findByCriteria(List<SearchCriteria> searchCriteriaList) {
		return new EntityChildSpecification<T>() {
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
