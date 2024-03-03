package kg.devcats.server.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Column(nullable = false, columnDefinition = "numeric(38,0)")
    BigDecimal price;

    @Column(nullable = false, columnDefinition = "numeric(38,0)")
    BigDecimal commission;

    @Column(nullable = false)
    BigDecimal percentageOfProfitForDealer;

    @Column(nullable = false)
    BigDecimal percentageOfProfitForSystem;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String imageUrl;

    @ManyToOne
    Currency currency;

    @Column(nullable = false)
    Integer quantity;

    @ManyToOne
    User manager;

    @Column(nullable = false, columnDefinition = "numeric(38,4)")
    BigDecimal pastRate;

}
