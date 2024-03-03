package kg.devcats.server.entity;


import jakarta.persistence.*;
import kg.devcats.server.entity.enums.RegistrationRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Table(name = "registration_requests")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest extends BaseEntity {

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String personalNumber;

    @Column(nullable = false)
    String documentId;

    @Column(nullable = false)
    String authority;

    @Column(nullable = false)
    Timestamp documentIssuedAt;

    @Column(nullable = false)
    Timestamp documentExpiresAt;

    @Column(nullable = false)
    String citizenship;

    @Column(nullable = false)
    Timestamp birthDate;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String patronymic;

    @Column(nullable = false, length = 2000)
    String profileImagePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RegistrationRequestStatus status;


}
