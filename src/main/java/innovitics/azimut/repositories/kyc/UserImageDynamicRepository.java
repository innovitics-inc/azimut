package innovitics.azimut.repositories.kyc;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.models.user.UserImage;
@Repository
public interface UserImageDynamicRepository extends JpaRepository<UserImage, Long>,JpaSpecificationExecutor<UserImage>,EntityGraphJpaSpecificationExecutor<UserImage>{

	 @EntityGraph(value = "UserImage.details",attributePaths = {"imageUrl","imageSubDirectory","imageType"},type = EntityGraphType.FETCH )
	 Optional<UserImage> findById(Long id);
	 
	 @Query(value="update user_images set deleted_at=sysdate() where user_id=? and image_type in (5,6,7) and deleted_at is null",nativeQuery = true)
     @Modifying
     @Transactional
     public  void deleteOldUserIdImages(Long userId);	
	 
	 @Query(value="update user_images set deleted_at=sysdate() where user_id=? and image_type in (1,2,3,4) and deleted_at is null",nativeQuery = true)
     @Modifying
     @Transactional
     public  void deleteOldUserFaceImages(Long userId);	
}
