package kg.devcats.server.repo;

import kg.devcats.server.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface HistoryRepository extends JpaRepository<Purchase,Long> {


    @Query(value = "SELECT pu " +
            "FROM Purchase pu " +
            "JOIN pu.dealer u " +
            "JOIN pu.product pr "+
            "WHERE u.email = :email "+
            "AND pu.deleted = false")
    List<Purchase> findAllExistingByOwnerEmail(@Param("email") String email);
}
