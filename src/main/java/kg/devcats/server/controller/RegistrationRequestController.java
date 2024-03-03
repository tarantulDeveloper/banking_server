package kg.devcats.server.controller;


import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.RegistrationRequestResponse;
import kg.devcats.server.endpoint.RegistrationRequestEndpoint;
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
@RequestMapping("/api/registration/requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationRequestController {

    RegistrationRequestEndpoint registrationRequestEndpoint;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping
    public List<RegistrationRequestResponse> getRegistrationRequests() {
        return registrationRequestEndpoint.findAllPending();
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/approve/{requestId}")
    public MessageResponse approveRegistrationRequestById(
            @PathVariable Long requestId) {
        return registrationRequestEndpoint.approveRequest(requestId);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/reject/{requestId}")
    public MessageResponse rejectRegistrationRequestById(@PathVariable Long requestId) {
        return registrationRequestEndpoint.rejectRequest(requestId);
    }


}
