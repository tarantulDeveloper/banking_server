package kg.devcats.server.service.impl;

import kg.devcats.server.entity.RegistrationRequest;
import kg.devcats.server.exception.RequestNotFoundException;
import kg.devcats.server.repo.RegistrationRequestRepository;
import kg.devcats.server.service.RegistrationRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class RegistrationRequestServiceImpl implements RegistrationRequestService {

    RegistrationRequestRepository registrationRequestRepository;

    @Override
    public List<RegistrationRequest> findAllPending() {
        return registrationRequestRepository.findAllPending();
    }

    @Override
    public void saveRequest(RegistrationRequest registrationRequest) {
        registrationRequestRepository.save(registrationRequest);
    }

    @Override
    public RegistrationRequest findPendingById(Long id) {
        return registrationRequestRepository.findPendingById(id).orElseThrow(RequestNotFoundException::new);
    }

    @Override
    public boolean existsByEmail(String email) {
        return registrationRequestRepository.existsByEmail(email);
    }

}
