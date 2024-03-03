package kg.devcats.server.service.impl;

import kg.devcats.server.dto.response.ChangeProfileInfoResponse;
import kg.devcats.server.entity.ProfileChangeRequest;
import kg.devcats.server.exception.RequestNotFoundException;
import kg.devcats.server.repo.ProfileChangeRequestRepository;
import kg.devcats.server.service.ProfileChangeRequestService;
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
public class ProfileChangeRequestServiceImpl implements ProfileChangeRequestService {

    ProfileChangeRequestRepository repository;

    @Override
    public void save(ProfileChangeRequest profileChangeRequest) {
        repository.save(profileChangeRequest);
    }

    @Override
    public ProfileChangeRequest getById(Long id) {
        return repository.findPendingById(id).orElseThrow(RequestNotFoundException::new);
    }

    @Override
    public List<ChangeProfileInfoResponse> getAllPending() {
        return repository.getAllPending();
    }
}
