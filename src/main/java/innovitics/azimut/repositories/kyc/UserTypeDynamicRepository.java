package innovitics.azimut.repositories.kyc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import innovitics.azimut.models.user.UserType;

public interface UserTypeDynamicRepository extends JpaRepository<UserType, Long>,JpaSpecificationExecutor<UserType>{

}
