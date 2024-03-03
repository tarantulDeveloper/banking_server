package kg.devcats.server.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction extends BaseEntity {


    @ManyToOne
    ClientAccount sender;

    @ManyToOne
    ClientAccount receiver;


    @Column(nullable = false, columnDefinition = "numeric(38,2) check (amount >= 0)")
    BigDecimal amount;

}

