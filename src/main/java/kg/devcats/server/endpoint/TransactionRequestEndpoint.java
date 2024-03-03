package kg.devcats.server.endpoint;


import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.TransactionRequestResponse;
import kg.devcats.server.entity.ClientAccount;
import kg.devcats.server.entity.Transaction;
import kg.devcats.server.entity.TransactionRequest;
import kg.devcats.server.entity.enums.TransactionRequestStatus;
import kg.devcats.server.service.TransactionRequestService;
import kg.devcats.server.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionRequestEndpoint {

    TransactionRequestService transactionRequestService;
    TransactionService transactionService;

    public List<TransactionRequestResponse> getAllPending() {
        return transactionRequestService.getAllPending();
    }


    public MessageResponse rejectById(Long id) {
        TransactionRequest transactionRequest = transactionRequestService.getById(id);
        transactionRequest.setStatus(TransactionRequestStatus.REJECTED);
        transactionRequestService.save(transactionRequest);
        return new MessageResponse("Transaction has been rejected successfully");
    }


    @Transactional
    public MessageResponse approveById(Long id) {
        TransactionRequest transactionRequest = transactionRequestService.getById(id);
        BigDecimal amountToSend = transactionRequest.getAmount();
        ClientAccount sender = transactionRequest.getSender();
        ClientAccount receiver = transactionRequest.getReceiver();
        sender.setBalance(sender.getBalance().subtract(amountToSend));
        receiver.setBalance(receiver.getBalance().add(amountToSend));
        transactionRequest.setStatus(TransactionRequestStatus.APPROVED);
        transactionService.save(Transaction.builder()
                .amount(amountToSend)
                .sender(sender)
                .receiver(receiver)
                .build());
        return new MessageResponse("Transaction has been approved successfully");
    }
}
