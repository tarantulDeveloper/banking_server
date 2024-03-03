package kg.devcats.server.entity;

import jakarta.persistence.*;
import kg.devcats.server.entity.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Entity
@SuperBuilder
@Table(name = "users", indexes = {
        @Index(name = "users_email_idx", columnList = "email", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "email", callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String patronymic;

    @Column(nullable = false, length = 2000)
    String profileImagePath;

    @Column(nullable = false)
    boolean activated;

    String activationToken;

}
