package kg.devcats.server.controller;


import jakarta.validation.Valid;
import kg.devcats.server.dto.request.TransactionRequest;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.TransactionResponse;
import kg.devcats.server.endpoint.TransactionEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionEndpoint transactionEndpoint;


    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/transfer")
    public MessageResponse transferMoney(@Valid @RequestBody TransactionRequest transactionRequest,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        return transactionEndpoint.createTransaction(transactionRequest, userDetails.getUsername());
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my")
    List<TransactionResponse> getPersonalTransactions(@AuthenticationPrincipal UserDetails userDetails) {
        return transactionEndpoint.getTransactionsInfoByEmail(userDetails.getUsername());
    }
}
