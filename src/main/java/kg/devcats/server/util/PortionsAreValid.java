package kg.devcats.server.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PortionsValidator.class)
@Documented
public @interface PortionsAreValid {
    String message() default "Portions are not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

