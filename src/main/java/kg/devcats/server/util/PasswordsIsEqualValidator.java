package kg.devcats.server.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.devcats.server.dto.request.RegistrationRequestForDealer;

public class PasswordsIsEqualValidator implements ConstraintValidator<PasswordsIsEqual, RegistrationRequestForDealer> {
    @Override
    public void initialize(PasswordsIsEqual constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegistrationRequestForDealer request, ConstraintValidatorContext constraintValidatorContext) {
        return request.password().equals(request.confirmPassword());
    }
}

