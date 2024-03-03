package kg.devcats.server.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Table(name = "documents")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Document extends BaseEntity {

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    User user;

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

}
