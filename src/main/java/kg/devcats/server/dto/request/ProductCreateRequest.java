package kg.devcats.server.dto.request;

import jakarta.validation.constraints.*;
import kg.devcats.server.util.PortionsAreValid;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@PortionsAreValid
public record ProductCreateRequest (
        @NotNull(message = "Product price can not be null")
        @Min(value = 1, message = "Product price at least must be 1")
        BigDecimal price,
        @NotNull(message = "Product commission can not be null")
        @Min(value = 0, message = "Transaction amount at least must be 0")
        BigDecimal commission,
        @NotNull(message = "Product dealer portion can not be null")
        @Min(value = 0, message = "Product portion for dealer at least must be 0")
        BigDecimal percentageOfProfitForDealer,
        @NotNull(message = "Product dealer portion can not be null")
        @Min(value = 0, message = "Product portion for system at least must be 0")
        BigDecimal percentageOfProfitForSystem,
        @NotNull(message = "Product name can not be null")
        @NotBlank(message = "Product name can not be blank")
        String name,
        @NotNull(message = "Product currency iso code can not be null")
        @Size(min = 3,max = 3,message = "Currency iso code must consist of 3 characters")
        String isoCode,
        @NotNull(message = "Product quantity can not be null")
        @Min(value = 0,message = "Product quantity must be positive or zero")
        Integer quantity,
        @NotNull(message = "Product image can not be null")
        MultipartFile file,
        @NotNull(message = "Product name can not be null")
        @NotBlank(message = "Product name can not be blank")
        String dealerEmail
) {
}
