package kg.devcats.server.endpoint;


import kg.devcats.server.dto.response.IncomeResponseForAdmin;
import kg.devcats.server.dto.response.MonthlyIncomeResponseForManager;
import kg.devcats.server.exception.DivisionByZeroException;
import kg.devcats.server.mapper.IncomeMapper;
import kg.devcats.server.service.CurrencyService;
import kg.devcats.server.service.IncomeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IncomeEndpoint {

    IncomeService incomeService;
    CurrencyService currencyService;
    RoundingMode roundingMode = RoundingMode.HALF_EVEN;
    IncomeMapper incomeMapper;

    public List<IncomeResponseForAdmin> getAllSystemIncomes(String isoCode) {
        List<IncomeResponseForAdmin> incomeInSoftcoin = incomeService.getSystemIncomes();

        Pair<BigDecimal, BigDecimal> rates = getRates(isoCode);

        if (rates == null) {
            return incomeInSoftcoin;
        }

        BigDecimal currencyRate = rates.getLeft();
        BigDecimal softCoinRate = rates.getRight();

        return incomeInSoftcoin.stream()
                .map(incomeResponseForAdmin -> convertToCurrency(incomeResponseForAdmin, currencyRate, softCoinRate))
                .collect(Collectors.toList());
    }

    public BigDecimal getAllSystemIncomesSum(String isoCode) {
        BigDecimal incomesSumInSoftcoin = incomeService.getIncomesSum();

        if (incomesSumInSoftcoin == null) {
            return BigDecimal.ZERO;
        }

        Pair<BigDecimal, BigDecimal> rates = getRates(isoCode);

        if(rates == null) {
            return incomesSumInSoftcoin;
        }

        BigDecimal currencyRate = rates.getLeft();
        BigDecimal softCoinRate = rates.getRight();

        BigDecimal incomeAmountDivideBySoftCoinRateResult = incomesSumInSoftcoin.divide(softCoinRate, 4, roundingMode);

        return incomeAmountDivideBySoftCoinRateResult.divide(currencyRate, 4, roundingMode);
    }

    public List<MonthlyIncomeResponseForManager> getSystemMonthlyIncome(Optional<Integer> year, String isoCode) {
        List<MonthlyIncomeResponseForManager> incomes;
        if (year.isPresent()) {
            incomes = getMonthlyIncomeResponseForManagers(isoCode, incomeService.getSystemMonthlyIncomeByYear(year.get()));
        } else {
            incomes = getMonthlyIncomeResponseForManagers(isoCode,incomeService.getSystemMonthlyIncome());
        }


        Map<String, BigDecimal> incomeMap = new HashMap<>();
        for (MonthlyIncomeResponseForManager income : incomes) {
            incomeMap.put(income.month(), income.total());
        }

        DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        String[] months = dfs.getMonths();

        List<MonthlyIncomeResponseForManager> result = new ArrayList<>();
        for (int i = 0; i < months.length - 1; i++) {
            String monthName = months[i];
            BigDecimal sum = incomeMap.getOrDefault(monthName,BigDecimal.ZERO);
            result.add(new MonthlyIncomeResponseForManager(monthName, sum));
        }
        return result;
    }

    private Pair<BigDecimal, BigDecimal> getRates(String isoCode) {
        if (isoCode.equals("SFC")) {
            return null;
        }

        BigDecimal currencyRate = currencyService.findByIsoCode(isoCode).getValue();
        BigDecimal softCoinRate = currencyService.findByIsoCode("SFC").getValue();

        if (currencyRate.compareTo(BigDecimal.ZERO) == 0 || softCoinRate.compareTo(BigDecimal.ZERO) == 0) {
            throw new DivisionByZeroException();
        }

        return Pair.of(currencyRate, softCoinRate);
    }

    private IncomeResponseForAdmin convertToCurrency(IncomeResponseForAdmin incomeResponseForAdmin, BigDecimal currencyRate, BigDecimal softCoinRate) {
        BigDecimal incomeAmount = convert(incomeResponseForAdmin.incomeAmount(), currencyRate, softCoinRate);
        BigDecimal systemPortion = convert(incomeResponseForAdmin.systemPortion(), currencyRate, softCoinRate);
        BigDecimal dealerPortion = convert(incomeResponseForAdmin.dealerPortion(), currencyRate, softCoinRate);
        BigDecimal cost = convert(incomeResponseForAdmin.cost(), currencyRate, softCoinRate);
        BigDecimal realCost = convert(incomeResponseForAdmin.realCost(), currencyRate, softCoinRate);

        return incomeMapper.toIncomeResponseForAdminInCurrency(incomeResponseForAdmin,incomeAmount,systemPortion, dealerPortion, cost, realCost);
    }

    private BigDecimal convert(BigDecimal amount, BigDecimal currencyRate, BigDecimal softCoinRate) {
        return amount.divide(softCoinRate, 4, roundingMode).divide(currencyRate, 4, roundingMode);
    }

    private List<MonthlyIncomeResponseForManager> getMonthlyIncomeResponseForManagers(String isoCode, List<MonthlyIncomeResponseForManager> monthlyIncomes) {
        Pair<BigDecimal, BigDecimal> rates = getRates(isoCode);

        if (rates == null) {
            return monthlyIncomes;
        }

        BigDecimal currencyRate = rates.getLeft();
        BigDecimal softCoinRate = rates.getRight();

        return monthlyIncomes.stream()
                .map(monthlyIncome -> {
                    BigDecimal totalIncomePerMonthDividedBySoftCoinRateResult = monthlyIncome.total().divide(softCoinRate, 4, roundingMode);
                    return new MonthlyIncomeResponseForManager(monthlyIncome.month(), totalIncomePerMonthDividedBySoftCoinRateResult.divide(currencyRate, 4, roundingMode));
                })
                .collect(Collectors.toList());
    }


}
