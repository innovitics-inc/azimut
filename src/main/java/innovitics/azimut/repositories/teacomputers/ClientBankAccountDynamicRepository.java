package innovitics.azimut.repositories.teacomputers;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import innovitics.azimut.models.teacomputers.ClientBankAccount;

@Repository
public interface ClientBankAccountDynamicRepository  extends JpaRepository<ClientBankAccount, Long>,JpaSpecificationExecutor<ClientBankAccount>{
	
	
	
	@Query(value="update client_bank_accounts set deleted_at=sysdate() where user_id=? and deleted_at is null",nativeQuery = true)
    @Modifying
    @Transactional
	void softDeleteClientBankAccounts(Long userId);
	

}
