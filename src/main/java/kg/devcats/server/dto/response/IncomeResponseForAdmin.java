package kg.devcats.server.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
public record IncomeResponseForAdmin(
        Long id,
        Timestamp createdAt,
        Timestamp updatedAt,
        String dealerEmail,
        BigDecimal incomeAmount,
        BigDecimal systemPortion,
        BigDecimal dealerPortion,
        BigDecimal cost,
        BigDecimal realCost,
        Long productId,
        String productName
)
{

}
