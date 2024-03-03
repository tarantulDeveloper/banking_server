package kg.devcats.server.repo;

import kg.devcats.server.dto.response.ChangeProfileInfoResponse;
import kg.devcats.server.entity.ProfileChangeRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileChangeRequestRepository extends JpaRepository<ProfileChangeRequest, Long> {

    @Query(value = "SELECT * FROM profile_changes_requests r WHERE r.status = 'PENDING' AND r.id = ? LIMIT 1", nativeQuery = true)
    Optional<ProfileChangeRequest> findPendingById(Long id);

    @Query(value = "SELECT NEW kg.devcats.server.dto.response.ChangeProfileInfoResponse" +
            "(r.id,r.dealerEmail,r.firstName,r.lastName,r.patronymic,r.phoneNumber,r.documentId,r.authority,r.documentIssuedAt,r.documentExpiresAt,r.citizenship,r.birthDate,r.createdAt) FROM " +
            "ProfileChangeRequest r where r.status = kg.devcats.server.entity.enums.ProfileChangeRequestStatus.PENDING")
    List<ChangeProfileInfoResponse> getAllPending();

}
