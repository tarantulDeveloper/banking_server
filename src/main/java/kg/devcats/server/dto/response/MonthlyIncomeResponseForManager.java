package kg.devcats.server.dto.response;

import java.math.BigDecimal;

public record MonthlyIncomeResponseForManager(
        String month,
        BigDecimal total
) {

}
