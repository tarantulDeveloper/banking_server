package kg.devcats.server.repo;

import kg.devcats.server.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog,Long> {


    Optional<Catalog> getCatalogByDealerEmail(String dealer_email);
}
