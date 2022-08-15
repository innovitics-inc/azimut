package innovitics.azimut.repositories.teacomputers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;

import innovitics.azimut.models.teacomputers.Bank;
@Repository
public interface BankDynamicRepository extends JpaRepository<Bank, Long>,JpaSpecificationExecutor<Bank>,EntityGraphJpaSpecificationExecutor<Bank>  {

}
