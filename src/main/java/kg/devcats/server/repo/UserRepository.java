package kg.devcats.server.repo;


import kg.devcats.server.dto.response.UserPersonalAccountResponse;
import kg.devcats.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users u WHERE u.email = ? LIMIT 1",nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT EXISTS(SELECT u.id FROM users u WHERE u.email = ?)",nativeQuery = true)
    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.activation_token = ?",nativeQuery = true)
    Optional<User> findByActivationToken(String token);

    @Query(value = "SELECT NEW kg.devcats.server.dto.response.UserPersonalAccountResponse" +
            "(u.firstName,u.lastName,u.patronymic,u.email,u.phoneNumber,u.profileImagePath,d.personalNumber,d.documentId,d.authority,d.documentIssuedAt,d.documentExpiresAt,d.citizenship,d.birthDate,c.balance) FROM " +
            "User u,Document d,ClientAccount c WHERE u.email= :email AND u.id = d.user.id AND c.user.id = u.id")
    UserPersonalAccountResponse findPersonalInfoByEmail(@Param("email") String email);

}
