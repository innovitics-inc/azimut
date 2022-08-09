package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.ChangePhoneNumberRequest;
@Repository
public interface ChangePhoneNumberRequestRepository extends JpaRepository<ChangePhoneNumberRequest, Long>, ChangePhoneNumberRequestRepositoryCustom {

}
