package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.TransactionRequestResponse;
import kg.devcats.server.entity.TransactionRequest;
import kg.devcats.server.exception.RequestNotFoundException;
import kg.devcats.server.repo.TransactionRequestRepository;
import kg.devcats.server.service.TransactionRequestService;
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
public class TransactionRequestServiceImpl implements TransactionRequestService {

    TransactionRequestRepository transactionRequestRepository;

    @Override
    public List<TransactionRequestResponse> getAllPending() {
        return transactionRequestRepository.getAllPending();
    }

    @Override
    public void save(TransactionRequest transactionRequest) {
        transactionRequestRepository.save(transactionRequest);
    }

    @Override
    public TransactionRequest getById(Long id) {
        return transactionRequestRepository.findPendingById(id).orElseThrow(RequestNotFoundException::new);
    }
}
