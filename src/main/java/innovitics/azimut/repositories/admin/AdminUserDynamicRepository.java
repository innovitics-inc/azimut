package innovitics.azimut.repositories.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.admin.AdminUser;
import innovitics.azimut.models.kyc.KYCPage;


@Repository
public interface AdminUserDynamicRepository extends JpaRepository<AdminUser, Long>,JpaSpecificationExecutor<AdminUser>,EntityGraphJpaSpecificationExecutor<AdminUser>{

}
