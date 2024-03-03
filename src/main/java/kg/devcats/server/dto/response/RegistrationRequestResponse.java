package kg.devcats.server.dto.response;

import java.sql.Timestamp;

public record RegistrationRequestResponse(
        Long id,
        String email,
        String phoneNumber,
        String personalNumber,
        String documentId,
        String authority,
        Timestamp documentIssuedAt,
        Timestamp documentExpiresAt,
        String citizenship,
        Timestamp birthDate,
        String firstName,
        String lastName,
        String patronymic
) {
}
