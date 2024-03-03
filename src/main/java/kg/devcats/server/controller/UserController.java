package kg.devcats.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.devcats.server.dto.request.*;
import kg.devcats.server.dto.response.*;
import kg.devcats.server.endpoint.UserEndpoint;
import kg.devcats.server.security.SimpleUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserEndpoint userEndpoint;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping
    public List<UserFullInfoResponse> getAllUsers() {
        return userEndpoint.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public UserFullInfoResponse getUserById(@PathVariable("userId") Long userId) {
        return userEndpoint.getUserById(userId);
    }

    @Operation(description = "\"role\" can only be ADMIN or MANAGER")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserFullInfoResponse addUser(@Valid @RequestBody SystemUserAddRequest userInfoRequest) {
        return userEndpoint.addSystemUser(userInfoRequest);
    }

    @Operation(description = "\"role\" can only be ADMIN or MANAGER. You can't change client's role.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public UserFullInfoResponse updateAdminOrManager(@Valid @RequestBody SystemUserUpdateRequest userUpdateRequest) {
        return userEndpoint.updateSystemUser(userUpdateRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/enable")
    public UserFullInfoResponse enableOrDisableUser(@Valid @RequestBody UserEnableOrDisableRequest userEnableOrDisableRequest) {
        return userEndpoint.enableOrDisableUser(userEnableOrDisableRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/delete")
    public MessageResponse deleteUser(@Valid @RequestBody UserDeleteRequest userDeleteRequest) {
        return userEndpoint.deleteUser(userDeleteRequest);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/client/{clientId}")
    public ClientFullInfoResponse getClientById(@PathVariable("clientId") Long clientId) {
        return userEndpoint.getClientById(clientId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PutMapping("/client")
    public ClientFullInfoResponse updateClient(@Valid @RequestBody ClientUpdateRequest clientUpdateRequest) {
        return userEndpoint.updateClient(clientUpdateRequest);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/personal")
    public UserPersonalAccountResponse getPersonalInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userEndpoint.getPersonalInfo(userDetails.getUsername());
    }

    @PutMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse updateProfilePhoto(@AuthenticationPrincipal UserDetails userEmail,@Valid @ModelAttribute UserPhotoUpdateRequest userUpdateRequest) {
        return userEndpoint.updateUserImage(userEmail.getUsername(), userUpdateRequest);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/personal/update")
    public MessageResponse updatePersonalInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ChangeProfileInfoRequest changeProfileInfoRequest) {
        return userEndpoint.updatePersonalInfo(userDetails.getUsername(), changeProfileInfoRequest);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/balance")
    public BigDecimal getPersonalBalance(@AuthenticationPrincipal SimpleUserDetails userDetails,
                                         @RequestParam(name = "isoCode", required = false, defaultValue = "SFC") String isoCode){
        return userEndpoint.getPersonalBalance(userDetails.getId(), isoCode.toUpperCase());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/dealer")
    public MessageResponse addDealerByManager(@Valid @RequestBody RegistrationRequestForDealer request) {
        return userEndpoint.addDealerByManager(request);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/admin-manager")
    public AdminOrManagerInfoResponse getAdminOrManagerInfo(@AuthenticationPrincipal SimpleUserDetails userDetails) {
        return userEndpoint.getAdminOrManagerById(userDetails.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PutMapping("/update-admin-manager")
    public AdminOrManagerInfoResponse updateAdminOrManagerInfo(@AuthenticationPrincipal SimpleUserDetails userDetails,
                                                               @RequestBody AdminOrManagerUpdateRequest request) {
        return userEndpoint.updateAdminOrManagerInfo(userDetails.getId(), request);
    }

}
