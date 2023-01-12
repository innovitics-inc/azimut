package innovitics.azimut.repositories.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.User;

@Repository
public interface UserDynamicRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{

	 @Query(value="update app_users set is_reviewed=1 where user_id=?",nativeQuery = true)
     @Modifying
	 void flagTheUserAsReviewed(Long userId);

}
