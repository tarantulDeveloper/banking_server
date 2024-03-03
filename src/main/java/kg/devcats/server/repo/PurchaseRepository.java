package kg.devcats.server.repo;

import kg.devcats.server.dto.response.MonthlyPurchaseResponseForManager;
import kg.devcats.server.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @Query(value = "SELECT count(*) from purchases",nativeQuery = true)
    Integer getTotalPurchases();


    @Query("SELECT NEW kg.devcats.server.dto.response.MonthlyPurchaseResponseForManager(" +
            "TRIM(to_char(p.createdAt, 'Month')), count(*)) " +
            "FROM Purchase p " +
            "WHERE YEAR(p.createdAt) = :year " +
            "GROUP BY TRIM(to_char(p.createdAt, 'Month'))")
    List<MonthlyPurchaseResponseForManager> getPurchaseTotalByMonth(@Param("year") int year);


}
