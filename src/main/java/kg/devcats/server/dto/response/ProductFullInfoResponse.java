package kg.devcats.server.dto.response;


import java.math.BigDecimal;

public record ProductFullInfoResponse(
        Long id,
        String name,
        String imageUrl,
        BigDecimal price,
        BigDecimal percentageOfProfitForDealer,
        BigDecimal percentageOfProfitForSystem,
        BigDecimal commission,
        Integer quantity,
        String isoCode,
        String dealerEmail
) {
}
