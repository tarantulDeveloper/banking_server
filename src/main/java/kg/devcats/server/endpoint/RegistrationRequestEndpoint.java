package kg.devcats.server.endpoint;

import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.dto.response.RegistrationRequestResponse;
import kg.devcats.server.entity.ClientAccount;
import kg.devcats.server.entity.Document;
import kg.devcats.server.entity.RegistrationRequest;
import kg.devcats.server.entity.User;
import kg.devcats.server.entity.enums.RegistrationRequestStatus;
import kg.devcats.server.entity.enums.Role;
import kg.devcats.server.mapper.RegistrationRequestMapper;
import kg.devcats.server.mapper.UserMapper;

import kg.devcats.server.service.RegistrationRequestService;
import kg.devcats.server.service.UserService;
import kg.devcats.server.service.DocumentService;
import kg.devcats.server.service.ClientAccountService;
import kg.devcats.server.service.MailSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationRequestEndpoint {

    RegistrationRequestService registrationRequestService;
    RegistrationRequestMapper registrationRequestMapper;
    UserMapper userMapper;
    UserService userService;
    DocumentService documentService;
    ClientAccountService clientAccountService;
    MailSender mailSender;

    public List<RegistrationRequestResponse> findAllPending() {
        return registrationRequestMapper.productsToProductDTOs(registrationRequestService.findAllPending());
    }

    @Transactional
    public MessageResponse approveRequest(Long requestId) {
        RegistrationRequest registrationRequest = registrationRequestService.findPendingById(requestId);
        String activationToken = String.valueOf(UUID.randomUUID());
        Document document =
                registrationRequestMapper.registrationRequestToDocument(registrationRequest);
        User user = userMapper.fromRegistrationRequestToUserEntity(registrationRequest);
        ClientAccount clientAccount = ClientAccount.builder()
                .balance(new BigDecimal(0))
                .build();
        user.setActivationToken(activationToken);
        user.setRole(Role.ROLE_USER);
        User saved = userService.saveUser(user);
        document.setUser(saved);
        clientAccount.setUser(saved);
        documentService.saveDocument(document);
        clientAccountService.save(clientAccount);
        registrationRequest.setStatus(RegistrationRequestStatus.APPROVED);
        registrationRequestService.saveRequest(registrationRequest);
        mailSender.sendRegisterMessage(registrationRequest.getEmail(), registrationRequest.getLastName() +
                " " + registrationRequest.getFirstName(), activationToken);
        return new MessageResponse("Request has been approved");
    }

    public MessageResponse rejectRequest(Long requestId) {
        RegistrationRequest registrationRequest = registrationRequestService.findPendingById(requestId);
        registrationRequest.setStatus(RegistrationRequestStatus.REJECTED);
        registrationRequestService.saveRequest(registrationRequest);
        return new MessageResponse("Request has been rejected");
    }
}
