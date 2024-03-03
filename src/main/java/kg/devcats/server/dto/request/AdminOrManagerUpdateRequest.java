package kg.devcats.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record AdminOrManagerUpdateRequest(
        @NotBlank(message = "Name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        String patronymic,
        @NotEmpty(message = "The phone number is required.")
//        @Pattern(regexp = "^\\+?[0-9]{13,20}$", message = "Invalid phone number")
        String phoneNumber
) {
}
