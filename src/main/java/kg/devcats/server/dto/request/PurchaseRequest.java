package kg.devcats.server.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product id can not be null")
        Long productId,
        @NotNull(message = "Product quantity can not be null")
        @Positive(message = "Product quantity must be positive")
        Integer quantity
) {
}
