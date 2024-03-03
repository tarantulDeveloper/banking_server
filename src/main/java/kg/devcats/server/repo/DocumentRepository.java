package kg.devcats.server.repo;

import kg.devcats.server.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "SELECT * FROM documents WHERE user_id = ?", nativeQuery = true)
    Optional<Document> findByUserId(Long userId);
}
