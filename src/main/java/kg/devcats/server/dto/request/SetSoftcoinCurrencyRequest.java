package kg.devcats.server.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record SetSoftcoinCurrencyRequest(
        @DecimalMin(value = "0.00000001", inclusive = true)
        @DecimalMax(value = "9999999999", inclusive = true)
        BigDecimal value
) {

}