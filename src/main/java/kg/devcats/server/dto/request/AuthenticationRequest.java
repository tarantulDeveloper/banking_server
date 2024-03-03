package kg.devcats.server.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}

