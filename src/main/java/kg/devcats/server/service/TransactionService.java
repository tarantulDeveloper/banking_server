package kg.devcats.server.service;

import kg.devcats.server.dto.response.TransactionResponse;
import kg.devcats.server.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);

    List<TransactionResponse> getTransactionInfoByEmail(String email);

}
