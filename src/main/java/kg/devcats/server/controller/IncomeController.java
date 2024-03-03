package kg.devcats.server.controller;


import kg.devcats.server.dto.response.IncomeResponseForAdmin;
import kg.devcats.server.dto.response.MonthlyIncomeResponseForManager;
import kg.devcats.server.endpoint.IncomeEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IncomeController {

    IncomeEndpoint incomeEndpoint;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/system")
    public List<IncomeResponseForAdmin> getSystemIncomes(@RequestParam(name = "isoCode", required = false, defaultValue = "SFC") String isoCode) {
        return incomeEndpoint.getAllSystemIncomes(isoCode);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/system/total")
    public BigDecimal getSystemIncomesSum(@RequestParam(value = "isoCode", required = false, defaultValue= "SFC") String isoCode) {
        return incomeEndpoint.getAllSystemIncomesSum(isoCode);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/system/monthly")
    public List<MonthlyIncomeResponseForManager> getSystemMonthlyIncome(
            @RequestParam(name = "year",required = false) Optional<Integer> year,
            @RequestParam(name = "isoCode", required = false, defaultValue = "SFC") String isoCode) {
        return incomeEndpoint.getSystemMonthlyIncome(year, isoCode);
    }


}
