package kg.devcats.server.repo;

import kg.devcats.server.dto.response.ProductFullInfoResponse;
import kg.devcats.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "SELECT * FROM products p WHERE p.deleted = false AND p.quantity > 0",nativeQuery = true)
    List<Product> findAllExisting();

    @Modifying
    @Transactional
    @Query(value = "UPDATE products  SET deleted = true WHERE id = ?",nativeQuery = true)
    void softDelete(Long productId);


    @Query(value = "SELECT p FROM Product p " +
            "JOIN Catalog c ON p MEMBER OF c.products " +
            "JOIN c.dealer u " +
            "WHERE u.email = :email " +
            "AND p.deleted = false " +
            "AND p.quantity > 0")
    List<Product> findAllExistingByOwnerEmail(@Param("email") String email);



    @Query(value = "SELECT NEW kg.devcats.server.dto.response.ProductFullInfoResponse(" +
            "p.id,p.name,p.imageUrl,p.price,p.percentageOfProfitForDealer,p.percentageOfProfitForSystem," +
            "p.commission,p.quantity,p.currency.isoCode,u.email) FROM Product p,User u,Catalog c" +
            " WHERE u.id = c.dealer.id AND p MEMBER OF c.products AND p.id = :productId")
    ProductFullInfoResponse findFullInfoById(@Param("productId")Long productId);
}
