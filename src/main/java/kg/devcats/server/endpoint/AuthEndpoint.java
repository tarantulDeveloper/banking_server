package kg.devcats.server.endpoint;

import kg.devcats.server.dto.request.AuthenticationRequest;
import kg.devcats.server.dto.request.RegistrationRequestForDealer;
import kg.devcats.server.dto.response.AuthenticationResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.entity.RegistrationRequest;
import kg.devcats.server.entity.User;
import kg.devcats.server.entity.enums.RegistrationRequestStatus;
import kg.devcats.server.exception.TokenIsExpiredException;
import kg.devcats.server.exception.UserIsAlreadyRegisteredException;
import kg.devcats.server.exception.UserNotFoundException;
import kg.devcats.server.mapper.RegistrationRequestMapper;
import kg.devcats.server.security.JwtService;
import kg.devcats.server.service.RegistrationRequestService;
import kg.devcats.server.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthEndpoint {

    AuthenticationManager authenticationManager;
    UserService userService;
    JwtService jwtService;
    RegistrationRequestService registrationRequestService;
    RegistrationRequestMapper registrationRequestMapper;
    PasswordEncoder passwordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userService.findUserByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return AuthenticationResponse.builder()
                .token(token)
                .refresh_token(refreshToken)
                .build();
    }

    public MessageResponse activateUserByToken(String token) {
        User user = userService.findByActivationToken(token).orElseThrow(UserNotFoundException::new);
        user.setActivated(true);
        user.setActivationToken("");
        userService.saveUser(user);
        return new MessageResponse("Your account has been activated");
    }

    public MessageResponse register(RegistrationRequestForDealer request) {
        if (!userService.userExistsByEmail(request.email()) & !registrationRequestService.existsByEmail(request.email())) {
            RegistrationRequest registrationRequest = registrationRequestMapper.toEntityRegistrationRequest(request);
            registrationRequest.setStatus(RegistrationRequestStatus.PENDING);
            registrationRequest.setPassword(passwordEncoder.encode(request.password()));
            registrationRequest.setProfileImagePath("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
            registrationRequestService.saveRequest(registrationRequest);
            return new MessageResponse("Your request has been accepted successfully");
        }
        throw new UserIsAlreadyRegisteredException();
    }

    public AuthenticationResponse generateNewAccessAndRefreshTokenByRefreshToken(String refreshToken) {
        if(jwtService.isTokenExpired(refreshToken)) {
            throw new TokenIsExpiredException();
        }

        String email = jwtService.extractUsername(refreshToken);

        User user = userService.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .refresh_token(jwtService.generateRefreshToken(email))
                .build();
    }

}
