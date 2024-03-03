package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.TransactionResponse;
import kg.devcats.server.entity.Transaction;
import kg.devcats.server.repo.TransactionRepository;
import kg.devcats.server.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionResponse> getTransactionInfoByEmail(String email) {
        return transactionRepository.findTransactionsByEmail(email);
    }


}
