package kg.devcats.server.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record HistoryResponse(
   String email,
   Integer quantity,
   String productName,
   BigDecimal cost,
   Timestamp createdAt
) {
}
