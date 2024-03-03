package kg.devcats.server.dto.response;

import java.math.BigDecimal;

public record CurrencyResponse(
        String isoCode,
        BigDecimal value
) {
}
