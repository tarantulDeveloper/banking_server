package kg.devcats.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.devcats.server.config.SecurityConfig;
import kg.devcats.server.dto.request.AuthenticationRequest;
import kg.devcats.server.dto.request.RegistrationRequestForDealer;
import kg.devcats.server.dto.response.AuthenticationResponse;
import kg.devcats.server.endpoint.AuthEndpoint;
import kg.devcats.server.entity.User;
import kg.devcats.server.exception.UserIsAlreadyRegisteredException;
import kg.devcats.server.exception.UserNotFoundException;
import kg.devcats.server.repo.UserRepository;
import kg.devcats.server.security.AuthEntryPoint;
import kg.devcats.server.security.JwtAuthenticationFilter;
import kg.devcats.server.security.JwtService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    JwtService jwtService;

    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    AuthEntryPoint authEntryPoint;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    AuthEndpoint authEndpoint;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldBeStatusCreated_whenSuccessfullyRegisteredUser() throws Exception {
        RegistrationRequestForDealer registrationRequest = getRegisterRequest();

        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldBeStatusBadRequest_whenRegisteringAlreadyExistedUser() throws Exception {
        RegistrationRequestForDealer registrationRequest = getRegisterRequest();

        when(authEndpoint.register(any(RegistrationRequestForDealer.class))).thenThrow(UserIsAlreadyRegisteredException.class);

        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldBeStatusOk_whenSuccessfullySignedIn() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user", "password");
        when(authEndpoint.authenticate(authenticationRequest)).thenReturn(new AuthenticationResponse("token","refresh-token"));

        mockMvc.perform(post("/api/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldBeStatusOk_whenActivationTokenIsFound() throws Exception{
        String activationToken = "Activation Token";

        User tempUser = User.builder().email("temp").build();
        when(userRepository.findByActivationToken(activationToken))
                .thenReturn(Optional.of(tempUser));

        mockMvc.perform(get("/api/auth/activate")
                .param("token", activationToken))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeStatusNotFound_whenTryingToActivateByNonExistingActivationToken() throws Exception {
        when(authEndpoint.activateUserByToken(anyString())).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get("/api/auth/activate")
                .param("token", "Non Existing Token"))
                .andExpect(status().isNotFound());
    }


    private RegistrationRequestForDealer getRegisterRequest() {
        return new RegistrationRequestForDealer(
                "user@mail.com",
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
