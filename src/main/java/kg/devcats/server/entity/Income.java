package kg.devcats.server.entity;

import jakarta.persistence.*;
import kg.devcats.server.entity.enums.OwnerType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Income extends BaseEntity {

    @ManyToOne
    Purchase purchase;

    @Enumerated(EnumType.STRING)
    OwnerType ownerType;

    BigDecimal incomeAmount;

}
