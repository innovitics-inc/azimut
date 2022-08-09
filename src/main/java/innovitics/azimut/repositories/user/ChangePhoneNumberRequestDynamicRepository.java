package innovitics.azimut.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.ChangePhoneNumberRequest;

@Repository
public interface ChangePhoneNumberRequestDynamicRepository extends JpaRepository<ChangePhoneNumberRequest, Long>,JpaSpecificationExecutor<ChangePhoneNumberRequest>{

}
