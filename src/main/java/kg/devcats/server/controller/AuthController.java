package kg.devcats.server.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import kg.devcats.server.dto.request.AuthenticationRequest;
import kg.devcats.server.dto.request.RegistrationRequestForDealer;
import kg.devcats.server.dto.response.AuthenticationResponse;
import kg.devcats.server.dto.response.MessageResponse;
import kg.devcats.server.endpoint.AuthEndpoint;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:8819", "http://194.152.37.7:8819"})
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthEndpoint authEndpoint;

    @SecurityRequirements
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse register(@Valid @RequestBody RegistrationRequestForDealer request) {
        return authEndpoint.register(request);
    }

    @SecurityRequirements
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return authEndpoint.authenticate(request);
    }


    @SecurityRequirements
    @GetMapping("/activate")
    public MessageResponse activateUser(@RequestParam(name = "token") String token) {
        return authEndpoint.activateUserByToken(token);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse generateNewAccessAndRefreshTokenByRefreshToken(@RequestParam(name = "refresh_token") String refresh_token) {
        return authEndpoint.generateNewAccessAndRefreshTokenByRefreshToken(refresh_token);
    }
}

