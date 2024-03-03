package kg.devcats.server.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "currencies")
@Table(indexes = {
        @Index(name = "iso_code_idx", columnList = "iso_code", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String isoCode;

    @Column(nullable = false, columnDefinition = "numeric(38,4)")
    private BigDecimal value;
}
