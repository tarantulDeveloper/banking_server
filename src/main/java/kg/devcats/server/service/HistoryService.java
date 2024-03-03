package kg.devcats.server.service;

import kg.devcats.server.entity.Purchase;

import java.util.List;


public interface HistoryService {
    List<Purchase> getPurchasesHistory(String email);
}
