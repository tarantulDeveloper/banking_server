package kg.devcats.server.service;

import kg.devcats.server.dto.response.IncomeResponseForAdmin;
import kg.devcats.server.dto.response.MonthlyIncomeResponseForManager;
import kg.devcats.server.entity.Income;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeService {
    Income save(Income income);

    List<IncomeResponseForAdmin> getSystemIncomes();

    BigDecimal getIncomesSum();

    List<MonthlyIncomeResponseForManager> getSystemMonthlyIncome();

    List<MonthlyIncomeResponseForManager> getSystemMonthlyIncomeByYear(int year);

}
