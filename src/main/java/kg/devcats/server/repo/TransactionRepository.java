package kg.devcats.server.repo;

import kg.devcats.server.dto.response.TransactionResponse;
import kg.devcats.server.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT NEW kg.devcats.server.dto.response.TransactionResponse(" +
            "  CASE WHEN t.sender.user.email = :email THEN t.receiver.user.email ELSE t.sender.user.email END," +
            "  CASE WHEN t.sender.user.email = :email THEN -t.amount ELSE t.amount END," +
            " t.createdAt) " +
            "FROM Transaction t " +
            "WHERE t.sender.user.email = :email OR t.receiver.user.email = :email")
    List<TransactionResponse> findTransactionsByEmail(@Param("email") String email);
}
