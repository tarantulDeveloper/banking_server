package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.IncomeResponseForAdmin;
import kg.devcats.server.dto.response.MonthlyIncomeResponseForManager;
import kg.devcats.server.entity.Income;
import kg.devcats.server.repo.IncomeRepository;
import kg.devcats.server.service.IncomeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IncomeServiceImpl implements IncomeService {

    IncomeRepository incomeRepository;

    @Override
    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public BigDecimal getIncomesSum() {
        return incomeRepository.getSystemIncomesSum();
    }

    @Override
    public List<MonthlyIncomeResponseForManager> getSystemMonthlyIncome() {
        return incomeRepository.getIncomeSumByMonth(Year.now().getValue());
    }

    @Override
    public List<MonthlyIncomeResponseForManager> getSystemMonthlyIncomeByYear(int year) {
        return incomeRepository.getIncomeSumByMonth(year);

    }

    @Override
    public List<IncomeResponseForAdmin> getSystemIncomes() {
        return incomeRepository.getSystemIncomes();
    }

}
