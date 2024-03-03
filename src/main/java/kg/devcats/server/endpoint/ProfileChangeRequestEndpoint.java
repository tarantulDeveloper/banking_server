package kg.devcats.server.endpoint;

import kg.devcats.server.dto.response.ChangeProfileInfoResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.entity.Document;
import kg.devcats.server.entity.ProfileChangeRequest;
import kg.devcats.server.entity.User;
import kg.devcats.server.entity.enums.ProfileChangeRequestStatus;
import kg.devcats.server.exception.UserNotFoundException;
import kg.devcats.server.service.DocumentService;
import kg.devcats.server.service.ProfileChangeRequestService;
import kg.devcats.server.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileChangeRequestEndpoint {
    ProfileChangeRequestService profileChangeRequestService;
    UserService userService;
    DocumentService documentService;

    public MessageResponse approveById(Long id) {
        ProfileChangeRequest request = profileChangeRequestService.getById(id);

        User user = userService.findUserByEmail(request.getDealerEmail()).orElseThrow(UserNotFoundException::new);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPatronymic(request.getPatronymic());
        user.setPhoneNumber(request.getPhoneNumber());
        userService.saveUser(user);
        Document userDocumentInfo = documentService.findDocumentByUserId(user.getId());
        userDocumentInfo.setDocumentId(request.getDocumentId());
        userDocumentInfo.setAuthority(request.getAuthority());
        userDocumentInfo.setDocumentIssuedAt(request.getDocumentIssuedAt());
        userDocumentInfo.setDocumentExpiresAt(request.getDocumentExpiresAt());
        userDocumentInfo.setCitizenship(request.getCitizenship());
        userDocumentInfo.setBirthDate(request.getBirthDate());
        documentService.saveDocument(userDocumentInfo);

        request.setStatus(ProfileChangeRequestStatus.APPROVED);
        profileChangeRequestService.save(request);

        return new MessageResponse("Profile changing request has been approved successfully");
    }

    public MessageResponse rejectById(Long id) {
        ProfileChangeRequest request = profileChangeRequestService.getById(id);
        request.setStatus(ProfileChangeRequestStatus.REJECTED);
        profileChangeRequestService.save(request);
        return new MessageResponse("Profile changing request has been rejected successfully");
    }

    public List<ChangeProfileInfoResponse> getAllPending() {
        return profileChangeRequestService.getAllPending();
    }
}
