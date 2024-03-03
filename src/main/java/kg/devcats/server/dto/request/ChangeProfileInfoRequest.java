package kg.devcats.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.sql.Timestamp;

public record ChangeProfileInfoRequest(
        @NotBlank(message = "Name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        @NotBlank(message = "Patronymic cannot be empty")
        String patronymic,
        String phoneNumber,
        @Pattern(regexp = "[a-zA-Z]{2}\\d*", message = "The first two characters must be letters in document id, followed by digits")
        @NotBlank(message = "Document id cannot be empty")
        String documentId,
        @NotBlank(message = "Authority cannot be empty")
        String authority,
        @NotNull(message = "documentIssuedAt cannot be null")
        Timestamp documentIssuedAt,
        @NotNull(message = "documentExpiresAt cannot be null")
        Timestamp documentExpiresAt,
        @NotBlank(message = "citizenship cannot be empty")
        String citizenship,
        @NotNull(message = "Birth date cannot be null")
        Timestamp birthDate
) {
}
