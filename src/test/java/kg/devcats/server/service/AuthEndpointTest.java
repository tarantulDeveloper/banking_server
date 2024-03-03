package kg.devcats.server.service;

import kg.devcats.server.dto.request.AuthenticationRequest;
import kg.devcats.server.dto.request.RegistrationRequestForDealer;
import kg.devcats.server.dto.response.AuthenticationResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.endpoint.AuthEndpoint;
import kg.devcats.server.entity.RegistrationRequest;
import kg.devcats.server.entity.User;
import kg.devcats.server.exception.UserIsAlreadyRegisteredException;
import kg.devcats.server.exception.UserNotFoundException;
import kg.devcats.server.mapper.RegistrationRequestMapper;
import kg.devcats.server.repo.RegistrationRequestRepository;
import kg.devcats.server.security.JwtService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthEndpointTest {

    @Mock
    JwtService jwtService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    RegistrationRequestRepository registrationRequestRepository;

    @Mock
    UserService userService;

    @Mock
    RegistrationRequestService registrationRequestService;

    @Spy
    RegistrationRequestMapper registrationRequestMapper;

    @InjectMocks
    AuthEndpoint authEndpoint;


    @Test
    void shouldRegisterUser() {
        RegistrationRequestForDealer registrationRequest = getRegisterRequest();
        when(userService.userExistsByEmail(registrationRequest.email())).thenReturn(false);
        when(registrationRequestService.existsByEmail(registrationRequest.email())).thenReturn(false);

        RegistrationRequest registrationRequestApproval = new RegistrationRequest();
        when(registrationRequestMapper.toEntityRegistrationRequest(any())).thenReturn(registrationRequestApproval);

        MessageResponse messageResponse = authEndpoint.register(registrationRequest);

        assertThat(messageResponse).isNotNull();
        assertThat(messageResponse.message()).isEqualTo("Your request has been accepted successfully");

        verify(registrationRequestService, times(1)).saveRequest(any());

    }

    @Test
    void shouldThrowUsersExists_whenRegisteringUser() {
        RegistrationRequestForDealer registrationRequest = getRegisterRequest();
        when(userService.userExistsByEmail(registrationRequest.email())).thenReturn(true);
        assertThatThrownBy(() -> authEndpoint.register(registrationRequest))
                .isInstanceOf(UserIsAlreadyRegisteredException.class);
    }

    @Test
    void shouldAuthenticateUser() {
        AuthenticationRequest request = new AuthenticationRequest("email", "password");
        User user = User.builder().email(request.email()).build();
        when(userService.findUserByEmail(request.email()))
                .thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("Access Token");

        AuthenticationResponse authenticationResponse = authEndpoint.authenticate(request);

        assertThat(authenticationResponse.token()).isEqualTo("Access Token");

    }

    @Test
    void shouldActivateUserByActivationToken() {
       User user = User.builder().email("email").build();
       when(userService.findByActivationToken("valid token")).thenReturn(Optional.of(user));

       MessageResponse response = authEndpoint.activateUserByToken("valid token");

       assertThat(response.message()).isEqualTo("Your account has been activated");
       assertThat(user.isActivated()).isTrue();
       assertThat(user.getActivationToken()).isEqualTo("");
    }

    @Test
    void shouldThrowUserNotFoundException_whenActivationTokenIsInvalid() {
        when(userService.findByActivationToken("invalid token")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authEndpoint.activateUserByToken("invalid token"))
                .isInstanceOf(UserNotFoundException.class);
    }




    private RegistrationRequestForDealer getRegisterRequest() {
        return new RegistrationRequestForDealer(
                "user",
                "password",
                "password",
                "Turdakun",
                "Usubaliev",
                "Usubalievich",
                new Timestamp(System.currentTimeMillis()),
                "1234567890",
                "12345678901234",
                "AN23456",
                "MKK-54-24",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "Kyrgystan"
        );
    }


}
