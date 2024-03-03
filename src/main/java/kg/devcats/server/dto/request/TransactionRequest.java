package kg.devcats.server.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank(message = "Transaction email can not blank") String email,
        @Positive(message = "Transaction amount can not be negative")
        @Min(value = 1, message = "Transaction amount at least must be 1")
        BigDecimal amount
) {

}
