package kg.devcats.server.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public record ChangeProfileInfoResponse(
        Long id,
        String dealerEmail,
        String firstName,
        String lastName,
        String patronymic,
        String phoneNumber,
        String documentId,
        String authority,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
        Timestamp documentIssuedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
        Timestamp documentExpiresAt,
        String citizenship,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
        Timestamp birthDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
        Timestamp createdAt
) {
}
