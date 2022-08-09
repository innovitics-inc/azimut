package innovitics.azimut.utilities.dbutilities.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;

public class FetchSpecification  <T extends BaseEntity,P extends BaseEntity> implements Specification<T> {

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
