package innovitics.azimut.repositories.user;

import java.util.List;

import innovitics.azimut.models.ChangePhoneNumberRequest;
import innovitics.azimut.models.user.User;

public interface ChangePhoneNumberRequestRepositoryCustom {

	List<ChangePhoneNumberRequest> findByOldPhoneNumber(String oldPhoneNumber);
	
	
}
