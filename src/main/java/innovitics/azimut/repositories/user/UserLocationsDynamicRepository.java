package innovitics.azimut.repositories.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.user.UserLocation;
@Repository
public interface UserLocationsDynamicRepository extends JpaRepository<UserLocation, Long>,JpaSpecificationExecutor<UserLocation>{


	 @Query(value="update user_locations set deleted_at=sysdate() where user_id=? and deleted_at is null",nativeQuery = true)
    @Modifying
    @Transactional
	void deleteOldUserLocations(Long userId);
	 
	
	
}
