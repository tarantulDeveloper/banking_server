package kg.devcats.server.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token,
        String refresh_token
) {
}
