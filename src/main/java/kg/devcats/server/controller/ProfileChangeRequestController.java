package kg.devcats.server.controller;

import kg.devcats.server.dto.response.ChangeProfileInfoResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.endpoint.ProfileChangeRequestEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/profile-changes-request")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileChangeRequestController {

    ProfileChangeRequestEndpoint profileChangeRequestEndpoint;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/requests")
    public List<ChangeProfileInfoResponse> getAll() {
        return profileChangeRequestEndpoint.getAllPending();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/approve/{id}")
    public MessageResponse approveById(@PathVariable Long id) {
        return profileChangeRequestEndpoint.approveById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/reject/{id}")
    public MessageResponse rejectById(@PathVariable Long id) {
        return profileChangeRequestEndpoint.rejectById(id);
    }
}
