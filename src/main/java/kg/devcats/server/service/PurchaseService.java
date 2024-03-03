package kg.devcats.server.service;


import kg.devcats.server.dto.response.MonthlyPurchaseResponseForManager;
import kg.devcats.server.entity.Purchase;
import java.util.List;

public interface PurchaseService {

    void save(Purchase purchase);

    Integer getTotalPurchases();

    List<MonthlyPurchaseResponseForManager> getSystemMonthlyPurchasesByYear(Integer integer);

    List<MonthlyPurchaseResponseForManager> getSystemMonthlyPurchases();
}

