package kg.devcats.server.dto.response;

import java.math.BigDecimal;

public record ProductResponseForManager(
        Long id,
        String name,
        String imageUrl,
        BigDecimal price,
        BigDecimal percentageOfProfitForDealer,
        BigDecimal percentageOfProfitForSystem,
        BigDecimal commission
) {

}
