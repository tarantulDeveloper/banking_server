package kg.devcats.server.service;

import kg.devcats.server.dto.response.ChangeProfileInfoResponse;
import kg.devcats.server.entity.ProfileChangeRequest;

import java.util.List;

public interface ProfileChangeRequestService {
    void save(ProfileChangeRequest profileChangeRequest);

    ProfileChangeRequest getById(Long id);

    List<ChangeProfileInfoResponse> getAllPending();
}
