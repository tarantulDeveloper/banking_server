package kg.devcats.server.repo;

import kg.devcats.server.entity.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {
    @Query(value = "SELECT * FROM client_accounts WHERE user_id = ?", nativeQuery = true)
    Optional<ClientAccount> findByUserId(Long clientId);

    @Query(value = "SELECT c.* FROM client_accounts c JOIN users u ON c.user_id = u.id WHERE u.email = ? LIMIT 1", nativeQuery = true)
    Optional<ClientAccount> findClientAccountByUserEmail(String email);
}