package kg.devcats.server.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import kg.devcats.server.util.PasswordsIsEqual;

import java.sql.Timestamp;

@PasswordsIsEqual
public record RegistrationRequestForDealer(
        @NotBlank(message = "Email cannot be empty")
        @Email
        String email,
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String confirmPassword,
        @NotBlank(message = "Name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        String patronymic,
        @Past(message = "Birth date must be in the past")
        @NotNull(message = "Birth date cannot be null")
        Timestamp birthDate,
        @NotBlank(message = "Phone number cannot be empty")
        String phoneNumber,
        @NotBlank(message = "Personal number cannot be empty")
        @Pattern(regexp = "[0-9]{14}", message = "Personal number must contain exactly 14 digits")
        String personalNumber,
        @Pattern(regexp = "[a-zA-Z]{2}\\d*", message = "The first two characters must be letters in document id, followed by digits")
        @NotBlank(message = "Document id cannot be empty")
        String documentId,
        @NotBlank(message = "authority cannot be empty")
        String authority,
        @NotNull(message = "documentIssuedAt cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
        Timestamp documentIssuedAt,
        @NotNull(message = "documentExpiresAt cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
        Timestamp documentExpiresAt,
        @NotBlank(message = "citizenship cannot be empty")
        String citizenship
) {
}
