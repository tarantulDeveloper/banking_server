package kg.devcats.server.dto.response;

import kg.devcats.server.entity.enums.Role;

public record AdminOrManagerInfoResponse(
        String email,
        Role role,
        String firstName,
        String lastName,
        String patronymic,
        String phoneNumber,
        String profileImagePath
) {
}
