package kg.devcats.server.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.devcats.server.dto.request.ProductCreateRequest;

import java.math.BigDecimal;

public class PortionsValidator implements ConstraintValidator<PortionsAreValid, ProductCreateRequest> {
    @Override
    public void initialize(PortionsAreValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ProductCreateRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.percentageOfProfitForDealer().add(request.percentageOfProfitForSystem()).equals(new BigDecimal(100));
    }
}
