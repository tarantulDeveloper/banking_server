package kg.devcats.server.repo;


import kg.devcats.server.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<Currency, Integer> {


    @Query(value = "SELECT c.* FROM currencies c WHERE c.iso_code in (:isoCodes)", nativeQuery = true)
    List<Currency> findAllByIsoCode(List<String> isoCodes);

    Optional<Currency> findByIsoCode(String isoCode);


    @Modifying
    @Transactional
    @Query("UPDATE currencies c SET c.value = :newValue WHERE c.isoCode = :isoCode")
    void updateValueByIsoCode(@Param("isoCode") String isoCode, @Param("newValue") BigDecimal newValue);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM currencies WHERE iso_code = ?1)", nativeQuery = true)
    boolean existsByIsoCode(String isoCode);

    @Query(value = "SELECT COUNT(*) FROM currencies", nativeQuery = true)
    int countRows();
}
