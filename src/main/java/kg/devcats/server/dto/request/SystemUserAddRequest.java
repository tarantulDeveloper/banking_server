package kg.devcats.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SystemUserAddRequest(
        @NotEmpty(message = "The email is required.")
//        @Email(message = "The email is not a valid email.")
        String email,

        @NotEmpty(message = "The password is required.")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z]).{4,}$",
                message = "Password must be 4 characters long and combination lowercase letters and numbers.")
        String password,
        @NotEmpty(message = "The role is required.")
        String role,
        @NotEmpty(message = "The phone number is required.")
//        @Pattern(regexp = "^\\+?[0-9]{13,20}$", message = "Invalid phone number")
        String phoneNumber,
        @NotBlank(message = "Name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,
        String patronymic
) {
}

