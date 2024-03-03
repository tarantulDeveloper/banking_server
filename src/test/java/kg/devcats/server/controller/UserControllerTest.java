package kg.devcats.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.devcats.server.config.SecurityConfig;
import kg.devcats.server.dto.request.SystemUserAddRequest;
import kg.devcats.server.dto.response.UserFullInfoResponse;
import kg.devcats.server.endpoint.UserEndpoint;
import kg.devcats.server.entity.enums.Role;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Import({SecurityConfig.class})
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserEndpoint userService;

    @MockBean
    JwtService jwtService;
    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    AuthEntryPoint authEntryPoint;

    @MockBean
    UserDetailsService userDetailsService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturnOk_forAdminUser() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldReturnForbidden_forUser() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturnOk_forGetUserById() throws Exception {
        long userId = 123L;
        mockMvc.perform(get("/api/users/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldReturnForbidden_forGetUserById() throws Exception {
        long userId = 123L;
        mockMvc.perform(get("/api/users/{userId}", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturnCreated_forAddUser_withAdminAccount() throws Exception {
        SystemUserAddRequest userAddRequest = getUserAddRequest();

        when(userService.addSystemUser(any(SystemUserAddRequest.class)))
                .thenReturn(getUserAllInfoResponse());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAddRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldReturnForbidden_forAddUser_withUserAccount() throws Exception {
        SystemUserAddRequest userAddRequest = getUserAddRequest();
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAddRequest)))
                .andExpect(status().isForbidden());
    }


    private SystemUserAddRequest getUserAddRequest() {
        return new SystemUserAddRequest(
                "email",
                "password890FK",
                "USER",
                "909090890890",
                "Firstname",
                "Lastname",
                "Patronymic"
        );
    }

    private UserFullInfoResponse getUserAllInfoResponse() {
        return new UserFullInfoResponse(
                1L,
                "email",
                Role.ROLE_USER,
                "8908087897",
                "Somename",
                "Somesurname",
                "Somepatronymic",
                "someurl",
                true,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                false
        );
    }

}
