package kg.devcats.server.entity;

import jakarta.persistence.*;
import kg.devcats.server.entity.enums.ProfileChangeRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "profile_changes_requests")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileChangeRequest extends BaseEntity{

    @Column(nullable = false)
    String dealerEmail;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String patronymic;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProfileChangeRequestStatus status;

}
