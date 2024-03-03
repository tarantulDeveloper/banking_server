package kg.devcats.server.controller;


import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.TransactionRequestResponse;
import kg.devcats.server.endpoint.TransactionRequestEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/transaction-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionRequestController {

    TransactionRequestEndpoint transactionRequestEndpoint;


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping
    public List<TransactionRequestResponse> getAll() {
        return transactionRequestEndpoint.getAllPending();
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/approve/{id}")
    public MessageResponse approveById(@PathVariable Long id) {
        return transactionRequestEndpoint.approveById(id);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/reject/{id}")
    public MessageResponse rejectById(@PathVariable Long id) {
        return transactionRequestEndpoint.rejectById(id);
    }
}
