package kg.devcats.server.repo;


import kg.devcats.server.dto.response.IncomeResponseForAdmin;
import kg.devcats.server.dto.response.MonthlyIncomeResponseForManager;
import kg.devcats.server.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Long> {

    @Query(value = "SELECT NEW kg.devcats.server.dto.response.IncomeResponseForAdmin(" +
            "i.id,i.createdAt,i.updatedAt,u.email,i.incomeAmount,p.percentageOfProfitForSystem," +
            "p.percentageOfProfitForDealer,pr.cost,pr.realCost,p.id,p.name) FROM User u,Product p,Income i," +
            "Purchase pr WHERE u.id=pr.dealer.id AND pr.product.id=p.id AND pr.id=i.purchase.id AND i.ownerType='SYSTEM'")
    List<IncomeResponseForAdmin> getSystemIncomes();


    @Query(value = "SELECT sum(i.income_amount) FROM incomes i where i.owner_type = 'SYSTEM'",nativeQuery = true)
    BigDecimal getSystemIncomesSum();


    @Query("SELECT NEW kg.devcats.server.dto.response.MonthlyIncomeResponseForManager(" +
            "TRIM(to_char(i.createdAt, 'Month')), SUM(i.incomeAmount)) " +
            "FROM Income i " +
            "WHERE YEAR(i.createdAt) = :year AND i.ownerType='SYSTEM'" +
            "GROUP BY TRIM(to_char(i.createdAt, 'Month'))")
    List<MonthlyIncomeResponseForManager> getIncomeSumByMonth(@Param("year") int year);





}
