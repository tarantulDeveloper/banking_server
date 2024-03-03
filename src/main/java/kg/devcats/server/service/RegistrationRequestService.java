package kg.devcats.server.service;

import kg.devcats.server.entity.RegistrationRequest;

import java.util.List;

public interface RegistrationRequestService {
    List<RegistrationRequest> findAllPending();

    void saveRequest(RegistrationRequest registrationRequest);

    RegistrationRequest findPendingById(Long id);

    boolean existsByEmail(String email);
}
