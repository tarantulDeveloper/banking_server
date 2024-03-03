package kg.devcats.server.service;

import kg.devcats.server.dto.response.TransactionRequestResponse;
import kg.devcats.server.entity.TransactionRequest;

import java.util.List;

public interface TransactionRequestService {
    List<TransactionRequestResponse> getAllPending();

    void save(TransactionRequest build);

    TransactionRequest getById(Long id);
}
