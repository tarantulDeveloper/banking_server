package kg.devcats.server.dto.response;

import lombok.Builder;

@Builder
public record MessageResponse(
        String message
) {
}
