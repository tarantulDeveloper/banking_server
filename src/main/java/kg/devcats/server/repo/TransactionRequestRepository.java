package kg.devcats.server.repo;

import kg.devcats.server.dto.response.TransactionRequestResponse;
import kg.devcats.server.entity.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRequestRepository extends JpaRepository<TransactionRequest, Long> {

    @Query(value = "SELECT NEW kg.devcats.server.dto.response.TransactionRequestResponse" +
            "(t.id,t.sender.user.email,t.receiver.user.email,t.amount,t.createdAt) FROM " +
            "TransactionRequest t where t.status = kg.devcats.server.entity.enums.TransactionRequestStatus.PENDING")
    List<TransactionRequestResponse> getAllPending();


    @Query(value = "SELECT * FROM transaction_requests tr WHERE tr.status = 'PENDING' AND tr.id = ? LIMIT 1", nativeQuery = true)
    Optional<TransactionRequest> findPendingById(Long id);
}
