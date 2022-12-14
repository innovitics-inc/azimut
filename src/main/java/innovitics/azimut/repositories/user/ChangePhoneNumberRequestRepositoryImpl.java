package innovitics.azimut.repositories.user;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.models.ChangePhoneNumberRequest;
import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.AbstractRepository;

public class ChangePhoneNumberRequestRepositoryImpl extends AbstractRepository<ChangePhoneNumberRequest>  implements ChangePhoneNumberRequestRepositoryCustom{

	@SuppressWarnings("unchecked")
	@Override
	public List<ChangePhoneNumberRequest> findByOldPhoneNumber(String oldPhoneNumber) {
		return (List<ChangePhoneNumberRequest>)this.generateQuery("Select * from change_phone_number_requests where (is_approved=0 or is_approved is null) and old_phone_number= ? ", ChangePhoneNumberRequest.class, oldPhoneNumber).getResultList();
	}


}
