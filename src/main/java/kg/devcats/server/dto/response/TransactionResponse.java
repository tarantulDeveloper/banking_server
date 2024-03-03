package kg.devcats.server.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record TransactionResponse(
        String email,
        BigDecimal amount,
        Timestamp createdAt
) {
}
