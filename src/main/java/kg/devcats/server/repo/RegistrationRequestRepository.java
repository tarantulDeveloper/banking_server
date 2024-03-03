package kg.devcats.server.repo;

import kg.devcats.server.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    @Query(value = "SELECT * FROM registration_requests r WHERE r.status='PENDING' ", nativeQuery = true)
    List<RegistrationRequest> findAllPending();


    @Query(value = "SELECT EXISTS(SELECT r.id FROM registration_requests r WHERE r.email = ?)", nativeQuery = true)
    boolean existsByEmail(String email);


    @Query(value = "SELECT * FROM registration_requests r WHERE r.status='PENDING' AND r.id = ? ", nativeQuery = true)
    Optional<RegistrationRequest> findPendingById(Long requestId);
}
