package kg.devcats.server.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;


public record UserPersonalAccountResponse(
        String firstName,
        String lastName,
        String patronymic,
        String email,
        String phoneNumber,
        String profileImagePath,
        String personalNumber,
        String documentId,
        String authority,
        Timestamp documentIssuedAt,
        Timestamp documentExpiresAt,
        String citizenship,
        Timestamp birthDate,
        BigDecimal balance
) {
}

