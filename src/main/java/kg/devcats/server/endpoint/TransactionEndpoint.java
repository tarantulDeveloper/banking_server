package kg.devcats.server.endpoint;


import kg.devcats.server.dto.request.TransactionRequest;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.TransactionResponse;
import kg.devcats.server.entity.ClientAccount;
import kg.devcats.server.entity.enums.TransactionRequestStatus;
import kg.devcats.server.exception.DuplicateEmailException;
import kg.devcats.server.exception.InsufficientFundsException;
import kg.devcats.server.service.ClientAccountService;
import kg.devcats.server.service.TransactionRequestService;
import kg.devcats.server.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionEndpoint {

    ClientAccountService clientAccountService;
    TransactionRequestService transactionRequestService;
    TransactionService transactionService;


    public MessageResponse createTransaction(TransactionRequest transactionRequest, String email) {
        if (transactionRequest.email().equals(email)) {
            throw new DuplicateEmailException("Duplicate emails has been provided");
        }
        BigDecimal amountToSend = transactionRequest.amount();
        ClientAccount sender =
                clientAccountService.findClientAccountByUserEmail(email);
        ClientAccount receiver =
                clientAccountService.findClientAccountByUserEmail(transactionRequest.email());
        validate(sender, amountToSend);

        transactionRequestService.save(kg.devcats.server.entity.TransactionRequest.builder()
                .status(TransactionRequestStatus.PENDING)
                .amount(amountToSend)
                .receiver(receiver)
                .sender(sender)
                .build());

        return new MessageResponse("Transaction request has been accepted successfully");
    }


    private void validate(ClientAccount sender, BigDecimal amount) {
        if (!(sender.getBalance().compareTo(amount) >= 0)) {
            log.error("There are not enough funds on the balance");
            throw new InsufficientFundsException();
        }
    }

    public List<TransactionResponse> getTransactionsInfoByEmail(String email) {
        return transactionService.getTransactionInfoByEmail(email);
    }


}
