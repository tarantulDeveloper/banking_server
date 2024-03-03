package kg.devcats.server.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsIsEqualValidator.class)
@Documented
public @interface PasswordsIsEqual {
    String message() default "Passwords not same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

